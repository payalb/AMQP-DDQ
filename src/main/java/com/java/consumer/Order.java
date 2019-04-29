package com.java.consumer;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Order implements Serializable{
	@JsonIgnore
	public int count;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String custId;
	public String amount;
	public String orderId;
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
