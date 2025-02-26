package com.fpoly.java5.jpas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpoly.java5.entity.AddressEntity;

public interface AddressJPA extends JpaRepository<AddressEntity, Integer>{
	
	@Query(value = "SELECT * FROM Address WHERE user_id=?1", nativeQuery = true)
	List<AddressEntity> findByUserId(int userId);
}
