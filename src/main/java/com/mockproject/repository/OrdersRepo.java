package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mockproject.entity.Orders;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long>{
	@Query(value = "select * from orders", nativeQuery =  true)
	List<Orders> findAll();
	
	@Query(value = "select * from orders where userId = ?1", nativeQuery = true)
	List<Orders> findByUserID(Long userId);
}
