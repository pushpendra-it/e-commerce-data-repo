package com.orderproject.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderproject.entity.OrderItems;
import com.orderproject.repo.OrderItemRepo;
import com.orderproject.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	OrderItemRepo itemRepository;

	@Override
	public List<OrderItems> getOrderItemList() {
		return this.itemRepository.findAll();
	}

	@Override
	public OrderItems getOrderItem(long id) {
		Optional<OrderItems> optionalEOptional = itemRepository.findById(id);
		OrderItems getOrderItem = null;

		if (optionalEOptional.isPresent()) {
			getOrderItem = optionalEOptional.get();
		}
		return getOrderItem;
	}

	@Override
	public OrderItems saveOrderItem(OrderItems orderItem) {
		return itemRepository.save(orderItem);
	}

	@Override
	public OrderItems updateOrderItem(OrderItems orderItem) {
		Optional<OrderItems> optionalEOptional = itemRepository.findById(orderItem.getItemId());
		OrderItems updateOrderItem = null;

		if (optionalEOptional.isPresent()) {
			updateOrderItem = optionalEOptional.get();
			updateOrderItem.setItemName(orderItem.getItemName());

			updateOrderItem = itemRepository.save(updateOrderItem);
		}
		return updateOrderItem;
	}

	@Override
	public boolean deleteOrderItem(long id) {
		Optional<OrderItems> orderItemOptional = itemRepository.findById(id);

		if (orderItemOptional.isPresent()) {
			itemRepository.deleteById(id);
			return true; // Deletion successful
		} else {
			return false; // Order item with given ID not found
		}

	}
	
	
}
