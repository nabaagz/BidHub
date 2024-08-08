package com.BidHub.ForwardBidding;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Catalogue {
	
	private @Id @GeneratedValue
	int itemID;
	
	private String itemName; 
	private String auctionType;
	private int currentPrice;
	private String description;
	private int bidWinnerID;
	private String endTime;
	private int shippingTime;
	public Catalogue(String itemName, String auctionType, int currentPrice, String description, int bidWinnerID,
			String endTime,int shippingTime) {
		this.itemName = itemName;
		this.auctionType=auctionType;
		this.currentPrice = currentPrice;
		this.description = description;
		this.bidWinnerID = bidWinnerID;
		this.endTime = endTime;
		this.shippingTime = shippingTime;
	}
	public Catalogue() {}
	
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getAuctionType() {
		return auctionType;
	}
	public void setAuctionType(String auctionType) {
		this.auctionType = auctionType;
	}
	public int getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getBidWinnerID() {
		return bidWinnerID;
	}
	public void setBidWinnerID(int bidWinnerID) {
		this.bidWinnerID = bidWinnerID;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getShippingTime() {
		return shippingTime;
	}
	public void setShippingTime(int shippingTime) {
		this.shippingTime = shippingTime;
	
	
	}
	

}
