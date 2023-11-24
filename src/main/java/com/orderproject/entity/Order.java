package com.orderproject.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name ="orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;

	@Column(name = "orderDate", nullable = false)
	@NotNull(message = "order date can not be null !!!")
	private String orderDate;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItems> itemList = new ArrayList<OrderItems>();
		
	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderItems> getItemList() {
		return itemList;
	}

	public void setItemList(List<OrderItems> itemList) {
		this.itemList = itemList;
	}

	public Order(Long orderId, String orderDate, List<OrderItems> itemList) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.itemList = itemList;
	}
	public Order(Long orderId, String orderDate) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;

	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", itemList=" + itemList + "]";
	}

	
	
	
}
