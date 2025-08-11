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
@Table(name = "products")
public class Product {
	@Column(name = "id", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", nullable = false, columnDefinition = "NVARCHAR (300)")
	private String name;

	@Column(name = "description", nullable = false, columnDefinition = "NTEXT")
	private String desc;

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "status", nullable = false)
	private boolean status;

	@ManyToOne
	@JoinColumn(name = "cat_id")
	private Category category;

	@OneToMany(mappedBy = "product")
	private List<Image> images;

	@OneToMany(mappedBy = "product")
	private List<CartItem> cartItemts;

	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItems;
}

// Khi thấy khoá ngoại dùng @ManyToOne và @JoinColumn 
// => sẽ nhận dữ liệu là đối tượng của table tương ứng 