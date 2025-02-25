package com.fpoly.java5.jpas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java5.entity.OrderDetailEntity;

public interface OrderDetailJPA extends JpaRepository<OrderDetailEntity, Integer>{

}
