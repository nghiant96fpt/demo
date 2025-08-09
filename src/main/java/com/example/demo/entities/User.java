package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
	@Column(name = "id", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "username", nullable = false, length = 100)
	private String username;

	@Column(name = "password", nullable = false, length = 200)
	private String password;

	@Column(name = "name", nullable = false, columnDefinition = "NVARCHAR (100)")
	private String name;

	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Column(name = "role", nullable = false)
	private int role;

	@Column(name = "status", nullable = false)
	private boolean status;

	@OneToMany(mappedBy = "user")
	private List<CartItem> cartItems;

	@OneToMany(mappedBy = "user")
	private List<Order> orders;
}
