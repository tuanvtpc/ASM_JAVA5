package com.fpoly.java5.jpas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpoly.java5.entity.OrderEntity;

public interface OrderJPA extends JpaRepository<OrderEntity, Integer>{
	
	@Query(value = "SELECT * FROM Orders WHERE status=?1", nativeQuery = true)
	List<OrderEntity> findByStatus(int status);
}
