package com.mockproject.service.impl;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockproject.Dto.CartDetailDto;
import com. mockproject.Dto.CartDto;
import com.mockproject.entity.Orders;
import com.mockproject.entity.Products;
import com.mockproject.entity.Users;
import com.mockproject.service.CartService;
import com.mockproject.service.OrderDetailsService;
import com.mockproject.service.OrdersService;
import com.mockproject.service.ProductsService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private OrderDetailsService orderDetailService;

	@Autowired
	private ProductsService productsService;

	@Override
	public CartDto updateCart(CartDto cart, Long productId, Integer quantity, Boolean isReplace) {

		// 1-Them Moi SP
		// 2-Update :
		// 2-1. Cong Don
		// 2-2. Replace
		// 3 - Delete : update quantity Ve 0

		Products product = productsService.findById(productId);

		HashMap<Long, CartDetailDto> details = cart.getDetails();

		if (!details.containsKey(productId)) {
			// Them Moi
			CartDetailDto newDetail = createNewCartDetail(product, quantity);
			details.put(productId, newDetail);
		} else if (quantity > 0) {
			// Update
			if (isReplace) {
				// Thay The
				details.get(productId).setQuantity(quantity);
			} else {
				// Cong Don
				Integer oldQuantity = details.get(productId).getQuantity();
				Integer newQuantity = oldQuantity + quantity;
				details.get(productId).setQuantity(newQuantity);
			}
		} else {
			// Delete
			details.remove(productId);
		}

		cart.setTotalQuantity(getTotalQuantity(cart));
		cart.setTotalPrice(getTotalPrice(cart));

		return cart;
	}

	@Override
	public Integer getTotalQuantity(CartDto cart) {
		Integer totalQuantity = 0;
		HashMap<Long, CartDetailDto> details = cart.getDetails();
		for (CartDetailDto cartDetail : details.values()) {
			totalQuantity += cartDetail.getQuantity();
		}
		return totalQuantity;
	}

	@Override
	public Double getTotalPrice(CartDto cart) {
		Double totalPrice = 0D;
		HashMap<Long, CartDetailDto> details = cart.getDetails();
		for (CartDetailDto cartDetail : details.values()) {
			totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
		}
		return totalPrice;
	}

	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	@Override
	public void insert(CartDto cart, Users user, String address, String phone) throws Exception{
		
		if(StringUtils.isAnyBlank(address, phone)){
			throw new Exception("address or phone must be not null or empty or whitespace");
		}
		
		// insert vào order
		Orders order = new Orders();
		order.setUser(user);
		order.setAddress(address);
		order.setPhone(phone);

		Orders orderResponse = ordersService.insert(order);
		// duyệt hashmap để insert lần lượt vào orderDetail
		// trong lúc duyệt hashmap qua từng SP => đi update quantity cho từng sản phẩm
		for (CartDetailDto cartDetail : cart.getDetails().values()) {
			Products product = productsService.findById(cartDetail.getProductId());
			if (product.getQuantity() >= cartDetail.getQuantity()) {
				cartDetail.setOrderId(orderResponse.getId());
				orderDetailService.insert(cartDetail);

				// update Quantity
				Integer newQuantity = product.getQuantity() - cartDetail.getQuantity();
				productsService.updateQuantity(newQuantity, cartDetail.getProductId());
			} else {
				throw new Exception("Order quantity must be less than current product quantity");
			}
		}
	}

	private CartDetailDto createNewCartDetail(Products product, Integer quantity) {
		CartDetailDto cartDetail = new CartDetailDto();
		cartDetail.setProductId(product.getId());
		cartDetail.setPrice(product.getPrice());
		cartDetail.setQuantity(quantity);
		cartDetail.setSlug(product.getSlug());
		cartDetail.setName(product.getName());
		cartDetail.setImgUrl(product.getImgUrl());
		return cartDetail;
	}
}
