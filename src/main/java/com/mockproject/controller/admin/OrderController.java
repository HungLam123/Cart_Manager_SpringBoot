package com.mockproject.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mockproject.entity.OrderDetails;
import com.mockproject.entity.Orders;
import com.mockproject.repository.OrdersRepo;
import com.mockproject.service.OrderDetailsService;

@Controller
@RequestMapping("/admin/order")
public class OrderController {

	@Autowired
	private OrdersRepo repo;
	
	@Autowired
	private OrderDetailsService orderDetailsService;
	
	@GetMapping("")
	public String doGetOrder(Model model) {
		List<Orders> orders = repo.findAll();
		model.addAttribute("orders", orders);
		List<OrderDetails> orderDetails = orderDetailsService.findAll();
		model.addAttribute("orderDetails", orderDetails);
		return "admin/orderView";
	}
}
