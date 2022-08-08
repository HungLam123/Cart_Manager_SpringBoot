package com.mockproject.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockproject.entity.Orders;
import com.mockproject.repository.OrdersRepo;
import com.mockproject.service.OrdersService;

@Service
public class OrdersServiceImpl implements OrdersService{

	@Autowired
	private OrdersRepo repo;
	
	@Override
	public Orders insert(Orders orders) {
		// TODO Auto-generated method stub
		return repo.saveAndFlush(orders);
	}

	@Override
	public List<Orders> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public List<Orders> findByUserId(Long userId) {
		// TODO Auto-generated method stub
		return repo.findByUserID(userId);
	}

	@Override
	public Orders findById(Long orderId) {
		Optional<Orders> optional = repo.findById(orderId);
		return optional.isPresent() ? optional.get() : null;
	}
}
