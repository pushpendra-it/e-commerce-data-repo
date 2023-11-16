package com.orderproject.service;

import java.util.List;

import com.orderproject.entity.OrderItems;


public interface OrderItemService {

	 public  List<OrderItems> getOrderItemList();
	 public OrderItems getOrderItem(long id);
	 public OrderItems saveOrderItem(OrderItems order);
	 public OrderItems updateOrderItem(OrderItems order);
	 public boolean deleteOrderItem(long id);
}
