package com.fpoly.java5.jpas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fpoly.java5.entity.ImageEntity;

import jakarta.transaction.Transactional;

public interface ImagesJPA extends JpaRepository<ImageEntity, Integer> {

	@Modifying
    @Transactional
    @Query(value = "DELETE FROM Images WHERE product_id = ?1", nativeQuery = true)
    void deleteByProId(int id);
}
