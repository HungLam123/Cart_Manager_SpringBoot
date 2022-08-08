package com.mockproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockproject.entity.ProductTypes;

@Repository
public interface ProductTypesRepo extends JpaRepository<ProductTypes, Long>{

}
