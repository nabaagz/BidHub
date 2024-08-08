package com.BidHub.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class BidItem {

	private @Id	@GeneratedValue Long id;
	private Long sellerUserId;
	private Long buyerUserId;
	private String itemName;
	private String description;
	private boolean dutch;
	private boolean forward;
	private double duration;
	private double bidPrice;
	private Status status;

	public BidItem() {
	}

	public BidItem(Long sellerUserId, String itemName, String description, boolean dutch, boolean forward,
			double duration, double bidPrice, Status status) {
		this.sellerUserId = sellerUserId;
		this.itemName = itemName;
		this.description = description;
		this.dutch = dutch;
		this.forward = forward;
		this.duration = duration;
		this.bidPrice = bidPrice;
		this.status = status;
	}

	public Long getId() {
		return this.id;
	}

	public Long getSellerUserId() {
		return this.sellerUserId;
	}

	public Long getBuyerUserId() {
		return this.buyerUserId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public String getDescription() {
		return this.description;
	}

	public double getBidPrice() {
		return this.bidPrice;
	}

	public Status getStatus() {
		return this.status;
	}

	public boolean isDutch() {
		return dutch;
	}

	public boolean isForward() {
		return forward;
	}

	public double getDuration() {
		return duration;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSellerUserId(Long sellerUserId) {
		this.sellerUserId = sellerUserId;
	}

	public void setBuyerUserId(Long buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setDutch(boolean dutch) {
		this.dutch = dutch;
	}

	public void setForward(boolean forward) {
		this.forward = forward;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof BidItem))
			return false;
		BidItem bidItem = (BidItem) o;
		return Objects.equals(this.id, bidItem.id) && Objects.equals(this.sellerUserId, bidItem.sellerUserId)
				&& Objects.equals(this.buyerUserId, bidItem.buyerUserId)
				&& Objects.equals(this.itemName, bidItem.itemName)
				&& Objects.equals(this.description, bidItem.description) && Objects.equals(this.dutch, bidItem.dutch)
				&& Objects.equals(this.forward, bidItem.forward) && Objects.equals(this.duration, bidItem.duration)
				&& Objects.equals(this.bidPrice, bidItem.bidPrice) && this.status == bidItem.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.sellerUserId, this.buyerUserId, this.itemName, this.description, this.dutch,
				this.forward, this.duration, this.bidPrice, this.status);
	}

	@Override
	public String toString() {
		return "Sell{" + "id=" + this.id + ", sellerUserId=" + this.sellerUserId + ", buyerUserId=" + this.buyerUserId
				+ ", itemName=" + this.itemName + ", description='" + this.description + '\'' + ", dutch=" + this.dutch
				+ ", forward=" + this.forward + ", duration=" + this.duration + ", bidPrice=" + this.bidPrice
				+ ", status=" + this.status + '}';
	}
}