package com.orderproject.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderproject.entity.Order;
import com.orderproject.exception.InvalidDateException;
import com.orderproject.serviceimpl.OrderServiceImpl;
import com.orderproject.util.ApiResponse;
import com.orderproject.util.MessageTemplate;
import com.orderproject.util.PathConstant;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = PathConstant.ORDER)
@Slf4j
public class OrderController {


	@Autowired
	private OrderServiceImpl orderServiceImpl;
	
	@GetMapping(value = PathConstant.HEALTH)
	public String helthCheck() {
		return "project running................";
	}

	
	// OrderList Controller
	@Operation(tags = "Get Orders", description = "Get All Orders", responses = {
			// TODO

	})
	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API return list of Orders
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@GetMapping(value = PathConstant.GET_ORDERlIST)
	public ResponseEntity<?> getAllOrders() {
		ApiResponse<List<Order>> orderResponse = (ApiResponse<List<Order>>) orderServiceImpl.getOrderList();
		HttpStatus httpStatus = HttpStatus.valueOf(orderResponse.getStatusCode());

		return new ResponseEntity<>(orderResponse, httpStatus);
	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API Save an Order 
	 * @param order
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	// Custom exception query suggestion ??
	@PostMapping(value = PathConstant.SAVE_ORDER)
	public ResponseEntity<?> saveItem(@RequestBody Order order) {

		ApiResponse<Void> response = orderServiceImpl.saveOrder(order);
		HttpStatus status = HttpStatus.valueOf(response.getStatusCode());

		return  new ResponseEntity<>(response,status);

	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API return single order with their details
	 * @param id
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@GetMapping(value = PathConstant.GET_ORDER_BYID)
	public ResponseEntity<?> getOrder(@PathVariable(value = "id") int id) {

		ApiResponse<Order> orderResponse = orderServiceImpl.getOrder(id);
		HttpStatus httpStatus = HttpStatus.valueOf(orderResponse.getStatusCode());

		return new ResponseEntity<>(orderResponse, httpStatus);

	}
	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API Update Order details 
	 * @param order
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@PutMapping(value = PathConstant.UPDATE_ORDER)
	public ResponseEntity<?> updateOrder(@RequestBody Order order) {

		ApiResponse<Void> response = orderServiceImpl.updateOrder(order);
		HttpStatus httpStatus = HttpStatus.valueOf(response.getStatusCode());

		return new ResponseEntity<>(response, httpStatus);
	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API delete an order by orderID
	 * @param id
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@DeleteMapping(value = PathConstant.DELETE_ORDER)
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable int id) {
		ApiResponse<Void> response = orderServiceImpl.deleteOrder(id);
		HttpStatus httpStatus = HttpStatus.valueOf(response.getStatusCode());

		return new ResponseEntity<>(response, httpStatus);
	}

}
