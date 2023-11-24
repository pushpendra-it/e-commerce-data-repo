package com.orderproject.controller;

import com.orderproject.entity.Order;
import com.orderproject.entity.OrderItems;
import com.orderproject.serviceimpl.OrderItemServiceImpl;
import com.orderproject.util.ApiResponse;
import com.orderproject.util.MessageTemplate;
import com.orderproject.util.PathConstant;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = PathConstant.ORDERITEM)
public class OrderItemController {


	@Autowired
	private OrderItemServiceImpl orderItemServiceImpl;


	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API return list of Orders
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@Operation(summary = "Get All Order-Item", description = "Get All Order-items Order-items exist, else will return 'not found'")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code =200, message = "Items List retrieved successfully"),
			@io.swagger.annotations.ApiResponse(code = 404, message = "No valid Order-Item found")
	})
	@GetMapping(value = PathConstant.GET_ORDERITEMlIST)
	public ResponseEntity<ApiResponse<List<OrderItems>>> getAllOrders() {
		ApiResponse<List<OrderItems>> orderResponse = orderItemServiceImpl.getAllOrderItems();
		HttpStatus httpStatus = HttpStatus.valueOf(orderResponse.getStatusCode());

		return new ResponseEntity<>(orderResponse, httpStatus);
	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API Save an Order
	 * @param
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@PostMapping(value = PathConstant.SAVE_ORDERITEM)
	public ResponseEntity<?> saveItem(@RequestBody OrderItems items) {

		ApiResponse<Void> response = orderItemServiceImpl.addNewOrderItem(items);
		HttpStatus status = HttpStatus.valueOf(response.getStatusCode());

		return  new ResponseEntity<>(response,status);

	}


	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API return single order with their details
	 * @param id
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@GetMapping(value = PathConstant.GET_ORDERITEM_BYID)
	public ResponseEntity<ApiResponse<OrderItems>> getOrder(@PathVariable(value = "id") int id) {
		ApiResponse<OrderItems> orderResponse = orderItemServiceImpl.getOrderitem(id);
		HttpStatus httpStatus = HttpStatus.valueOf(orderResponse.getStatusCode());

		return new ResponseEntity<>(orderResponse, httpStatus);

	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API Update Order details
	 * @param
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@PutMapping(value = PathConstant.UPDATE_ORDERITEM)
	public ResponseEntity<?> updateOrderItem(@PathVariable(name = "id") Long orderId, @RequestBody OrderItems order) {
		ApiResponse<Void> response = orderItemServiceImpl.updateItemById(orderId,order);
		HttpStatus httpStatus = HttpStatus.valueOf(response.getStatusCode());

		return new ResponseEntity<>(response, httpStatus);
	}

	/**
	 * @author Pushpendra
	 * @apiNote : This Rest API delete an order by orderID
	 * @param id
	 * @return Response entity with(Status code,message,data with expected response)
	 */
	@DeleteMapping(value = PathConstant.DELETE_ORDERITEM)
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable int id) {
		ApiResponse<Void> response = orderItemServiceImpl.deleteItemById(id);
		HttpStatus httpStatus = HttpStatus.valueOf(response.getStatusCode());

		return new ResponseEntity<>(response, httpStatus);
	}

}
