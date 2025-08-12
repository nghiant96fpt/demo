package com.example.demo.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.CartItem;

public interface CartItemJPA extends JpaRepository<CartItem, Integer> {

	@Query(value = "SELECT * FROM cart_items WHERE prod_id=?1 AND user_id=?2", nativeQuery = true)
	Optional<CartItem> getCartItemByProdIdAndUserId(int prodId, int userId);
}
