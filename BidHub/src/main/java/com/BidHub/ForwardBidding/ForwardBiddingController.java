package com.BidHub.ForwardBidding;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Map;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.HashMap;



@RestController
public class ForwardBiddingController {
	
	@Autowired ForwardBiddingService newService;
	
	// @Autowired CatalogueController cataloguecontroller;

	//get all bids
	@GetMapping("/ForwardBidding/getAll")
	public ResponseEntity<ArrayList<Bid>> getAllItems() {
		ArrayList<Bid> getAllBids = newService.getAllItems();
		
		if (getAllBids != null) {
			return ResponseEntity.ok(getAllBids);
					
		} else {
			return ResponseEntity.notFound().build();
		}
		}
	//get a bid
	@GetMapping ("/ForwardBidding/get")
	public ResponseEntity<Bid> getItem(@RequestParam(name="id") int itemID){
		Bid item = newService.getItem(itemID);
		
		if (item!=null) {
			return ResponseEntity.ok(item);
		}
		else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	//add an item to the db
	@PostMapping("/ForwardBidding/add")
	public ResponseEntity<Bid> addItem(Bid item){
		try {
	        Bid addedItem = newService.addItem(item);
	        return ResponseEntity.ok(addedItem);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    }
	}
	
	
	@GetMapping("/ForwardBidding/getBidByCatalogueID")
	public ResponseEntity<ArrayList<Bid>> getBidByCatalogueID(@RequestParam(name="id") int id)
	{
		if (id != 0) {
			ArrayList<Bid> list = newService.getBidByCatalogueID(id);
			return ResponseEntity.ok(list);
		}
		else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	//posting a bid for forwardbidding
	
	@PostMapping("/forwardBidding/send")
	public ResponseEntity<Map<String, Object>> sendForwardBid(@RequestBody @Valid Bid bid){
		 Map<String, Object> response = new HashMap<>();

	        if (bid == null || bid.getAmount() == 0 || bid.getCatalogueID() == 0 || bid.getUserID() == 0) {
	            return ResponseEntity.badRequest().body(response);
	        }
	        
	     // Get CatalogItem/Auction details
	        ResponseEntity<Catalogue> responseEntity = CatalogueController.getItem(bid.getCatalogueID());
	        Catalogue auctionItem = responseEntity.getBody();
	        if (auctionItem == null) {
	            System.out.println("CatalogItem does not exist");
	            return ResponseEntity.badRequest().body(response);
	        }
	        
	     // Check if Auction is open for bids
	        ZonedDateTime time = ZonedDateTime.parse(auctionItem.getEndTime());
	        if (ZonedDateTime.now().isAfter(time)) {
	            System.out.println(bid.toString() + "Above bid because the auction has closed. \n");
	            return ResponseEntity.badRequest().body(response);
	        }
	        
	     // Store bid in db
	        newService.addItem(bid);

	        // Compare bid to highest
	        if (auctionItem.getCurrentPrice() < bid.getAmount()) {
	            auctionItem.setCurrentPrice(bid.getAmount());
	            auctionItem.setBidWinnerID(bid.getUserID());
	          
	           CatalogueController.addItem(auctionItem);
	        }

	        response.put("highestBid", auctionItem.getCurrentPrice());
	        response.put("highestBidderID", auctionItem.getBidWinnerID());
	        
	        return ResponseEntity.ok(response);
	    }
	
	@GetMapping("/forwardBidding/getStatus")
	public ResponseEntity<Map<String, Object>> getAuctionStatus(@RequestParam(name="id") int id) {
        ResponseEntity<Catalogue> responseEntity = CatalogueController.getItem(id);
        Catalogue auctionItem = responseEntity.getBody();
        if (auctionItem == null) {
            System.out.println("CatalogItem does not exist");
            return ResponseEntity.badRequest().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("highestBid", auctionItem.getCurrentPrice());
        response.put("highestBidderID", auctionItem.getBidWinnerID());

        return ResponseEntity.ok(response);
    }
	
	}
	
	
	
