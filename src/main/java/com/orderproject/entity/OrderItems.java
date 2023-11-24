package com.orderproject.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "order_item")
public class OrderItems {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long itemId;
	
	@NotNull(message = "item name should't null")
	private String itemName;
	
	private int itemQty;
	private BigDecimal itemPrice;

	@ManyToOne
	@JoinColumn(name = "order_id_fk")
	private Order order;
	
	public OrderItems() {
		// TODO Auto-generated constructor stub
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public OrderItems(long itemId, String itemName, int itemQty, BigDecimal itemPrice, Order order) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemQty = itemQty;
		this.itemPrice = itemPrice;
		this.order = order;
	}

	public OrderItems(long itemId, String itemName, int itemQty, BigDecimal itemPrice) {

		this.itemId = itemId;
		this.itemName = itemName;
		this.itemQty = itemQty;
		this.itemPrice = itemPrice;

	}
	@Override
	public String toString() {
		return "OrderItems [itemId=" + itemId + ", itemName=" + itemName + ", itemQty=" + itemQty + ", itemPrice="
				+ itemPrice + ", order=" + order + "]";
	}
	
	
    
}

