package com.orderproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderproject.entity.OrderItems;


public interface OrderItemRepo extends JpaRepository<OrderItems, Long> {

}
