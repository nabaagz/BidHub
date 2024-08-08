package com.BidHub.ForwardBidding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@Service
public class CatalogueService {
	
	@Autowired private CatalogueRepository repository;
	
	
	//get all items from the catalogue
	public ArrayList<Catalogue> getAllItems(){
		
		ArrayList<Catalogue> List = new ArrayList<>();
		Iterable<Catalogue> listIterable = repository.findAll();
		
		if (listIterable != null) {
			Iterator<Catalogue> newIterator = listIterable.iterator();
			while(newIterator.hasNext()) {
				List.add(newIterator.next());
		}
		return List;
		}
		
		else {
			return null;
		}
	}
	
	//get one item from a cataloge 
	public Catalogue getItem(int itemID) {
		
		Optional<Catalogue> item = repository.findById(itemID);
		return item.orElse(null);
		
	}
	
	
	//add an item 
	
	public Catalogue addItem(Catalogue item) {
		
		Catalogue addedItem = repository.save(item);
		return addedItem;
		
	}
	
}
