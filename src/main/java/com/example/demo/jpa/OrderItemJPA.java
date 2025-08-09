package com.example.demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.OrderItem;

public interface OrderItemJPA extends JpaRepository<OrderItem, Integer> {

}
