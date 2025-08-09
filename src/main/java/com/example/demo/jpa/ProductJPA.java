package com.example.demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Product;

public interface ProductJPA extends JpaRepository<Product, Integer> {

}
