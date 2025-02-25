package com.fpoly.java5.jpas;

import com.fpoly.java5.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface WishlistJPA extends JpaRepository<WishlistEntity, Integer> {
    
    // Lấy danh sách sản phẩm yêu thích của người dùng dựa vào user_id
    @Query("SELECT w FROM WishlistEntity w WHERE w.user.id = ?1")
    List<WishlistEntity> findByUserId(Integer userId);
}
