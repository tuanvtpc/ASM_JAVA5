package com.fpoly.java5.jpas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpoly.java5.entity.ProductEntity;

public interface ProductJPA extends JpaRepository<ProductEntity, Integer>{
	
	@Query(value = "SELECT * FROM Products WHERE product_id=?1", nativeQuery = true)
	public Optional<ProductEntity> findByIdProd(int prodId);
}
