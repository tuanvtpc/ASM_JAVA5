package com.fpoly.java5.controllers;

<<<<<<< Updated upstream
import java.util.List;

=======
>>>>>>> Stashed changes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fpoly.java5.entity.ProductEntity;
import com.fpoly.java5.jpas.ProductJPA;

import com.fpoly.java5.jpas.ProductJPA;

@Controller
public class HomeController {
	
	@Autowired
	ProductJPA productJPA;
	
	
	@ModelAttribute(name = "products")
	public List<ProductEntity> getProduct() {
		return productJPA.findAll();
	}
	
	@GetMapping("/homepage")
	public String homePage() {
		return "/user/index.html";
	}
	
	@Autowired
	ProductJPA productJPA;
	
}
