package com.example.demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.CartItem;

public interface CartItemJPA extends JpaRepository<CartItem, Integer> {

}
