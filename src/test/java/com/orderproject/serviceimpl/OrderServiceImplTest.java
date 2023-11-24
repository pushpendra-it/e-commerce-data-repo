package com.orderproject.serviceimpl;

import com.orderproject.entity.Order;
import com.orderproject.entity.OrderItems;
import com.orderproject.repo.OrderItemRepo;
import com.orderproject.repo.OrderRepository;
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
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService; //dummy object

    @Mock
    OrderRepository orderRepo;//dummy object
    @Test
    void getOrderList_success() {

        List<Order> orderList = createOrderList();

        Mockito.when(orderRepo.findAll()).thenReturn(orderList);

        ApiResponse<List<Order>> response = orderService.getOrderList();


        Assertions.assertEquals(orderList, response.getData());
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals(orderList.size() + " Orders found", response.getMessage());

        Mockito.verify(orderRepo, Mockito.times(1)).findAll();
    }
    @Test
    void getOrderList_Fail() {


        Mockito.when(orderRepo.findAll()).thenReturn(Collections.emptyList());

        ApiResponse<List<Order>> response = orderService.getOrderList();

        Assertions.assertNull(response.getData());
        Assertions.assertEquals(404, response.getStatusCode(), "status code 400 expected");
        Assertions.assertEquals("No Orders Found", response.getMessage());

        Mockito.verify(orderRepo, Mockito.times(1)).findAll();
    }

    @Test
    void getOrder_success() {

        Order order = get_new_order();
        Mockito.when(orderRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(order)); //if available then return order item
        ApiResponse<Order> response = orderService.getOrder(order.getOrderId()); //calling the service

        //checking test
        Assertions.assertEquals("Order with OrderID: " +order.getOrderId() + " Found!",response.getMessage());
        Assertions.assertEquals(200,response.getStatusCode());
        Assertions.assertEquals(order.getOrderId(),response.getData().getOrderId());

        Mockito.verify(orderRepo,Mockito.times(1)).findById(order.getOrderId());//go to repo and find ...verify //count how many time
    }

    @Test
    void getOrderitem_fail() {

        Order order = get_new_order();
        Mockito.when(orderRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty()); //if available then return order item
        ApiResponse<Order> response = orderService.getOrder(order.getOrderId()); //calling the service

        //checking test
        Assertions.assertEquals("Order with OrderID: " + order.getOrderId() + " Not Found!",response.getMessage());
        Assertions.assertEquals(404,response.getStatusCode());
        assertNull(response.getData());

        Mockito.verify(orderRepo,Mockito.times(1)).findById(order.getOrderId());//go to repo and find ...verify //count how many time
    }

    @Test
    void addNewOrder_success() {

        Order order = createOrder();

        Mockito.when(orderRepo.save(Mockito.any(Order.class))).thenReturn(order);

        ApiResponse<Void> response = orderService.saveOrder(order);

        Assertions.assertEquals(201, response.getStatusCode());

        Mockito.verify(orderRepo, Mockito.times(1)).save(Mockito.any(Order.class));
    }

    @Test
    void updateById_Success() {
        Order order = createOrder();

        Mockito.when(orderRepo.existsById(order.getOrderId())).thenReturn(true);
        Mockito.when(orderRepo.findById(order.getOrderId())).thenReturn(Optional.of(order));

        ApiResponse<Void> response = orderService.updateOrder(order);

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("Order :"+order.getOrderId()+" Updated Successfully!", response.getMessage());

        Mockito.verify(orderRepo).existsById(order.getOrderId());
        Mockito.verify(orderRepo).findById(order.getOrderId());
        Mockito.verify(orderRepo).save(order);
    }

    @Test
    void updateById_Fail() {
        Order order = createOrder();

        Mockito.when(orderRepo.existsById(order.getOrderId())).thenReturn(false);


        ApiResponse<Void> response = orderService.updateOrder(order);

        Assertions.assertEquals(404, response.getStatusCode());
        Assertions.assertEquals("Order :"+order.getOrderId()+" Not Found!", response.getMessage());

        Mockito.verify(orderRepo).existsById(order.getOrderId());

    }

    @Test
    void deleteById_Success() {

        Order order = createOrder();

        Mockito.when(orderRepo.existsById(order.getOrderId())).thenReturn(true);
        Mockito.doNothing().when(orderRepo).deleteById(order.getOrderId());

        ApiResponse<Void> response = orderService.deleteOrder(order.getOrderId());

        Assertions.assertEquals(200, response.getStatusCode());

        Mockito.verify(orderRepo).existsById(order.getOrderId());
        Mockito.verify(orderRepo).deleteById(order.getOrderId());
    }

    @Test
    void deleteItemById_Fail() {


        Order order= createOrder();

        Mockito.when(orderRepo.existsById(order.getOrderId())).thenReturn(false);

        ApiResponse<Void> response = orderService.deleteOrder(order.getOrderId());

        Assertions.assertEquals(404, response.getStatusCode());

        Mockito.verify(orderRepo).existsById(order.getOrderId());

    }

    private Order get_new_order(){

        Order order =new Order(101L,"23-nov-2023");

        return order;
    }


    private Order createOrder() {
        Order order = new Order(101L,"23-nov-2023");
        return order;
    }
    private List<Order> createOrderList() {
        List<Order> orderList= IntStream.range(0, 2).mapToObj(n -> createOrder()).collect(Collectors.toList());

        return orderList;
    }

}