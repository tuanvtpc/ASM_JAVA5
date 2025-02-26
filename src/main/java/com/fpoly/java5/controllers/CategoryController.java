package com.fpoly.java5.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.java5.beans.CategoryBean;
import com.fpoly.java5.entity.CategoryEntity;
import com.fpoly.java5.jpas.CategoryJPA;
import com.fpoly.java5.services.CategoryService;

import jakarta.validation.Valid;

@Controller
public class CategoryController {
	@Autowired
    private CategoryService categoryService;
	 @Autowired
	    private CategoryJPA categoryJPA; // Inject Repository vào Controller

    // Hiển thị danh sách danh mục
	 @GetMapping("/admin/category")
	    public String listCategories(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
	        List<CategoryEntity> categories;
	        if (keyword != null && !keyword.isEmpty()) {
	            categories = categoryService.searchCategories(keyword);
	        } else {
	            categories = categoryService.getAllCategories();
	        }
	        model.addAttribute("categories", categories);
	        model.addAttribute("keyword", keyword);
	        return "admin/category";
	    }
    @GetMapping("/category")
    public String redirectToAdminCategory() {
        return "redirect:/admin/category";
    }


    // Hiển thị form thêm danh mục
    @GetMapping("/admin/category/add")
    public String showCategoryForm(Model model) {
        model.addAttribute("categoryBean", new CategoryBean());
        return "admin/category-form"; // Đúng với thư mục chứa file .html
    }



    @PostMapping("/admin/category/save")
    public String saveCategory(@Valid @ModelAttribute("categoryBean") CategoryBean categoryBean,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/category-form"; // Trả về form nếu có lỗi
        }
        
        // Chuyển từ Bean sang Entity để lưu vào DB
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryBean.getName());
        category.setStatus(categoryBean.getStatus());
        
        categoryService.saveCategory(category); // Gọi Service để lưu vào DB

        return "redirect:/admin/category"; // Chuyển hướng sau khi lưu thành công
    }


    // Xử lý thêm danh mục
    @PostMapping("/admin/category/add")
    public String addCategory(@ModelAttribute("category") CategoryEntity category, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/category-form";
        }
        categoryService.saveCategory(category);
        return "redirect:/admin/category";
    }

    // Hiển thị form chỉnh sửa danh mục
    @GetMapping("/admin/category/edit/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model) {
        CategoryEntity category = categoryService.findById(id);
        if (category == null) {
            return "redirect:/admin/category"; // Chuyển hướng nếu không tìm thấy
        }
        model.addAttribute("categoryBean", category);
        return "admin/category-form";
    }




    @PostMapping("/admin/category/update")
    public String updateCategory(@RequestParam Integer id,
                                 @RequestParam String name,
                                 @RequestParam Boolean status) {
        categoryService.updateCategory(id, name, status);
        return "redirect:/admin/category";
    }





    // Xóa danh mục
    @GetMapping("/admin/category/delete")
    public String deleteCategory() {
        return "admin/category-delete";
    }	
	
    @PostMapping("/admin/category/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/category"; // Quay lại danh sách sau khi xóa
    }

    
   


}
