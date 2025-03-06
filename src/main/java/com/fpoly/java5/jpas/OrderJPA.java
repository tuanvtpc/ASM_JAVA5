package com.fpoly.java5.jpas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpoly.java5.entity.OrderEntity;

public interface OrderJPA extends JpaRepository<OrderEntity, Integer>{
	
	@Query(value = "SELECT * FROM Orders WHERE status=?1", nativeQuery = true)
	List<OrderEntity> findByStatus(int status);
	
	
	@Query(value = "SELECT * FROM Orders WHERE user_id=?1 ", nativeQuery = true)
	public List<OrderEntity> getListOrderByUser(Integer userId);
	
	@Query(value = "SELECT * FROM Orders WHERE user_id = ?1 AND status = ?2", nativeQuery = true)
	List<OrderEntity> findByUserIdAndStatus(Integer userId, int status);
	
	@Query(value = "SELECT COUNT(*) FROM Orders", nativeQuery = true)
	int countAllOrders();
	
	@Query(value = "SELECT SUM(total_amount) FROM Orders WHERE status IN (4, 5)", nativeQuery = true)
    Double sumTotalAmountByStatus();
	
	@Query(value = "SELECT COUNT(*) FROM Orders WHERE status = 2", nativeQuery = true)
	int countAllOrderByStatus();
	
	

	
}
