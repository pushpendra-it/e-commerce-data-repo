package com.orderproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderproject.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	
}
