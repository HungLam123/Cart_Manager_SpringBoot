package com.mockproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mockproject.entity.ProductTypes;
import com.mockproject.entity.Products;
import com.mockproject.entity.UnitTypes;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long>{
	
	@Query(value = "select * from products where isDeleted = 1 or quantity = 0", nativeQuery = true)
	List<Products> findProducts();
	List<Products> findByIsDeletedAndQuantityGreaterThan(Boolean isDeleted, Integer quantity);	
	
	List<Products> findAll();	
	Page<Products> findByIsDeletedAndQuantityGreaterThan(Boolean isDeleted, Integer quantity, Pageable pageable);	
	Products findBySlug(String slug);//-> select * from products where slug = ? 
	
	@Query(value = "SELECT * FROM products WHERE typeId = 1 and isDeleted = 0 and quantity > 0", nativeQuery = true)
	List<Products> findDienThoai();
	
	@Query(value = "SELECT * FROM products WHERE typeId = 2 and isDeleted = 0 and quantity > 0", nativeQuery = true)
	List<Products> findLapTop();
	
	@Modifying(clearAutomatically = true)//khi update mọi thứ liên quan sẽ update theo
	@Query(value = "UPDATE products SET quantity = ? where id = ?", nativeQuery = true)
	void updateQuantity(Integer newQuantity, Long productId);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "Update products Set isDeleted = 1 where id = ?", nativeQuery = true)
	void updateLogical(Long id);
	@Modifying(clearAutomatically = true)
	@Query(value = "Update products Set isDeleted = 0 where id = ?", nativeQuery = true)
	void updateRepeat(Long id);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "insert into products(id, name, typeId, quantity, price, unitId, description, slug)"
			+ " values(:#{#sp.id}, :#{#sp.name}, :#{#sp.productType.id}, :#{#sp.quantity}, :#{#sp.price}, :#{#sp.unitType.id},"
			+ " :#{#sp.description}, :#{#sp.slug})", nativeQuery = true)
	Products insert(@Param("sp")Products products);

	@Modifying(clearAutomatically = true)
	@Query(value = "Update Products SET name = ?1, quantity = ?2, price = ?3, description = ?4"
			+ ",slug = ?5, typeid = ?6, unitId = ?7 where id = ?8 ", nativeQuery = true)
	void update(String name, Integer quantity, Double price, String description, 
			String slug, ProductTypes productType, UnitTypes unitType, Long id);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "Update Products SET name = ?1 where id = ?2")
	void testUpdate(String name, Long id);
}
