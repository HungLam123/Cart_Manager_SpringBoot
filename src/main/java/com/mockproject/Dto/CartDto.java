package com.mockproject.Dto;

import java.io.Serializable;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CartDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String address;
	private String phone;
	private Double totalPrice = 0D;
	private Integer totalQuantity = 0;
	
	private HashMap<Long, CartDetailDto> details = new HashMap<>();

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}


	public HashMap<Long, CartDetailDto> getDetails() {
		return details;
	}

	public void setDetails(HashMap<Long, CartDetailDto> details) {
		this.details = details;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
