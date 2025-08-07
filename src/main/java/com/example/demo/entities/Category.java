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
@Table(name = "categories")
public class Category {
	@Column(name = "id", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", nullable = false, columnDefinition = "NVARCHAR (100)")
	private String name;

	@OneToMany(mappedBy = "category")
	private List<Product> products;
}

// Sẽ có key OneToMany để thể hiện quan hệ 1 - n
// Muốn sử dụng OnToMany ở lớp con tương đương bắt buộc phải có
// key ManyToOne trước 