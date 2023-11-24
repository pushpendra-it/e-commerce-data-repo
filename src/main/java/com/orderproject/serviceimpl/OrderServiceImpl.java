package com.orderproject.serviceimpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.orderproject.util.ApiResponse;
import com.orderproject.util.MessageTemplate;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderproject.entity.Order;
import com.orderproject.exception.InvalidDateException;
import com.orderproject.repo.OrderRepository;
import com.orderproject.service.OrderService;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Override
	public ApiResponse<List<Order>> getOrderList() {
		ApiResponse<List<Order>> listResponse = new ApiResponse<>();

		List<Order> orderList = orderRepository.findAll();

		if (orderList.isEmpty()) {
			log.info("No Orders Found");
			listResponse.setStatusCode(404);
			listResponse.setMessage("No Orders Found");
		} else {

			log.info(orderList.size() + " Orders found");
			listResponse.setData(orderList);
			listResponse.setStatusCode(200);
			listResponse.setMessage(orderList.size() + " Orders found");


			}

		return listResponse;

	}

	@Override
	public ApiResponse<Order> getOrder(long id) {
		ApiResponse<Order> orderResponse = new ApiResponse<>();

		Order ordernew = new Order();

		Optional<Order> order = orderRepository.findById(id);
		if (order.isPresent()) {
			log.info("Order with OrderID: " + id + " Found!");
			ordernew.setOrderId(id);
			ordernew.setOrderDate(order.get().getOrderDate());


			orderResponse.setStatusCode(200);
			orderResponse.setMessage("Order with OrderID: " + id + " Found!");

			orderResponse.setData(ordernew);
		} else {
			log.info("Order with OrderID: " + id + " Not Found!");
			orderResponse.setStatusCode(404);
			orderResponse.setMessage("Order with OrderID: " + id + " Not Found!");


		}

		return orderResponse;

	}

	@Override
	public ApiResponse<Void> saveOrder(Order order) {
		ApiResponse response = new ApiResponse();
		if(order.getOrderDate()==null) {
			response.setStatusCode(400);
			response.setMessage(MessageTemplate.DATA_SAVED_FAILED);

			throw new InvalidDateException();
		}else {
			orderRepository.save(order);
			response.setStatusCode(201);
			response.setMessage(MessageTemplate.DATA_SAVED_SUCCSSFULLY);


		}
	return  response;
	}


	@Override
	public ApiResponse<Void> updateOrder(Order order) {
		ApiResponse<Void> response = new ApiResponse<>();


		if (orderRepository.existsById(order.getOrderId())) {
			Order fetchedOrder = orderRepository.findById(order.getOrderId()).get();

			fetchedOrder.setOrderDate(order.getOrderDate());

			orderRepository.save(fetchedOrder);
			log.info("Order :"+order.getOrderId()+" Updated Successfully!");

			response.setStatusCode(200);
			response.setMessage("Order :"+order.getOrderId()+" Updated Successfully!");

		}
		else{
			log.info("Order :"+order.getOrderId()+" Not Found!");
			response.setStatusCode(404);
			response.setMessage("Order :"+order.getOrderId()+" Not Found!");
		}

		return response;
	}

	@Override
	public ApiResponse<Void> deleteOrder(long id) {
		ApiResponse<Void> response = new ApiResponse<>();


		if(orderRepository.existsById(id))
		{
			orderRepository.deleteById(id);

			log.info("Order :"+id+" Deleted Successfully!");

			response.setStatusCode(200);
			response.setMessage("Order :"+id+" Deleted Successfully!");

		}
		else {
			log.info("Order :" + id + " Not Found!");

			response.setStatusCode(404);
			response.setMessage("Order :" + id + " Not Found!");
		}
		return response;

	}


 
	
}
