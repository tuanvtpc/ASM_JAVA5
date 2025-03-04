package com.fpoly.java5.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	private CategoryJPA categoryJPA;

	@GetMapping("/admin/category")
	public String listCategories(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
		List<CategoryEntity> categories;
		if (keyword != null && !keyword.isEmpty()) {
			categories = categoryService.searchCategories(keyword);
		} else {
			categories = categoryJPA.findAll();
		}
		model.addAttribute("categories", categories);
		model.addAttribute("keyword", keyword);
		return "admin/category";
	}

	@GetMapping("/admin/add-category")
	public String showCategoryForm(CategoryBean categoryBean, @RequestParam(value = "id", required = false) Integer id, Model model) {
	    if (id != null) {
	        Optional<CategoryEntity> optionalCategory = categoryJPA.findById(id);
	        if (optionalCategory.isPresent()) {
	            CategoryEntity categoryEntity = optionalCategory.get();
	            categoryBean.setId(categoryEntity.getId());
	            categoryBean.setName(categoryEntity.getName());
	            categoryBean.setStatus(categoryEntity.getStatus());
	        }
	    }
	    model.addAttribute("categoryBean", categoryBean); 
	    return "admin/category-form";
	}


	@PostMapping("/admin/add-category")
	public String saveCategory(@Valid @ModelAttribute("categoryBean") CategoryBean categoryBean, Errors errors,
	        Model model) {
	    if (errors.hasErrors()) {
	        errors.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage())); // Log lỗi
	        return "admin/category-form";
	    }

	    String result = categoryService.saveCategory(categoryBean);

	    if (result.equals("success")) {
	        return "redirect:/admin/category";
	    } else {
	        model.addAttribute("error", result);
	        return "admin/category-form";
	    }
	}


	@PostMapping("/admin/delete-category")
	public String deleteCategory(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
		boolean isDeleted = categoryService.deleteCategory(id);
		if (isDeleted) {
			redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công!");
		} else {
			redirectAttributes.addFlashAttribute("message", "Không thể xóa sán phẩm vì có sản phẩm!");
		}
		return "redirect:/category";
	}

}
