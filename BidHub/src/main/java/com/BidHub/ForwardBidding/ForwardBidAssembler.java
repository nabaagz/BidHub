package com.BidHub.ForwardBidding;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;



@Component
public class ForwardBidAssembler implements RepresentationModelAssembler<Bid, EntityModel<Bid>>{

	@Override
	  public EntityModel<Bid> toModel(Bid bidItem) {

	    // Unconditional links to single-item resource and aggregate root

	    EntityModel<Bid> bidItemModel = EntityModel.of(bidItem,
	        linkTo(methodOn(ForwardBiddingController.class).getItem(bidItem.getItemID())).withSelfRel(),
	        linkTo(methodOn(ForwardBiddingController.class).getAllItems()).withRel("bidItems"));

	    

	    return bidItemModel;
	  }
}
