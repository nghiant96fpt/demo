package com.example.demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Order;

public interface OrderJPA extends JpaRepository<Order, Integer> {

}
