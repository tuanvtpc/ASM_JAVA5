package com.fpoly.java5.jpas;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.java5.entity.UserEntity;

public interface UserJPA extends JpaRepository<UserEntity, Integer> {
	@Query("SELECT u FROM UserEntity u WHERE u.username LIKE %:keyword% OR u.email LIKE %:keyword% OR u.name LIKE %:keyword%")
	List<UserEntity> searchUsers(@Param("keyword") String keyword, Sort sort);

	
	@Query(name = "SELECT * FROM users WHERE username=?1 OR email=?2", nativeQuery = true)
	public List<UserEntity> findByUsernameAndEmail(String username, String email);

	@Query(value = "SELECT * FROM users WHERE (username=?1 OR email=?2) AND id != ?3", nativeQuery = true)
	public List<UserEntity> findByUsernameOrEmailAndId(String username, String email, int id);

	List<UserEntity> findByNameContainingIgnoreCaseAndUsernameContainingIgnoreCaseAndEmailContainingIgnoreCase(
			String name, String username, String email, Sort sort);

	Optional<UserEntity> findByUsername(String username);

}
