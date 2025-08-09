package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
	@Column(name = "id", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "phone", nullable = false, length = 100)
	private String phone;

	@Column(name = "name", nullable = false, columnDefinition = "NVARCHAR (100)")
	private String name;

	@Column(name = "address", nullable = false, columnDefinition = "NVARCHAR (200)")
	private String address;

	@Column(name = "total_price", nullable = false)
	private int totalPrice;

	@Column(name = "status", nullable = false)
	private int status;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems;

}