package com.fpoly.java5.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.java5.entity.AddressEntity;
import com.fpoly.java5.entity.CartDetailEntity;
import com.fpoly.java5.entity.OrderDetailEntity;
import com.fpoly.java5.entity.OrderEntity;
import com.fpoly.java5.jpas.AddressJPA;
import com.fpoly.java5.jpas.OrderDetailJPA;
import com.fpoly.java5.jpas.OrderJPA;

@Service
public class OrderService {

	@Autowired
	OrderJPA orderJPA;

	@Autowired
	OrderDetailJPA orderDetailJPA;

	@Autowired
	CartService cartService;

	@Autowired
	AddressJPA addressJPA;

	public void CreateOrder(int addressId, int paymentMethod) {
		AddressEntity address = addressJPA.findById(addressId)
				.orElseThrow(() -> new RuntimeException("Address not found"));

		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setUser(cartService.getUser());
		orderEntity.setPhone(address.getPhone());
		orderEntity.setAddress(address.getAddress());
		orderEntity.setName(address.getName());
		orderEntity.setStatus(0);
		orderEntity.setTotalAmount(cartService.getTotalPrice());
		orderEntity.setPaymentMethod(paymentMethod);
		orderEntity.setPaymentStatus(0);
		orderEntity.setTransactionId(0);
		orderEntity.setCreatedAt(new Date());

		OrderEntity saveOder = orderJPA.save(orderEntity);

		List<CartDetailEntity> cartDetailEntity = cartService.getList();

		for (CartDetailEntity item : cartDetailEntity) {
			OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
			orderDetailEntity.setOrder(orderEntity);
			orderDetailEntity.setProduct(item.getProduct());
			orderDetailEntity.setPrice(item.getProduct().getPrice());
			orderDetailEntity.setQuantity(item.getQuantity());

			orderDetailJPA.save(orderDetailEntity);
		}
		cartService.clearCart();

	}

}
