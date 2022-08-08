package com.mockproject.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mockproject.entity.Products;
import com.mockproject.service.ProductsService;

@RestController
public class ProductsAPI {
	@Autowired
	ProductsService productsService;

//	@GetMapping("/products")
//	public ResponseEntity< List<Products> > doGetProducts(){
//		List<Products> listProducts = productsService.findAll();
//		return ResponseEntity.ok(listProducts);
//	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Products> get(@PathVariable("id") Long id) {
		try {
			Products product = productsService.get(id);	
			return new ResponseEntity<Products>(product, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Products>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/api/v1/products/create")
	public void add(@RequestBody Products product) {
		productsService.save(product);
	}
	
	@PutMapping("/api/v1/products/update/{id}")
	public ResponseEntity<?> update(@RequestBody Products product, @PathVariable("id") Long id) {
		try {
			Products exitsProducts = productsService.get(id);
			productsService.save(product);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/api/v1/products/delete/{id}")
	public void delete(@PathVariable("id") Long id) {
		productsService.delete(id);
	}
}
