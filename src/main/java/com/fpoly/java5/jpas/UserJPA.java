package com.fpoly.java5.jpas;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fpoly.java5.entity.UserEntity;

public interface UserJPA extends JpaRepository<UserEntity, Integer> {

    @Query(value = "SELECT * FROM users WHERE username=?1 OR email=?2", nativeQuery = true)
    public List<UserEntity> findByUsernameAndEmail(String username, String email);

    @Query(value = "SELECT * FROM users WHERE (username=?1 OR email=?2) AND id != ?3", nativeQuery = true)
    public List<UserEntity> findByUsernameOrEmailAndId(String username, String email, int id);

    @Query(value = "SELECT * FROM users WHERE username=?1", nativeQuery = true)
    public Optional<UserEntity> findByUsername(String username);
    
    
    

}
