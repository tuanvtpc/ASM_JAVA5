package com.fpoly.java5.services;

import com.fpoly.java5.beans.CategoryBean;
import com.fpoly.java5.entity.CategoryEntity;
import com.fpoly.java5.jpas.CategoryJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
	
	@Autowired
	CategoryJPA categoryJPA;

    public List<CategoryEntity> searchCategories(String keyword) {
        return categoryJPA.searchCategories(keyword);
    }
    
    public String saveCategory(CategoryBean categoryBean) {
        try {
            CategoryEntity categoryEntity;
            if (categoryBean.getId() != null) {
                Optional<CategoryEntity> optionalCategory = categoryJPA.findById(categoryBean.getId());
                if (optionalCategory.isPresent()) {
                    categoryEntity = optionalCategory.get();
                } else {
                    return "Không tìm thấy danh mục với ID: " + categoryBean.getId();
                }
            } else {
                categoryEntity = new CategoryEntity();
            }

            categoryEntity.setName(categoryBean.getName());
            categoryEntity.setStatus(categoryBean.getStatus());

            categoryJPA.save(categoryEntity);
            return "success";
        } catch (Exception e) {
            return "Đã xảy ra lỗi khi lưu danh mục: " + e.getMessage();
        }
    }
    
    public String updateCategory(Integer id, CategoryBean categoryBean) {
        try {
            Optional<CategoryEntity> optionalCategory = categoryJPA.findById(id);
            if (optionalCategory.isPresent()) {
                CategoryEntity categoryEntity = optionalCategory.get();
                categoryEntity.setName(categoryBean.getName());
                categoryEntity.setStatus(categoryBean.getStatus());

                categoryJPA.save(categoryEntity);
                return "success";
            } else {
                return "Không tìm thấy danh mục với ID: " + id;
            }
        } catch (Exception e) {
            return "Đã xảy ra lỗi khi cập nhật danh mục: " + e.getMessage();
        }
    }
    
    
    public boolean deleteCategory(Integer id) {
        int productCount = categoryJPA.countProductsByCategoryId(id);
        if (productCount > 0) {
            return false;
        } else {
        	categoryJPA.deleteByCatId(id);
            return true;
        }
    }


}
