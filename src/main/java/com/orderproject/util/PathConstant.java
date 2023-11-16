package com.orderproject.util;

public class PathConstant {

	public static final String HEALTH = "/health";
	public static final String ORDER = "orders";
	public static final String GET_ORDERlIST = "/get-order-list"; // Get a list of all orders
	public static final String SAVE_ORDER = "/save-order"; // Create a new order
	public static final String GET_ORDER_BYID = "/order/{id}"; // Get an order by ID
	public static final String UPDATE_ORDER = "/update-order/{id}"; // Update an existing order
	public static final String DELETE_ORDER = "/delete-order/{id}"; // Delete an order by ID

	public static final String ORDERITEM = "/orderitem";
	public static final String GET_ORDERITEMlIST = "/get-orderitem-list";// Get a list of all order items
	public static final String SAVE_ORDERITEM = "/save-orderitem";// Create a new order item
	public static final String GET_ORDERITEM_BYID = "/orderitem/{id}";// Get an order item by ID
	public static final String UPDATE_ORDERITEM = "/update-orderitem/{id}";// Update an existing order item
	public static final String DELETE_ORDERITEM = "/delete-orderitem/{id}";// Delete an order item by ID

}
