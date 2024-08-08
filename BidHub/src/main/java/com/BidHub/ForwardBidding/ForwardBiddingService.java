package com.BidHub.ForwardBidding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@Service
public class ForwardBiddingService {
	
	
	@Autowired private ForwardBiddingRepository repository;
	
	
	//get an ITEM from db
	public Bid getItem (int itemID) {
		Optional<Bid> item = repository.findById(itemID);		
		return item.orElse(null);
	}
	
	//getting a BID from db
	public ArrayList<Bid> getBidByCatalogueID(int catalogueID){
		ArrayList<Bid> bids = repository.findByCatalogueID(catalogueID);
		return bids;
	}
	
	//get all ITEMS from db and stores it in list and returns it
	public ArrayList<Bid> getAllItems(){
		
		Iterable <Bid> bidsIterable = repository.findAll();
		if (bidsIterable != null) {
			ArrayList<Bid> list = new ArrayList();
			for (Bid bid: bidsIterable) {
				list.add(bid);
			}
			return list;
		}
		else {
			return null;
		}
	}
	//add an ITEM to the db
	
	public Bid addItem (Bid item) {
		Bid newItem = repository.save(item);
		return newItem;
		
	}
	

	

}
