package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mockproject.Dto.CartDetailDto;
import com.mockproject.entity.OrderDetails;

@Repository
public interface OrdersDetailsRepo extends JpaRepository<OrderDetails, Long>{
	
	@Modifying(clearAutomatically = true) //khi update thì những thứ liên quan sẽ update theo
	@Query(value= "insert into order_details(orderId, productId, price, quantity)"
			+ " values(:#{#dto.orderId}, :#{#dto.productId}, :#{#dto.price}, :#{#dto.quantity})",
			nativeQuery = true)
	void insert(@Param("dto") CartDetailDto cartDetailDto);
	
	@Query(value = "select * from order_details", nativeQuery = true)
	List<OrderDetails> findAll();
	
	@Query(value = "  select * from order_details where orderId = ?1", nativeQuery = true)
	List<OrderDetails> findByOrderId(Long orderId);
}
