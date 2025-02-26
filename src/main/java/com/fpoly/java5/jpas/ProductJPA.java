package com.fpoly.java5.jpas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java5.entity.ProductEntity;

public interface ProductJPA extends JpaRepository<ProductEntity, Integer>{
	
	boolean existsByCategoryId(int category);
}
