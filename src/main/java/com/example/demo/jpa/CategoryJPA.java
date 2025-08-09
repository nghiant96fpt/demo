package com.example.demo.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Category;

public interface CategoryJPA extends JpaRepository<Category, Integer> {

	@Query(value = "SELECT * FROM categories WHERE name=?1", nativeQuery = true)
	Optional<Category> checkNameExist(String name);
}
