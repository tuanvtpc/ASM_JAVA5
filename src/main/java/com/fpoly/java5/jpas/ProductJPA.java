package com.fpoly.java5.jpas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.java5.entity.ProductEntity;

public interface ProductJPA extends JpaRepository<ProductEntity, Integer>{
<<<<<<< HEAD
	
	boolean existsByCategoryId(int category);
=======
	@Query(value = "SELECT * FROM products WHERE name LIKE %:name%", nativeQuery = true)
	List<ProductEntity> findByName(@Param("name") String name);
>>>>>>> minhdang
}
