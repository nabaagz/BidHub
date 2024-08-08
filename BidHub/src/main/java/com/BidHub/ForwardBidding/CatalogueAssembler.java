package com.BidHub.ForwardBidding;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CatalogueAssembler {
	
	
	public EntityModel<Catalogue> toModel(Catalogue catalogue){
		EntityModel<Catalogue> catalogueModel = EntityModel.of(catalogue,
		        linkTo(methodOn(CatalogueController.class).getItem(catalogue.getItemID())).withSelfRel(),
		        linkTo(methodOn(CatalogueController.class).getAllItems()).withRel("allItems")
		    );

		    return catalogueModel;

}
}