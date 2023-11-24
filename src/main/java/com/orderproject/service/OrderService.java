package com.orderproject.service;

import java.util.List;

import com.orderproject.entity.Order;
import com.orderproject.util.ApiResponse;


public interface OrderService {

	public ApiResponse<List<Order>> getOrderList();
	 public ApiResponse<Order> getOrder(long id);
	 public ApiResponse<Void> saveOrder(Order order);
	 public ApiResponse<Void> updateOrder(Order order);
	 public ApiResponse<Void> deleteOrder(long id);
}
