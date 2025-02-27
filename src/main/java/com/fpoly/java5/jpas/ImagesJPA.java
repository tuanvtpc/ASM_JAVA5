package com.fpoly.java5.jpas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpoly.java5.entity.ImageEntity;

public interface ImagesJPA extends JpaRepository<ImageEntity, Integer> {

	@Query(value = "DELETE FORM Images WHERE product_id=?1", nativeQuery = true)
	public void deleteByProdId(int id);
}
