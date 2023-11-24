package com.orderproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderproject.entity.Order;
import com.orderproject.entity.OrderItems;
import com.orderproject.serviceimpl.OrderItemServiceImpl;
import com.orderproject.util.ApiResponse;
import com.orderproject.util.MessageTemplate;
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
@WebMvcTest(OrderItemController.class)
class OrderItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderItemServiceImpl orderItemsService;



    @Test
    void getAllOrders() throws Exception {
        // Given
        List<OrderItems> orderItemList = createOrderItemsList();
        ApiResponse<List<OrderItems>> response = createNewResponse(200, orderItemList.size() + " Order-Items found!", orderItemList);

        // Mock behavior of service method
        Mockito.when(orderItemsService.getAllOrderItems()).thenReturn(response);

        // When & Then

            mockMvc.perform(MockMvcRequestBuilders.get("/orderitem/get-orderitem-list"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(response.getMessage()))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data[0].itemName").value(orderItemList.get(0).getItemName())); // Check the first item's name

    }


    @Test
    void saveItem() throws Exception {
        // Create a sample OrderItems object
        OrderItems orderItems = createNewOrderItem();

        // Create a sample ApiResponse object
        ApiResponse<Void> response = createNewResponse(201, "Order Item Added Successfully: " + orderItems.getItemId(), null);

        // Mock behavior of the service method
        Mockito.when(orderItemsService.addNewOrderItem(Mockito.any(OrderItems.class))).thenReturn(response);

        // Perform the mock HTTP request using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.post("/orderitem/save-orderitem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItems)))
                .andExpect(status().isCreated()) // Expecting a HTTP 201 response
                .andExpect(jsonPath("$.message")
                        .value(response.getMessage())); // Checking the response message
    }


    @Test
    void getOrder() throws Exception {
        OrderItems orderItem = createNewOrderItem();
        ApiResponse<OrderItems> response = createNewResponse(200, "Item with itemId: " + orderItem.getItemId() + " found", orderItem);

        // Mock behaviour of service method
        Mockito.when(orderItemsService.getOrderitem(Mockito.anyLong())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/orderitem/orderitem/{id}", orderItem.getItemId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message")
                        .value(response.getMessage()));


    }

    @Test
    void updateOrderItem() throws Exception {
        // Create a new order item for testing
        OrderItems orderItem = createNewOrderItem();

        // Create a response message for successful item update
        ApiResponse<Void> response = createNewResponse(200, "Order-Item with id: " + orderItem.getItemId() + " Updated Successfully", null);

        // Mock the behavior of the service method to update the order item
        Mockito.when(orderItemsService.updateItemById(Mockito.anyLong(), Mockito.any(OrderItems.class)))
                .thenReturn(response);

        // Perform the PUT request using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.put("/orderitem/update-orderitem/{id}", orderItem.getItemId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItem)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message")
                        .value(response.getMessage()));
    }


    @Test
    void deleteOrder() throws Exception {
        // Create a sample order item
        OrderItems orderItem = createNewOrderItem();

        // Mock service method response
        ApiResponse<Void> response = createNewResponse(200, "Order-item with ID: " + orderItem.getItemId() + " Deleted Successfully!", null);
        Mockito.when(orderItemsService.deleteItemById(Mockito.anyLong())).thenReturn(response);

        // Perform the delete request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/orderitem/delete-orderitem/{id}", orderItem.getItemId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Assert the HTTP status is 200
                .andExpect(jsonPath ("$.statusCode").value(response.getStatusCode()))
                .andExpect(jsonPath("$.message")
                        .value(response.getMessage()));// Assuming an empty response body for a successful delete
                  }


    private OrderItems createNewOrderItem() {
        OrderItems orderItem = new OrderItems(101L,"mobile",1, BigDecimal.valueOf(25000.00));
        return orderItem;
    }
    private OrderItems createOrderItem() {
        OrderItems orderItem = new OrderItems(501, "lunchbox", 2, BigDecimal.valueOf(25000.00));
        return orderItem;
    }

    private List<OrderItems> createOrderItemsList() {

        List<OrderItems> orderItemList = IntStream.range(0, 2)
                .mapToObj(n -> createNewOrderItem()).collect(Collectors.toList());

        return orderItemList;
    }
    private <T> ApiResponse<T> createNewResponse(int code, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();


        response.setStatusCode(code);
        response.setMessage(message);

        response.setData(data);

        return response;
    }

}