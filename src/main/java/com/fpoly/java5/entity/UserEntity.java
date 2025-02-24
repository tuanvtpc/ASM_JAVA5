package com.fpoly.java5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;

	@Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
	private String name;

	@Column(name = "username", nullable = false, length = 100)
	private String username;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "role", nullable = false)
	private int role;

	@Column(name = "email", nullable = false, length = 255)
	private String email;

	@Column(name = "phone", length = 12)
	private String phone;

	@Column(name = "avatar", length = 255, nullable = false)
	private String avatar;

	@Column(name = "is_active")
	private boolean isActive;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<AddressEntity> addresses;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<CartEntity> carts;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<OrderEntity> orders;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<WishlistEntity> wishlists;
}