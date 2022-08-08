package com.mockproject.service;

import java.util.List;

import com.mockproject.Dto.CartDetailDto;
import com.mockproject.entity.OrderDetails;

public interface OrderDetailsService {
	void insert(CartDetailDto cartDetailDto);
	List<OrderDetails> findAll();
	List<OrderDetails> findByOrderId(Long orderId);
}
