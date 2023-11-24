package com.orderproject.serviceimpl;

import com.orderproject.entity.OrderItems;
import com.orderproject.repo.OrderItemRepo;
import com.orderproject.util.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) //use this
class OrderItemServiceImplTest {
    @InjectMocks
    OrderItemServiceImpl orderItemService; //dummy object

    @Mock
    OrderItemRepo orderItemRepo;//dummy object
    @Test
    void getOrderitem_success() {

        OrderItems orderItems = get_new_order();
        Mockito.when(orderItemRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(orderItems)); //if available then return order item
        ApiResponse<OrderItems> response = orderItemService.getOrderitem(orderItems.getItemId()); //calling the service

        //checking test
        Assertions.assertEquals("Item with itemId: " + orderItems.getItemId() + " found",response.getMessage());
        Assertions.assertEquals(200,response.getStatusCode());
        Assertions.assertEquals(orderItems,response.getData());

        Mockito.verify(orderItemRepo,Mockito.times(1)).findById(orderItems.getItemId());//go to repo and find ...verify //count how many time
    }

    @Test
    void getOrderitem_fail() {

        OrderItems orderItems = get_new_order();
        Mockito.when(orderItemRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty()); //if available then return order item
        ApiResponse<OrderItems> response = orderItemService.getOrderitem(orderItems.getItemId()); //calling the service

        //checking test
        Assertions.assertEquals("item with itemId: " + orderItems.getItemId() + " not found!",response.getMessage());
        Assertions.assertEquals(404,response.getStatusCode());
        assertNull(response.getData());

        Mockito.verify(orderItemRepo,Mockito.times(1)).findById(orderItems.getItemId());//go to repo and find ...verify //count how many time
    }

    @Test
    void getAllOrderItems_Success() {

        List<OrderItems> orderItemList = createOrderItemsList();

        Mockito.when(orderItemRepo.findAll()).thenReturn(orderItemList);

        ApiResponse<List<OrderItems>> response = orderItemService.getAllOrderItems();


        Assertions.assertEquals(orderItemList, response.getData());
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals(orderItemList.size() + " Order-Items found!", response.getMessage());

        Mockito.verify(orderItemRepo, Mockito.times(1)).findAll();
    }

    @Test
    void getAllOrderItems_Fail() {


        Mockito.when(orderItemRepo.findAll()).thenReturn(Collections.emptyList());

        ApiResponse<List<OrderItems>> response = orderItemService.getAllOrderItems();

        Assertions.assertNull(response.getData());
        Assertions.assertEquals(404, response.getStatusCode(), "status code 400 expected");
        Assertions.assertEquals("No Order-Items found", response.getMessage());

        Mockito.verify(orderItemRepo, Mockito.times(1)).findAll();
    }
    @Test
    void addNewOrderItem_success() {

        OrderItems orderItem = createOrderItem();

        Mockito.when(orderItemRepo.save(Mockito.any(OrderItems.class))).thenReturn(orderItem);

        ApiResponse<Void> response = orderItemService.addNewOrderItem(orderItem);

        Assertions.assertEquals(201, response.getStatusCode());

        Mockito.verify(orderItemRepo, Mockito.times(1)).save(Mockito.any(OrderItems.class));
    }

    @Test
    void updateItemById_Success() {
        OrderItems orderItem = createOrderItem();

        Mockito.when(orderItemRepo.existsById(orderItem.getItemId())).thenReturn(true);
        Mockito.when(orderItemRepo.findById(orderItem.getItemId())).thenReturn(Optional.of(orderItem));

        ApiResponse<Void> response = orderItemService.updateItemById(orderItem.getItemId(), orderItem);

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("Order-Item with id: " + orderItem.getItemId() + " Updated Successfully", response.getMessage());

        Mockito.verify(orderItemRepo).existsById(orderItem.getItemId());
        Mockito.verify(orderItemRepo).findById(orderItem.getItemId());
        Mockito.verify(orderItemRepo).save(orderItem);
    }

    @Test
    void updateItemById_Fail() {
        OrderItems orderItem = createOrderItem();

        Mockito.when(orderItemRepo.existsById(orderItem.getItemId())).thenReturn(false);


        ApiResponse<Void> response = orderItemService.updateItemById(orderItem.getItemId(), orderItem);

        Assertions.assertEquals(400, response.getStatusCode());
        Assertions.assertEquals("Order-Item with id: " + orderItem.getItemId() + " Not Found", response.getMessage());

        Mockito.verify(orderItemRepo).existsById(orderItem.getItemId());

    }

    @Test
    void deleteItemById_Success() {

        OrderItems orderItem = createOrderItem();

        Mockito.when(orderItemRepo.existsById(orderItem.getItemId())).thenReturn(true);
        Mockito.doNothing().when(orderItemRepo).deleteById(orderItem.getItemId());

        ApiResponse<Void> response = orderItemService.deleteItemById(orderItem.getItemId());

        Assertions.assertEquals(200, response.getStatusCode());

        Mockito.verify(orderItemRepo).existsById(orderItem.getItemId());
        Mockito.verify(orderItemRepo).deleteById(orderItem.getItemId());
    }

    @Test
    void deleteItemById_Fail() {


        OrderItems orderItem = createOrderItem();

        Mockito.when(orderItemRepo.existsById(orderItem.getItemId())).thenReturn(false);

        ApiResponse<Void> response = orderItemService.deleteItemById(orderItem.getItemId());

        Assertions.assertEquals(404, response.getStatusCode());

        Mockito.verify(orderItemRepo).existsById(orderItem.getItemId());

    }
    private OrderItems get_new_order(){

        OrderItems orderItems =new OrderItems(101L,"mobile",1, BigDecimal.valueOf(25000.00));

        return orderItems;
    }

    private OrderItems createOrderItem() {
        OrderItems orderItem = new OrderItems(501, "lunchbox", 2, BigDecimal.valueOf(25000.00));
        return orderItem;
    }

    private List<OrderItems> createOrderItemsList() {
        List<OrderItems> orderItemList = IntStream.range(0, 2).mapToObj(n -> createOrderItem()).collect(Collectors.toList());

        return orderItemList;
    }

}