package com.fpoly.java5.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fpoly.java5.beans.ProductBean;
import com.fpoly.java5.entity.CategoryEntity;
import com.fpoly.java5.entity.ProductEntity;
import com.fpoly.java5.jpas.CategoryJPA;
import com.fpoly.java5.jpas.ProductJPA;
import com.fpoly.java5.services.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

	@Autowired
	CategoryJPA categoryJPA;
	
	@Autowired
	ProductJPA productJPA;

	@Autowired
	ProductService productService;

	@GetMapping("/product")
	public String product(Model model) {
		
	    
		return "/admin/product.html";
	}
	
	@ModelAttribute(name = "productList")
	public List<ProductEntity> getProduct() {
		return productJPA.findAll();
	}

	@ModelAttribute(name = "categories")
	public List<CategoryEntity> getCategory() {
		return categoryJPA.findAll();
	}

	@GetMapping("/product-form")
	public String showFormProduct(Model model) {
		ProductBean productBean = new ProductBean();
		model.addAttribute("product", productBean);

		return "/admin/product-form.html";
	}

	@PostMapping("/product-form")
	public String handleProductForm(@Valid @ModelAttribute("product") ProductBean productBean, Errors errors,
			Model model) {
		boolean hasErrors = errors.hasErrors();
		String imageError = productBean.isImagesError();

		if (hasErrors || imageError != null) {
			if (imageError != null) {
				model.addAttribute("imageError", imageError);
			}
			return "/admin/product-form.html";
		}

		Boolean result = productService.saveProduct(productBean);
		if (result) {
			return "redirect:/admin/product";
		} else {
			model.addAttribute("saveError", "Lưu sản phẩm thất bại. Vui lòng thử lại.");
			return "/admin/product-form.html";
		}
	}

}
