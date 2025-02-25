package com.fpoly.java5.services;

import com.fpoly.java5.entity.CategoryEntity;
import com.fpoly.java5.jpas.CategoryJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
	@Autowired
    private CategoryJPA categoryRepository;
    private final CategoryJPA categoryJPA;

    public CategoryService(CategoryJPA categoryJPA) {
        this.categoryJPA = categoryJPA;
    }
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }
    public List<CategoryEntity> getAllActiveCategories() {
        return categoryJPA.findAllActiveCategories();
    }
    public void saveCategory(CategoryEntity category) {
        categoryRepository.save(category);
    }
    public CategoryEntity getCategoryById(Integer id) {
        return categoryJPA.findCategoryById(id);
    }

    @Transactional
    public void addCategory(String name, Boolean status) {
        categoryJPA.insertCategory(name, status);
    }

    @Transactional
    public void updateCategory(Integer id, String name, Boolean status) {
        categoryJPA.updateCategory(name, status, id);
    }

    @Transactional
    public void disableCategory(Integer id) {
        categoryJPA.disableCategory(id);
    }
    @Transactional
    public void deleteCategory(Integer id) {
        categoryJPA.deleteCategoryById(id);
    }
    public List<CategoryEntity> searchCategories(String keyword) {
        return categoryJPA.searchCategories(keyword);
    }


}
