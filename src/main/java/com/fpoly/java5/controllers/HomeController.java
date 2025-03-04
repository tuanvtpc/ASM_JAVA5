package com.fpoly.java5.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.java5.entity.CategoryEntity;
import com.fpoly.java5.entity.ProductEntity;
import com.fpoly.java5.jpas.CategoryJPA;
import com.fpoly.java5.jpas.ProductJPA;

import com.fpoly.java5.jpas.ProductJPA;

@Controller
public class HomeController {
	
	@Autowired
	ProductJPA productJPA;
	
	@Autowired
	CategoryJPA categoryJPA;
	
	
	@ModelAttribute(name = "products")
	public List<ProductEntity> getProduct() {
		return productJPA.findAllActiveProducts();
	}
	
	@ModelAttribute("categories")
	public List<CategoryEntity> getCategory() {
		return categoryJPA.findAll();
	}
	
	@GetMapping("/homepage")
	public String homePage(@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
	    List<ProductEntity> products;
	    
	    if (categoryId != null) {
	        products = productJPA.findByCategoryId(categoryId);
	    } else {
	        products = productJPA.findAllActiveProducts();
	    }
	    
	    model.addAttribute("products", products);
	    
	    return "/user/index.html";
	}
	
	
}
