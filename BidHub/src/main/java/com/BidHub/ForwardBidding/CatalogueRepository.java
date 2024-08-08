package com.BidHub.ForwardBidding;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

	
	public interface CatalogueRepository extends CrudRepository<Catalogue, Integer> {

}
