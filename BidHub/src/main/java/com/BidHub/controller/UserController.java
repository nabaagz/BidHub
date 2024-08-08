package com.BidHub.controller;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.BidHub.exception.ApiException;
import com.BidHub.assembler.UserModelAssembler;
import com.BidHub.assembler.BidItemModelAssembler;
import com.BidHub.entity.ChangePassword;
import com.BidHub.entity.ForgotPassword;
import com.BidHub.entity.SignIn;
import com.BidHub.entity.SignUp;
import com.BidHub.entity.BidItem;
import com.BidHub.entity.Status;
import com.BidHub.entity.User;
import com.BidHub.repository.BidItemRepository;
import com.BidHub.repository.UserRepository;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController {

	private final UserRepository userRepository;
	private final BidItemRepository bidItemRepository;
	private final UserModelAssembler userAssembler;
	private final BidItemModelAssembler bidItemAssembler;

	private boolean checkUsernameAvailable(String username) throws Exception {
		Optional<User> userFound = userRepository.findByUsername(username);
		if (userFound.isPresent()) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Username is already exists.");
		}
		return true;
	}

	private boolean checkUsernameValid(String username) throws Exception {
		if (!Pattern.matches("^(?=\\S+$).{5,}$", username)) {
			throw new ApiException(HttpStatus.BAD_REQUEST,
					"Username must be at least 5 charcters long, and not contain any spaces.");
		}
		return true;
	}

	UserController(UserRepository userRepository, BidItemRepository bidItemRepository, UserModelAssembler userAssembler,
			BidItemModelAssembler bidItemAssembler) {
		this.userRepository = userRepository;
		this.bidItemRepository = bidItemRepository;
		this.userAssembler = userAssembler;
		this.bidItemAssembler = bidItemAssembler;
	}

	@GetMapping("/users")
	public CollectionModel<EntityModel<User>> allUsers() {

		List<EntityModel<User>> users = userRepository.findAll().stream().map(userAssembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(users, linkTo(methodOn(UserController.class).allUsers()).withSelfRel());
	}

	@GetMapping("/users/{userId}")
	public EntityModel<User> oneUser(@PathVariable Long userId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found."));

		return userAssembler.toModel(user);
	}

	@GetMapping("/signIn")
	public EntityModel<User> signIn(@RequestBody SignIn userSignIn) {

		User user = userRepository.findByUsername(userSignIn.getUsername())
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found."));

		if (!user.getPassword().equals(userSignIn.getPassword())) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Password is incorrect.");
		}

		return userAssembler.toModel(user);
	}

	@PutMapping("/signIn/forgetPassowrd")
	ResponseEntity<?> userForgotPassword(@RequestBody ForgotPassword forgotPassword) throws Exception {

		User user = userRepository.findByUsername(forgotPassword.getUsername())
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found."));

		if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$",
				forgotPassword.getNewPassword())) {
			throw new ApiException(HttpStatus.BAD_REQUEST,
					"Password must be at least 8 charcters long, at least 1 upper case and 1 lower case letter, at least 1 number, and at least 1 special character.");
		}
		if (!forgotPassword.getNewPassword().equals(forgotPassword.getConfirmNewPassword())) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Password and Confirm Password do not match.");
		}
		user.setPassword(forgotPassword.getNewPassword());

		EntityModel<User> entityModel = userAssembler.toModel(userRepository.save(user));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PostMapping("/signUp")
	ResponseEntity<?> signUp(@RequestBody SignUp newUser) throws Exception {

		if (newUser.getFirstName().isBlank() && newUser.getFirstName().isEmpty() && newUser.getLastName().isBlank()
				&& newUser.getLastName().isEmpty() && newUser.getEmail().isBlank() && newUser.getEmail().isEmpty()
				&& newUser.getPhone().isBlank() && newUser.getPhone().isEmpty() && newUser.getAddress().isBlank()
				&& newUser.getAddress().isEmpty() && newUser.getPostalCode().isBlank()
				&& newUser.getPostalCode().isEmpty() && newUser.getCity().isBlank() && newUser.getCity().isEmpty()
				&& newUser.getCountry().isBlank() && newUser.getCountry().isEmpty() && newUser.getUsername().isBlank()
				&& newUser.getUsername().isEmpty() && newUser.getPassword().isBlank() && newUser.getPassword().isEmpty()
				&& newUser.getConfirmPassword().isBlank() && newUser.getConfirmPassword().isEmpty()) {
			throw new ApiException(HttpStatus.BAD_REQUEST,
					"All fields must be filled in, 'Unit' is the only exception.");
		}
		if (checkUsernameAvailable(newUser.getUsername()) && checkUsernameValid(newUser.getUsername())) {
			if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$",
					newUser.getPassword())) {
				throw new ApiException(HttpStatus.BAD_REQUEST,
						"Password must be at least 8 charcters long, at least 1 upper case and 1 lower case letter, at least 1 number, and at least 1 special character.");
			}
			if (!newUser.getPassword().equals(newUser.getConfirmPassword())) {
				throw new ApiException(HttpStatus.BAD_REQUEST, "Password and Confirm Password do not match.");
			}
		}

		User user = new User();
		user.setName(newUser.getName());
		user.setEmail(newUser.getEmail());
		user.setAddress(newUser.getAddress());
		user.setUnit(newUser.getUnit());
		user.setPostalCode(newUser.getPostalCode());
		user.setCity(newUser.getCity());
		user.setCountry(newUser.getCountry());
		user.setPhone(newUser.getPhone());
		user.setUsername(newUser.getUsername());
		user.setPassword(newUser.getPassword());

		EntityModel<User> entityModel = userAssembler.toModel(userRepository.save(user));

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}

	@PutMapping("/account/{userId}")
	ResponseEntity<?> editUser(@RequestBody User newUser, @PathVariable Long userId) throws Exception {

		User updatedUser = null;

		if (checkUsernameAvailable(newUser.getUsername()) && checkUsernameValid(newUser.getUsername())) {
			updatedUser = userRepository.findById(userId) //
					.map(user -> {
						user.setName(newUser.getName());
						user.setEmail(newUser.getEmail());
						user.setAddress(newUser.getAddress());
						user.setUnit(newUser.getUnit());
						user.setPostalCode(newUser.getPostalCode());
						user.setCity(newUser.getCity());
						user.setCountry(newUser.getCountry());
						user.setPhone(newUser.getPhone());
						user.setUsername(newUser.getUsername());
						return userRepository.save(user);
					}) //
					.orElseGet(() -> {
						newUser.setId(userId);
						return userRepository.save(newUser);
					});
		}

		EntityModel<User> entityModel = userAssembler.toModel(updatedUser);

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}

	@PutMapping("/account/{userId}/changePassowrd")
	ResponseEntity<?> editUserPassword(@RequestBody ChangePassword changePassword, @PathVariable Long userId)
			throws Exception {

		User updatedUser = userRepository.findById(userId).get();

		if (!updatedUser.getPassword().equals(changePassword.getCurrentPassword())) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Current Password is incorrect.");
		}

		if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$",
				changePassword.getNewPassword())) {
			throw new ApiException(HttpStatus.BAD_REQUEST,
					"New password must be at least 8 charcters long, at least 1 upper case and 1 lower case letter, at least 1 number, and at least 1 special character.");
		}

		if (updatedUser.getPassword().equals(changePassword.getNewPassword())) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "New password must be different from previous.");
		}

		if (!changePassword.getNewPassword().equals(changePassword.getConfirmNewPassword())) {
			throw new ApiException(HttpStatus.BAD_REQUEST,
					"New Password and Confirm New Password fields do not match.");
		}

		updatedUser.setPassword(changePassword.getNewPassword());
		userRepository.save(updatedUser);

		EntityModel<User> entityModel = userAssembler.toModel(updatedUser);

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}

	@DeleteMapping("/account/{userId}")
	ResponseEntity<?> deleteUser(@PathVariable Long userId) {

		userRepository.deleteById(userId);

		return ResponseEntity.noContent().build();
	}

	// User here is the one selling the item
	@GetMapping("/account/{userId}/bidItems")
	public CollectionModel<EntityModel<BidItem>> userAllBidItems(@PathVariable Long userId) {

		List<EntityModel<BidItem>> bidItems = bidItemRepository.findAll().stream()
				.filter(item -> item.getSellerUserId() == userId).map(bidItemAssembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(bidItems, //
				linkTo(methodOn(BidItemController.class).all()).withSelfRel());
	}

	// User here is the one selling the item
	@GetMapping("/account/{userId}/bidItems/{bidItemItemId}")
	public EntityModel<BidItem> userOneBidItem(@PathVariable Long bidItemItemId) {

		BidItem bidItem = bidItemRepository.findById(bidItemItemId) //
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Bid item not found."));

		return bidItemAssembler.toModel(bidItem);
	}

	// User here is the one selling the item
	@PostMapping("/account/{userId}/bidItems")
	ResponseEntity<EntityModel<BidItem>> userNewBidItem(@RequestBody BidItem bidItem, @PathVariable Long userId) {

		if (bidItem.getItemName().isBlank() && bidItem.getItemName().isEmpty() && bidItem.getDescription().isBlank()
				&& bidItem.getDescription().isEmpty()) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "All fields must be filled in.");
		}
		if (!bidItem.isDutch() && !bidItem.isForward()) {
			throw new ApiException(HttpStatus.BAD_REQUEST,
					"Item must be part of one type of bidding: Dutch bidding or Forward bidding.");
		}
		if (bidItem.isDutch() && bidItem.isForward()) {
			throw new ApiException(HttpStatus.BAD_REQUEST,
					"Item must be part of only one type of bidding: Dutch bidding or Forward bidding.");
		}
		if (bidItem.isDutch()) {
			bidItem.setDuration(0);
		}
		if (bidItem.isForward()) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Duration cannot be less than or equal to 0.");
		}
		if (bidItem.getBidPrice() <= 0) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Bid Price cannot be less than or equal to 0.");
		}

		bidItem.setSellerUserId(userId);
		bidItem.setStatus(Status.IN_PROGRESS);
		BidItem newBidItem = bidItemRepository.save(bidItem);

		return ResponseEntity //
				.created(linkTo(methodOn(BidItemController.class).one(newBidItem.getId())).toUri()) //
				.body(bidItemAssembler.toModel(newBidItem));
	}

	// User here can be the one selling or buying the item
	@PutMapping("/account/{userId}/bidItems/{bidItemId}")
	ResponseEntity<?> editBidItem(@RequestBody BidItem newBidItem, @PathVariable Long bidItemId) {

		BidItem updatingBidItem = bidItemRepository.findById(bidItemId).get();

		if (updatingBidItem.getStatus().equals(Status.SOLD)) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Item is unavailable to make changes.");
		}
		if (newBidItem.getItemName().isBlank() && newBidItem.getItemName().isEmpty()
				&& newBidItem.getDescription().isBlank() && newBidItem.getDescription().isEmpty()) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "All fields must be filled in.");
		}
		if (newBidItem.getBidPrice() <= 0) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Bid Price cannot be less than or equal to 0.");
		}
		if (updatingBidItem.isDutch() != newBidItem.isDutch() && updatingBidItem.isForward() != newBidItem.isForward()
				&& updatingBidItem.getDuration() != newBidItem.getDuration()) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Do not change type of bidding and duration of bidding.");
		}

		BidItem updatedBidItem = bidItemRepository.findById(bidItemId) //
				.map(bidItemItem -> {
					bidItemItem.setItemName(newBidItem.getItemName());
					bidItemItem.setDescription(newBidItem.getDescription());
					bidItemItem.setBidPrice(newBidItem.getBidPrice());
					return bidItemRepository.save(bidItemItem);
				}) //
				.orElseGet(() -> {
					newBidItem.setId(bidItemId);
					return bidItemRepository.save(newBidItem);
				});

		EntityModel<BidItem> entityModel = bidItemAssembler.toModel(updatedBidItem);

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}

	// User here is the one buying the item
	@PutMapping("/account/{userId}/bidItems/{bidItemId}/buyNow")
	public ResponseEntity<?> sold(@PathVariable Long bidItemId, @PathVariable Long userId) {

		BidItem bidItem = bidItemRepository.findById(bidItemId) //
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Bid item not found."));

		if (bidItem.getStatus() == Status.IN_PROGRESS) {
			bidItem.setStatus(Status.SOLD);
			bidItem.setBuyerUserId(userId);
			return ResponseEntity.ok(bidItemAssembler.toModel(bidItemRepository.save(bidItem)));
		}

		return ResponseEntity //
				.status(HttpStatus.METHOD_NOT_ALLOWED) //
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
				.body(Problem.create() //
						.withTitle("Method not allowed") //
						.withDetail("You can't complete an bidItem that is in the " + bidItem.getStatus() + " status"));
	}

	// User here is the one selling the item
	@GetMapping("/account/{userId}/bidItems/sold")
	public CollectionModel<EntityModel<BidItem>> userAllSoldBidItems(@PathVariable Long userId) {

		List<EntityModel<BidItem>> bidItems = bidItemRepository.findAll().stream()
				.filter(item -> item.getSellerUserId() == userId && item.getStatus().equals(Status.SOLD)) //
				.map(bidItemAssembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(bidItems, //
				linkTo(methodOn(BidItemController.class).all()).withSelfRel());
	}

	// User here is the one selling the item
	@GetMapping("/account/{userId}/bidItems/won")
	public CollectionModel<EntityModel<BidItem>> userAllBoughtBidItems(@PathVariable Long userId) {

		List<EntityModel<BidItem>> bidItems = bidItemRepository.findAll().stream()
				.filter(item -> item.getBuyerUserId() == userId && item.getStatus().equals(Status.SOLD)) //
				.map(bidItemAssembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(bidItems, //
				linkTo(methodOn(BidItemController.class).all()).withSelfRel());
	}

	// User here is the one selling the item
	@DeleteMapping("/users/{userId}/bidItems/{bidItemId}")
	ResponseEntity<?> cancel(@PathVariable Long bidItemId) {

		bidItemRepository.deleteById(bidItemId);

		return ResponseEntity.noContent().build();
	}
}