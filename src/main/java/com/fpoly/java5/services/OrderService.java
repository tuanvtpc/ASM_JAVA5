package com.fpoly.java5.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpoly.java5.entity.AddressEntity;
import com.fpoly.java5.entity.CartDetailEntity;
import com.fpoly.java5.entity.OrderDetailEntity;
import com.fpoly.java5.entity.OrderEntity;
import com.fpoly.java5.entity.ProductEntity;
import com.fpoly.java5.jpas.AddressJPA;
import com.fpoly.java5.jpas.OrderDetailJPA;
import com.fpoly.java5.jpas.OrderJPA;
import com.fpoly.java5.jpas.ProductJPA;

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

	@Autowired
    ProductJPA productJPA; 

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
            ProductEntity product = item.getProduct();
            int remainingQuantity = product.getQuantity() - item.getQuantity();
            if (remainingQuantity < 0) {
                throw new RuntimeException("Số lượng sản phẩm " + product.getName() + " không đủ");
            }
            product.setQuantity(remainingQuantity);
            productJPA.save(product); 

            OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
            orderDetailEntity.setOrder(orderEntity);
            orderDetailEntity.setProduct(item.getProduct());
            orderDetailEntity.setPrice(item.getProduct().getPrice());
            orderDetailEntity.setQuantity(item.getQuantity());

            orderDetailJPA.save(orderDetailEntity);
        }
        cartService.clearCart();
    }
	
	
	public void updateOrderStatus(int orderId, int status) {
        Optional<OrderEntity> orderOpt = orderJPA.findById(orderId);
        if (orderOpt.isPresent()) {
            OrderEntity order = orderOpt.get();
            order.setStatus(status);
            orderJPA.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }
	
	 public Double getTotalAmountByStatus() {
	        return orderJPA.sumTotalAmountByStatus();
	    }
	

}
