package com.mockproject.service;

import java.util.List;

import com.mockproject.entity.ProductTypes;

public interface ProductTypesService {
	ProductTypes findById(Long id);
	List<ProductTypes> findAll();
}
