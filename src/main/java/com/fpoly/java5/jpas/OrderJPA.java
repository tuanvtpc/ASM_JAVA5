package com.fpoly.java5.jpas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java5.entity.OrderEntity;

public interface OrderJPA extends JpaRepository<OrderEntity, Integer>{
	
}
