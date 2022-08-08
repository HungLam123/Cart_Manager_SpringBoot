package com.mockproject.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockproject.Dto.CartDetailDto;
import com.mockproject.entity.OrderDetails;
import com.mockproject.repository.OrdersDetailsRepo;
import com.mockproject.service.OrderDetailsService;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService{

	@Autowired
	private OrdersDetailsRepo repo;
	
	@Override
	public void insert(CartDetailDto cartDetailDto) {
		repo.insert(cartDetailDto);
	}

	@Override
	public List<OrderDetails> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public List<OrderDetails> findByOrderId(Long orderId) {
		// TODO Auto-generated method stub
		return repo.findByOrderId(orderId);
	}

}
