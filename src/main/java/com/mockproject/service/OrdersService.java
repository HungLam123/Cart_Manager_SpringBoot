package com.mockproject.service;

import java.util.List;

import com.mockproject.entity.Orders;

public interface OrdersService {

	Orders insert(Orders orders);
	List<Orders> findAll();
	List<Orders> findByUserId(Long userId);
	Orders findById(Long orderId);
}
