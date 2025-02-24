package com.fpoly.java5.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.java5.entity.CartDetailEntity;
import com.fpoly.java5.entity.CartEntity;
import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.jpas.CartJPA;
import com.fpoly.java5.jpas.UserJPA;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CartService {
	@Autowired
	CartJPA cartJPA;
	
	@Autowired
	UserJPA userJPA;
	
	@Autowired
	HttpServletRequest req;
	
	@Autowired
	HttpServletResponse resp;
	
	private UserEntity getUser() {
		Cookie cookies[] = req.getCookies();
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("user_id")) {
				Optional<UserEntity> userOptional = userJPA.findById(Integer.parseInt(cookie.getValue()));
				
				return userOptional.isPresent() ? userOptional.get() : null; 
			}
		}
		
		return null;
	}
	
	private CartEntity getCart() {
		try {
			UserEntity userEntity = getUser();
			if (userEntity != null && userEntity.getCarts() != null) {
				return (CartEntity) userEntity.getCarts();
			}
			
			CartEntity cartEntity = new CartEntity();
			
			cartEntity.setUser(userEntity);
			
			return cartJPA.save(cartEntity);
			
			
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<CartDetailEntity> getList(){
		CartEntity cartEntity = getCart();
		
		return cartEntity !=null ? cartEntity.getCartDetails() : new ArrayList<CartDetailEntity>();
	}
	
	
	
	public boolean addToCart(int prodId) {
		
		return true;
	}
	
	public boolean deleteCartItem(int cartItemId) {
		
		return true;
	}
	
	public boolean updateQuantityCartItem(int cartItemId, int quantity) {
		
		return true;
	}
}
