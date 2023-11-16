package com.orderproject.controller;

import java.util.List;

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
	public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
		List<Order> orderList = orderServiceImpl.getOrderList();

		if (!orderList.isEmpty()) {
			ApiResponse<List<Order>> response = new ApiResponse<>(HttpStatus.OK.value(),
					MessageTemplate.DATA_FETCHED_SUCCSSFULLY, orderList);
			return ResponseEntity.ok(response);
		} else {
			ApiResponse<List<Order>> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), MessageTemplate.DATA_FETCHED_FAILED,
					null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
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

		try {
			Order order1 = orderServiceImpl.saveOrder(order);
			return new ResponseEntity<>(order1, HttpStatus.CREATED);
		} catch (InvalidDateException ex) {
			// TODO: handle exception
			return new ResponseEntity<>("not saved / invalid date", HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API return single order with their details
	 * @param id
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@GetMapping(value = PathConstant.GET_ORDER_BYID)
	public ResponseEntity<ApiResponse<Order>> getOrder(@PathVariable(value = "id") int id) {
		Order order = orderServiceImpl.getOrder(id);

		if (order != null) {
			ApiResponse<Order> response = new ApiResponse<>(HttpStatus.OK.value(), MessageTemplate.DATA_FETCHED_SUCCSSFULLY,
					order);
			return ResponseEntity.ok(response);
		} else {
			ApiResponse<Order> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), MessageTemplate.DATA_FETCHED_FAILED, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API Update Order details 
	 * @param order
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@PutMapping(value = PathConstant.UPDATE_ORDER)
	public ResponseEntity<ApiResponse<Order>> updateOrder(@RequestBody Order order) {
		// System.out.println(">>>>>>>>>IIIIIIIIIIIII"+order);// use
		Order updatedOrder = orderServiceImpl.updateOrder(order);
		ApiResponse<Order> response = new ApiResponse<>();

		if (updatedOrder != null) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage(MessageTemplate.DATA_UPDATED_SUCCSSFULLY);
			response.setData(updatedOrder);
			return ResponseEntity.ok(response);
		} else {
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMessage(MessageTemplate.DATA_UPDATED_FAILED);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API delete an order by orderID
	 * @param id
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@DeleteMapping(value = PathConstant.DELETE_ORDER)
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable int id) {
		boolean deletionStatus = orderServiceImpl.deleteOrder(id);
		ApiResponse response = new ApiResponse();

		if (deletionStatus) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage(MessageTemplate.DATA_DELETED_SUCCSSFULLY);
			return ResponseEntity.ok(response);
		} else {
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setMessage(MessageTemplate.DATA_DELETE_FAILED);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

}
