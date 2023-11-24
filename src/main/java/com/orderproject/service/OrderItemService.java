package com.orderproject.service;

import java.util.List;

import com.orderproject.entity.OrderItems;
import com.orderproject.util.ApiResponse;


public interface OrderItemService {


	ApiResponse<OrderItems> getOrderitem(long itemId);

	ApiResponse<List<OrderItems>> getAllOrderItems();

	//post request
	ApiResponse<Void> addNewOrderItem(OrderItems orderItem);

	//put request
	ApiResponse<Void> updateItemById(long itemId, OrderItems orderItem);

	//delete request
	ApiResponse<Void> deleteItemById(long itemId);

}
