package com.orderproject.service;

import java.util.List;

import com.orderproject.entity.Order;


public interface OrderService {

	public  List<Order> getOrderList();
	 public Order getOrder(long id);
	 public Order saveOrder(Order order);
	 public Order updateOrder(Order order);
	 public boolean deleteOrder(long id);
}
