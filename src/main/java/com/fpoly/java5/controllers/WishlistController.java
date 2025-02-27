package com.fpoly.java5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WishlistController {

    @GetMapping("/wishlist")
    public String showWishlist() {
        return "user/wishlist"; // Trả về file wishlist.html
    }
}