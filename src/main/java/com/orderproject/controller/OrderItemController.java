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

import com.orderproject.entity.OrderItems;
import com.orderproject.serviceimpl.OrderItemServiceImpl;
import com.orderproject.util.ApiResponse;
import com.orderproject.util.MessageTemplate;
import com.orderproject.util.PathConstant;

@RestController
@RequestMapping(value = PathConstant.ORDERITEM)
public class OrderItemController {


	@Autowired
	private OrderItemServiceImpl orderItemServiceImpl;

	@GetMapping("/health")
	public String helthCheck() {
		return "project running................";
	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API return list of Orders
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@GetMapping(value = PathConstant.GET_ORDERITEMlIST)
	public ResponseEntity<ApiResponse<List<OrderItems>>> getAllOrders() {
		List<OrderItems> orderList = orderItemServiceImpl.getOrderItemList();

		if (!orderList.isEmpty()) {
			ApiResponse<List<OrderItems>> response = new ApiResponse<>(HttpStatus.OK.value(),
					MessageTemplate.DATA_FETCHED_SUCCSSFULLY, orderList);
			return ResponseEntity.ok(response);
		} else {
			ApiResponse<List<OrderItems>> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(),
					MessageTemplate.DATA_FETCHED_FAILED, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API Save an Order
	 * @param order
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@PostMapping(value = PathConstant.SAVE_ORDERITEM)
	public ResponseEntity<ApiResponse<OrderItems>> saveItem(@RequestBody OrderItems item) {
		OrderItems savedItem = orderItemServiceImpl.saveOrderItem(item);
		ApiResponse<OrderItems> response = new ApiResponse<>();

		if (savedItem != null) {
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setMessage(MessageTemplate.DATA_SAVED_SUCCSSFULLY);
			response.setData(savedItem);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} else {
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMessage(MessageTemplate.DATA_SAVED_FAILED);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API return single order with their details
	 * @param id
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@GetMapping(value = PathConstant.GET_ORDERITEM_BYID)
	public ResponseEntity<ApiResponse<OrderItems>> getOrder(@PathVariable(value = "id") int id) {
		OrderItems orderItem = orderItemServiceImpl.getOrderItem(id);
		ApiResponse<OrderItems> response = new ApiResponse<>();

		if (orderItem != null) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage(MessageTemplate.DATA_FETCHED_SUCCSSFULLY);
			response.setData(orderItem);
			return ResponseEntity.ok(response);
		} else {
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setMessage(MessageTemplate.DATA_FETCHED_FAILED);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API Update Order details
	 * @param order
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@PutMapping(value = PathConstant.UPDATE_ORDERITEM)
	public ResponseEntity<ApiResponse<OrderItems>> updateOrderItem(@RequestBody OrderItems orderItems) {
		OrderItems updatedOrderItem = orderItemServiceImpl.updateOrderItem(orderItems);
		ApiResponse<OrderItems> response = new ApiResponse<>();

		if (updatedOrderItem != null) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage(MessageTemplate.DATA_UPDATED_SUCCSSFULLY);
			response.setData(updatedOrderItem);
			return ResponseEntity.ok(response);
		} else {
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setMessage(MessageTemplate.DATA_UPDATED_FAILED);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API delete an order by orderID
	 * @param id
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@DeleteMapping(value = PathConstant.DELETE_ORDERITEM)
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable int id) {
		boolean deletionStatus = orderItemServiceImpl.deleteOrderItem(id);
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
