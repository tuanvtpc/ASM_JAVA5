package com.fpoly.java5.jpas;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpoly.java5.entity.UserEntity;

public interface UserJPA extends JpaRepository<UserEntity, Integer> {
	@Query(name = "SELECT * FROM users WHERE username=?1 OR email=?2", nativeQuery = true)
	public List<UserEntity> findByUsernameAndEmail(String username, String email);

	@Query(value = "SELECT * FROM users WHERE (username=?1 OR email=?2) AND id != ?3", nativeQuery = true)
	public List<UserEntity> findByUsernameOrEmailAndId(String username, String email, int id);

	List<UserEntity> findByNameContainingIgnoreCaseAndUsernameContainingIgnoreCaseAndEmailContainingIgnoreCase(
			String name, String username, String email, Sort sort);

	Optional<UserEntity> findByUsername(String username);
	
	Optional<UserEntity> findByEmail(String email);

}
