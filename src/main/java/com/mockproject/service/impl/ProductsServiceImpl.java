package com.mockproject.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mockproject.entity.Products;
import com.mockproject.repository.ProductsRepo;
import com.mockproject.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	private ProductsRepo repo;
	
//	@Autowired
//	private ProductTypesService productTypesSercice;
//	
//	@Autowired
//	private UnitTypesService unitTypesService;

	@Override
	public List<Products> findAll() {
		// TODO Auto-generated method stub
		return repo.findByIsDeletedAndQuantityGreaterThan(Boolean.FALSE, 0);
//		return repo.findAllAvailable();
	}

	@Override
	public Products findBySlug(String slug) {
		// TODO Auto-generated method stub
		return repo.findBySlug(slug);
	}

	@Override
	public Products findById(Long id) {
		Optional<Products> optional = repo.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public void updateQuantity(Integer newQuantity, Long productId) {
		repo.updateQuantity(newQuantity, productId);
	}

	@Override
	public Page<Products> findAll(int pageSize, int pageNumber) throws Exception {
		// TODO Auto-generated method stub
		if (pageNumber >= 1) {
			return repo.findByIsDeletedAndQuantityGreaterThan(Boolean.FALSE, 0,
					PageRequest.of(pageNumber - 1, pageSize));
		} else {
			throw new Exception("Page number must be greater than 0");
		}

	}

	@Override
	public List<Products> findProducts() {
		return repo.findProducts();
	}

	@Override
	public List<Products> findDienThoai() {
		// TODO Auto-generated method stub
		return repo.findDienThoai();
	}

	@Override
	public List<Products> findLapTop() {
		// TODO Auto-generated method stub
		return repo.findLapTop();
	}

	@Override
	public void save(Products product) {
		repo.save(product);
	}

	@Override
	public Products get(Long id) {
		return repo.findById(id).get();
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	public Products insert(Products products) {
		products.setIsDeleted(Boolean.FALSE);
		return repo.saveAndFlush(products);
	}

	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	@Override
	public void deleteLogical(Long id) {
		repo.updateLogical(id);
	}

	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	@Override
	public void repeatLogical(Long id) {
		repo.updateRepeat(id);
	}

	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	@Override
	public void update(Products product) {
		repo.update(product.getName(), product.getQuantity(), product.getPrice(), product.getDescription(),
				product.getSlug(), product.getProductType(), product.getUnitType(), product.getId());
	}
}
