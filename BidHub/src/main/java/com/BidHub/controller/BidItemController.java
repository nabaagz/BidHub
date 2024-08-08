package com.BidHub.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.BidHub.assembler.BidItemModelAssembler;
import com.BidHub.entity.BidItem;
import com.BidHub.exception.ApiException;
import com.BidHub.repository.BidItemRepository;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class BidItemController {

	private final BidItemRepository bidItemRepository;
	private final BidItemModelAssembler bidItemAssembler;

	BidItemController(BidItemRepository bidItemRepository, BidItemModelAssembler bidItemAssembler) {

		this.bidItemRepository = bidItemRepository;
		this.bidItemAssembler = bidItemAssembler;
	}

	@GetMapping("/bidItems")
	public CollectionModel<EntityModel<BidItem>> all() {

		List<EntityModel<BidItem>> bidItems = bidItemRepository.findAll().stream() //
				.map(bidItemAssembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(bidItems, //
				linkTo(methodOn(BidItemController.class).all()).withSelfRel());
	}

	@GetMapping("/bidItems/{bitItemId}")
	public EntityModel<BidItem> one(@PathVariable Long bitItemId) {

		BidItem bidItem = bidItemRepository.findById(bitItemId) //
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Bid item not found."));

		return bidItemAssembler.toModel(bidItem);
	}
}