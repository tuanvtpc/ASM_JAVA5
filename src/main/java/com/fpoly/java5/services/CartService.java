package com.fpoly.java5.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fpoly.java5.entity.CartDetailEntity;
import com.fpoly.java5.entity.CartEntity;
import com.fpoly.java5.entity.ProductEntity;
import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.jpas.CartDetailJPA;
import com.fpoly.java5.jpas.CartJPA;
import com.fpoly.java5.jpas.ProductJPA;
import com.fpoly.java5.jpas.UserJPA;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CartService {
	@Autowired
	CartJPA cartJPA;

	@Autowired
	CartDetailJPA cartDetailJPA;

	@Autowired
	UserJPA userJPA;

	@Autowired
	ProductJPA productJPA;

	@Autowired
	HttpServletRequest req;

	@Autowired
	HttpServletResponse resp;
	
	
	
	
	
	public void clearCart() {
	    CartEntity cart = getCart();
	    if (cart != null) {
	        cartDetailJPA.deleteByCartId(cart.getId());
	    } else {
	        throw new RuntimeException("Cart not found");
	    }
	}

	public UserEntity getUser() {
		Cookie[] cookies = req.getCookies();
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

	private CartEntity getCart(){
	    try{
	      UserEntity userEntity = getUser();
	      if(userEntity != null && userEntity.getCarts() != null){
	        return userEntity.getCarts();
	      }
	      CartEntity cartEntity = new CartEntity();
	      cartEntity.setUser(userEntity);
	      return cartJPA.save(cartEntity);
	    }catch(Exception e){
	      return null;
	    }
	  }

	public List<CartDetailEntity> getList() {
		CartEntity cartEntity = getCart();

		return cartEntity != null ? cartEntity.getCartDetails() : new ArrayList<CartDetailEntity>();
	}

	public boolean addToCart(int prodId) {
		try {
			CartEntity cartEntity = getCart();
			Optional<CartDetailEntity> cartDetailOptional = cartDetailJPA.findByProductIdAndCartId(prodId,
					cartEntity.getId());

			if (cartDetailOptional.isPresent()) {
				// CAP NHAT LAI SL
				CartDetailEntity cartDetailEntity = cartDetailOptional.get();
				cartDetailEntity.setQuantity(cartDetailOptional.get().getQuantity() + 1);
				cartDetailJPA.save(cartDetailEntity);
			} else {
				CartDetailEntity cartDetailEntity = new CartDetailEntity();
				cartDetailEntity.setCart(cartEntity);
				Optional<ProductEntity> productOptional = productJPA.findById(prodId);
				if (!productOptional.isPresent()) {
					return false;
				}
				cartDetailEntity.setProduct(productOptional.get());
				cartDetailEntity.setQuantity(1);
				cartDetailJPA.save(cartDetailEntity);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean deleteCartItem(int cartItemId) {
		try {
			CartEntity cartEntity = getCart();

			Optional<CartDetailEntity> cartDetailOptional = cartDetailJPA.findByIdAndCartId(cartItemId,
					cartEntity.getId());
			if (!cartDetailOptional.isPresent()) {
				return false;
			}

			cartDetailJPA.deleteById(cartItemId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean updateQuantityCartItem(int cartItemId, int quantity) {
		try {
			CartEntity cartEntity = getCart();
			Optional<CartDetailEntity> cartDetailOptional = cartDetailJPA.findByIdAndCartId(cartItemId,
					cartEntity.getId());
			if (!cartDetailOptional.isPresent()) {
				return false;
			}

			if (quantity <= 0) {
				deleteCartItem(cartItemId);
				return true;
			}

			CartDetailEntity cartDetailEntity = cartDetailOptional.get();
			cartDetailEntity.setQuantity(quantity);
			cartDetailJPA.save(cartDetailEntity);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public double getTotalPrice() {
        double total = 0;
        for (CartDetailEntity item : getList()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
}
