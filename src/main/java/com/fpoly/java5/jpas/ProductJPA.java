package com.fpoly.java5.jpas;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.java5.entity.ProductEntity;

public interface ProductJPA extends JpaRepository<ProductEntity, Integer>{

	
	boolean existsByCategoryId(int category);

	@Query(value = "SELECT * FROM products WHERE name LIKE %:name%", nativeQuery = true)
	List<ProductEntity> findByName(@Param("name") String name);

	@Query(value = "SELECT * FROM Products WHERE product_id=?1", nativeQuery = true)
	public Optional<ProductEntity> findByIdProd(int prodId);
	
	@Query(value = "SELECT * FROM Products WHERE is_active = 1 ORDER BY product_id DESC", nativeQuery = true)
	List<ProductEntity> findAllActiveProducts();
	
	@Query(value = "SELECT * FROM products WHERE category_id=?1  AND is_active = 1 ORDER BY product_id DESC", nativeQuery = true)
	List<ProductEntity> findByCategoryId(Integer categoryId);
}
