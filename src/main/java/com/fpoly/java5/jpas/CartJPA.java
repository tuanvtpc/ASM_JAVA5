package com.fpoly.java5.jpas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java5.entity.CartEntity;
import com.fpoly.java5.entity.UserEntity;

public interface CartJPA extends JpaRepository<CartEntity, Integer>{

	Optional<CartEntity> findByUser(UserEntity user);
}
