package com.BidHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.BidHub.entity.BidItem;

@Repository
public interface BidItemRepository extends JpaRepository<BidItem, Long> {
}

