package com.orderproject.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderproject.entity.Order;
import com.orderproject.exception.InvalidDateException;
import com.orderproject.repo.OrderRepository;
import com.orderproject.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Override
	public List<Order> getOrderList() {
		return this.orderRepository.findAll();

	}

	@Override
	public Order getOrder(long id) {
		Optional<Order> optionalEOptional = orderRepository.findById(id);
		Order updateOrder = null;

		if (optionalEOptional.isPresent()) {
			updateOrder = optionalEOptional.get();
		}
		return updateOrder;

	}

	@Override
	public Order saveOrder(Order order) {
		if(order.getOrderDate()==null) {
			throw new InvalidDateException();
		}else {
		return orderRepository.save(order);
		}	}

	@Override
	public Order updateOrder(Order order) {
		Optional<Order> optionalEOptional = orderRepository.findById(order.getOrderId());
		Order updateOrder = null;

		if (optionalEOptional.isPresent()) {
			updateOrder = optionalEOptional.get();
			updateOrder.setOrderDate(order.getOrderDate());
			updateOrder = orderRepository.save(updateOrder);
		}
		return updateOrder;
	}

	@Override
	public boolean deleteOrder(long id) {
		Optional<Order> orderOptional = orderRepository.findById(id);

		if (orderOptional.isPresent()) {
			orderRepository.deleteById(id);
			return true;
		} else {
			return false;
		}

	}
 
	
}
