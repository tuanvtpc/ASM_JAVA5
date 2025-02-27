package com.fpoly.java5.jpas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java5.entity.AddressEntity;
import com.fpoly.java5.entity.UserEntity;

public interface AddressJPA extends JpaRepository<AddressEntity, Integer>{
	List<AddressEntity> findByUser(UserEntity user);
}
