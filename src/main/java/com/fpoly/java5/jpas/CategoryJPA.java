package com.fpoly.java5.jpas;

import com.fpoly.java5.entity.CategoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface CategoryJPA extends JpaRepository<CategoryEntity, Integer> {
	
   
    @Query(value = "SELECT * FROM category WHERE LOWER(name) LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    List<CategoryEntity> searchCategories(@Param("keyword") String keyword);

    
    @Query(value = "DELETE FROM Category WHERE category_id=?1", nativeQuery = true)
    Optional<CategoryEntity> deleteByCatId(Integer categoryId);
    
    @Query(value = "SELECT COUNT(*) FROM Products WHERE category_id = ?1", nativeQuery = true)
    public int countProductsByCategoryId(Integer categoryId);
    
    Optional<CategoryEntity> findById(Integer id);
    
    @Query(value = "SELECT * FROM category WHERE status = 1 ORDER BY category_id DESC", nativeQuery = true)
    List<CategoryEntity> findAllActiveCategories();

}