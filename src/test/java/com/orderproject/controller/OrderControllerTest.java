package com.orderproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderproject.entity.Order;
import com.orderproject.entity.OrderItems;
import com.orderproject.serviceimpl.OrderItemServiceImpl;
import com.orderproject.serviceimpl.OrderServiceImpl;
import com.orderproject.util.ApiResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderServiceImpl orderService;
    @Test
    void getAllOrders() throws Exception {
        List<Order> orderList = createOrderList();
        ApiResponse<List<Order>> response = createNewResponse(200, orderList.size() + " Orders found", orderList);

        // Mock behavior of service method
        Mockito.when(orderService.getOrderList()).thenReturn(response);

        // When & Then

        mockMvc.perform(MockMvcRequestBuilders.get("/order/get-order-list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(response.getMessage()))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].orderDate").value(response.getData().get(0).getOrderDate())); // Check the first item's name

    }

    @Test
    void saveItem() throws Exception {

        // Create a sample OrderItems object
        Order order = createNewOrder();

        // Create a sample ApiResponse object
        ApiResponse<Void> response = createNewResponse(201, "Order Added Successfully: " + order.getOrderId(), null);

        // Mock behavior of the service method
        Mockito.when(orderService.saveOrder(Mockito.any(Order.class))).thenReturn(response);

        // Perform the mock HTTP request using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.post("/order/save-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated()) // Expecting a HTTP 201 response
                .andExpect(jsonPath("$.message")
                        .value(response.getMessage())); // Checking the response message
    }

    @Test
    void getOrder() throws Exception {
        Order order = createNewOrder();
        ApiResponse<Order> response = createNewResponse(200, "Item with itemId: " + order.getOrderId() + " found", order);

        // Mock behaviour of service method
        Mockito.when(orderService.getOrder(Mockito.anyLong())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/order/order/{id}", order.getOrderId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message")
                        .value(response.getMessage()));

    }

    @Test
    void updateOrder() throws Exception {
        // Create a new order item for testing
        Order order = createNewOrder();

        // Create a response message for successful item update
        ApiResponse<Void> response = createNewResponse(200, "Order-Item with id: " + order.getOrderId() + " Updated Successfully", null);

        // Mock the behavior of the service method to update the order item
        Mockito.when(orderService.updateOrder(Mockito.any(Order.class)))
                .thenReturn(response);

        // Perform the PUT request using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.put("/order/update-order/{id}", order.getOrderId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message")
                        .value(response.getMessage()));

    }

    @Test
    void deleteOrder() throws Exception {

        // Create a sample order item
        Order order = createNewOrder();

        // Mock service method response
        ApiResponse<Void> response = createNewResponse(200, "Order-item with ID: " + order.getOrderId()+ " Deleted Successfully!", null);
        Mockito.when(orderService.deleteOrder(Mockito.anyLong())).thenReturn(response);

        // Perform the delete request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete-order/{id}", order.getOrderId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Assert the HTTP status is 200
                .andExpect(jsonPath ("$.statusCode").value(response.getStatusCode()))
                .andExpect(jsonPath("$.message")
                        .value(response.getMessage()));// Assuming an empty response body for a successful delete

    }

    private Order createNewOrder() {
        Order order = new Order(101L,"23-nov-2023");
        return order;
    }
    private Order createOrder() {
        Order order= new Order(101L,"23-nov-2023");
        return order;
    }

    private List<Order> createOrderList() {

        List<Order> orderList = IntStream.range(0, 2)
                .mapToObj(n -> createNewOrder()).collect(Collectors.toList());

        return orderList;
    }
    private <T> ApiResponse<T> createNewResponse(int code, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();


        response.setStatusCode(code);
        response.setMessage(message);

        response.setData(data);

        return response;
    }

}