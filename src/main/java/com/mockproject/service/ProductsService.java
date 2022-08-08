package com.mockproject.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mockproject.entity.Products;

public interface ProductsService {
	List<Products> findProducts();
	List<Products> findAll();
	List<Products> findDienThoai();
	List<Products> findLapTop();
	Page<Products> findAll(int pageSize, int pageNumber) throws Exception;
	Products findBySlug(String slug);
	Products findById(Long id);
	void updateQuantity(Integer newQuantity, Long productId);
	
	//
	void save(Products product);
	Products get(Long id);
	void delete(Long id);
	
	Products insert(Products products);
	void deleteLogical(Long id);
	void repeatLogical(Long id);
	void update(Products product);
}
