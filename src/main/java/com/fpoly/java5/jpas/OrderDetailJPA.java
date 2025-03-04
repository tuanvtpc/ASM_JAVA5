package com.fpoly.java5.jpas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpoly.java5.entity.OrderDetailEntity;

public interface OrderDetailJPA extends JpaRepository<OrderDetailEntity, Integer>{
	
	@Query(value = "SELECT * FROM order_details WHERE order_id=?1", nativeQuery = true)
	List<OrderDetailEntity> findByIdOrder(Integer orderId);

}
