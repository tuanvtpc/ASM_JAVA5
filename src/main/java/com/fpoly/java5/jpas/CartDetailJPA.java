package com.fpoly.java5.jpas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fpoly.java5.entity.CartDetailEntity;

public interface CartDetailJPA extends JpaRepository<CartDetailEntity, Integer> {
	// Tìm sản phẩm có thuộc giỏ hàng và giỏ hàng có thuộc sở hữu user hiện tại
	// không?
	@Query(value = "SELECT TOP 1 * FROM cart_details WHERE product_id=?1 AND cart_id=?2", nativeQuery = true)
	public Optional<CartDetailEntity> findByProductIdAndCartId(int prodId, int cartId);

	@Query(value = "SELECT * FROM cart_details WHERE cart_detail_id=?1 AND cart_id=?2", nativeQuery = true)
	public Optional<CartDetailEntity> findByIdAndCartId(int id, int cartId);
	
	@Modifying
	@Query(value = "DELETE FROM cart_details WHERE cart_id =?1", nativeQuery = true)
	public void deleteByCartId(int cartId);
	
	@Query(value = "SELECT COALESCE(SUM(quantity), 0) FROM cart_details WHERE cart_id = ?1", nativeQuery = true)
	Integer sumQuantityByCartId(int cartId);
}
