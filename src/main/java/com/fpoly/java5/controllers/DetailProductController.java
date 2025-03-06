package com.fpoly.java5.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.java5.entity.CartEntity;
import com.fpoly.java5.entity.CategoryEntity;
import com.fpoly.java5.entity.ProductEntity;
import com.fpoly.java5.jpas.CartDetailJPA;
import com.fpoly.java5.jpas.CategoryJPA;
import com.fpoly.java5.jpas.ProductJPA;
import com.fpoly.java5.services.CartService;

@Controller
public class DetailProductController {
    
    @Autowired
    private ProductJPA productJPA;
    
    @Autowired
	CategoryJPA categoryJPA;
	
	@Autowired
	CartDetailJPA cartDetailJPA;
	
	@Autowired
	CartService cartService;
	
	
	
	
	@ModelAttribute("categories")
	public List<CategoryEntity> getCategory() {
		return categoryJPA.findAll();
	}
	
	@ModelAttribute("totalCartItem")
	public int getTotalCartItem() {
	    CartEntity cartEntity = cartService.getCart();
	    if (cartEntity != null) {
	        return cartDetailJPA.sumQuantityByCartId(cartEntity.getId());
	    }
	    return 0; 
	}

    @GetMapping("/detail-product")
    public String product(@RequestParam("prodId") int prodId, Model model) {
        Optional<ProductEntity> productEntity = productJPA.findByIdProd(prodId);

        if (productEntity.isPresent()) {
            model.addAttribute("product", productEntity.get());
        } else {
            return "redirect:/user/product"; 
        }

        return "user/detail-product"; 
    }
}
