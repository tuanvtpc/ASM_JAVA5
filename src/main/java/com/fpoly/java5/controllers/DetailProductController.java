package com.fpoly.java5.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.java5.entity.ProductEntity;
import com.fpoly.java5.jpas.ProductJPA;

@Controller
public class DetailProductController {
    
    @Autowired
    private ProductJPA productJPA;

    @GetMapping("/detail-product")
    public String product(@RequestParam("prodId") int prodId, Model model) {
        Optional<ProductEntity> productEntity = productJPA.findByIdProd(prodId);

        if (productEntity.isPresent()) {
            model.addAttribute("product", productEntity.get());
        } else {
            return "redirect:/user/product"; 
        }

        return "user/detail-product"; // Đúng đường dẫn
    }
}
