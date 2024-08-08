package com.BidHub.ForwardBidding;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ForwardBiddingRepository extends  CrudRepository<Bid, Integer>{
	ArrayList<Bid> findByCatalogueID(int catalogueID);
}
