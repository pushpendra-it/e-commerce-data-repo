package com.orderproject.serviceimpl;

import java.util.List;
import java.util.Optional;

import com.orderproject.exception.InvalidDateException;
import com.orderproject.util.ApiResponse;
import com.orderproject.util.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderproject.entity.OrderItems;
import com.orderproject.repo.OrderItemRepo;
import com.orderproject.service.OrderItemService;

@Service
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	OrderItemRepo orderItemRepository;


	@Override
	public ApiResponse<OrderItems> getOrderitem(long itemId) {
		Optional<OrderItems> orderItem = orderItemRepository.findById(itemId);

		ApiResponse<OrderItems> responseOrderItem = new ApiResponse<>();
		if (orderItem.isPresent()) {
			responseOrderItem.setStatusCode(200);
			responseOrderItem.setMessage("Item with itemId: " + itemId + " found");
			responseOrderItem.setData(orderItem.get());
		} else {
			log.info("item with itemId: " + itemId + " not found in DB!");
			responseOrderItem.setStatusCode(404);
			responseOrderItem.setMessage("item with itemId: " + itemId + " not found!");

		}
		return responseOrderItem;
	}

	@Override
	public ApiResponse<List<OrderItems>> getAllOrderItems() {
		ApiResponse<List<OrderItems>> listResponse = new ApiResponse<>();
		List<OrderItems> orderItemList = orderItemRepository.findAll();


		if (orderItemList.isEmpty()) {
			log.info("No Order-Items found");
			listResponse.setStatusCode(404);
			listResponse.setMessage("No Order-Items found");

		} else {
			listResponse.setStatusCode(200);
			listResponse.setMessage(orderItemList.size() + " Order-Items found!");
			listResponse.setData(orderItemList);
		}

		return listResponse;

	}

	@Override
	public ApiResponse<Void> addNewOrderItem(OrderItems orderItem) {
		long savedOrderItemId = orderItemRepository.save(orderItem).getItemId();
		ApiResponse<Void> response = new ApiResponse<>();

		log.info("Order Item Added Successfully: " + orderItem);

		response.setStatusCode(201);
		response.setMessage("Order Item Added Successfully: " + savedOrderItemId);


		return response;

	}

	@Override
	public ApiResponse<Void> updateItemById(long itemId, OrderItems orderItem) {
		ApiResponse<Void> response = new ApiResponse<>();


		if (orderItemRepository.existsById(itemId)) {
			OrderItems fetchedOrderItem = orderItemRepository.findById(itemId).get();

			fetchedOrderItem.setItemName(orderItem.getItemName());
			fetchedOrderItem.setItemPrice(orderItem.getItemPrice());
			fetchedOrderItem.setItemQty(orderItem.getItemQty());

			orderItemRepository.save(fetchedOrderItem);
			log.info("Order-Item with id: " + itemId + " Updated Successfully");


			response.setStatusCode(200);
			response.setMessage("Order-Item with id: " + itemId + " Updated Successfully");

		} else {

			response.setStatusCode(400);
			response.setMessage("Order-Item with id: " + itemId + " Not Found");
			log.info("Order-Item with id: " + itemId + " Not Found");

		}

		return response;

	}

	@Override
	public ApiResponse<Void> deleteItemById(long itemId) {
		ApiResponse<Void> response = new ApiResponse<>();
		if(orderItemRepository.existsById(itemId))
		{
			orderItemRepository.deleteById(itemId);

			log.info("Order :"+itemId+" Deleted Successfully!");

			response.setStatusCode(200);
			response.setMessage("Order :"+itemId+" Deleted Successfully!");

		}
		else {
			log.info("Order :" + itemId + " Not Found!");

			response.setStatusCode(404);
			response.setMessage("Order :" + itemId + " Not Found!");
		}
		return response;

	}
}
