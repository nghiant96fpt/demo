package com.example.demo.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Order;

public interface OrderJPA extends JpaRepository<Order, Integer> {

	@Query(value = "SELECT * FROM orders WHERE id=?1 AND user_id=?2", nativeQuery = true)
	Optional<Order> getBorderByIdAndUserId(int id, int userId);
}
