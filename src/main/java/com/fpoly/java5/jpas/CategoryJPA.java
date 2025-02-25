package com.fpoly.java5.jpas;

import com.fpoly.java5.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryJPA extends JpaRepository<CategoryEntity, Integer> {

    @Query(value = "SELECT * FROM category WHERE status = true", nativeQuery = true)
    List<CategoryEntity> findAllActiveCategories();

    @Query(value = "SELECT * FROM category WHERE category_id = ?1", nativeQuery = true)
    CategoryEntity findCategoryById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO category (name, status) VALUES (?1, ?2)", nativeQuery = true)
    void insertCategory(String name, Boolean status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE category SET name = ?1, status = ?2 WHERE category_id = ?3", nativeQuery = true)
    void updateCategory(String name, Boolean status, Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE category SET status = false WHERE category_id = ?1", nativeQuery = true)
    void disableCategory(Integer id);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM category WHERE category_id = ?1", nativeQuery = true)
    void deleteCategoryById(Integer id);
   
    @Query(value = "SELECT * FROM category WHERE LOWER(name) LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    List<CategoryEntity> searchCategories(@Param("keyword") String keyword);



}