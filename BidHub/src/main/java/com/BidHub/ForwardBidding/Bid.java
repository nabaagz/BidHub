package com.BidHub.ForwardBidding;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity

public class Bid {
	@Id
	@GeneratedValue
	//using @id and @generatedValue to specify the primary key for my entity class
	private int itemID;
	private int userID;
	private int amount;
	private String itemName;
	private int catalogueID;

	public Bid(int userID, int amount, String itemName, int catalogueID) {
	     this.userID = userID;
	     this.amount = amount;
	     this.itemName = itemName;
	     this.catalogueID=catalogueID;
	 }

public int getUserID() {
	return userID;
}
public void setUserID(int userID) {
	this.userID = userID;
}
public int getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}
public int getItemID() {
	return itemID;
}
public void setItemID(int itemID) {
	this.itemID = itemID;
}

public String getitemName() {
	return itemName;
}

public void setitemName(String itemName) {
	this.itemName = itemName;
}

public int getCatalogueID() {
	return catalogueID;
}

public void setCatalogueID(int catalogueID) {
	this.catalogueID = catalogueID;
}

}
