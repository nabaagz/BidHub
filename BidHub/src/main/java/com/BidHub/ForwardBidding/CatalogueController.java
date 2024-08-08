package com.BidHub.ForwardBidding;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

public class CatalogueController {
	
	@Autowired
	static CatalogueService newService;
	
	@GetMapping("/Catalogue/getAll")
	public ResponseEntity<ArrayList<Catalogue>> getAllItems(){
		ArrayList<Catalogue> List = newService.getAllItems();
		
		return ((List != null) ? new ResponseEntity<ArrayList<Catalogue>>(List, HttpStatus.OK) :
			new ResponseEntity<>(null, HttpStatus.OK));
	}
	
	@GetMapping("/Catalogue/get")
	public static ResponseEntity<Catalogue> getItem(@RequestParam(name="id") int itemID){
		
		Catalogue item = newService.getItem(itemID);
		
		if (item != null) {
			return ResponseEntity.ok(item);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/Catalogue/add")
	public static ResponseEntity<Catalogue> addItem(Catalogue Item){
		
		try {
			Catalogue addedItem = newService.addItem(Item);
			return ResponseEntity.ok(addedItem);
		}
		catch (Exception e){
			
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}
