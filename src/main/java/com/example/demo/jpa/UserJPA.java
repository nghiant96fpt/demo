package com.example.demo.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.User;

public interface UserJPA extends JpaRepository<User, Integer> {

	@Query(value = "SELECT * FROM users WHERE username=?1 OR email=?2 ", nativeQuery = true)
	public List<User> checkEmailOrUserExist(String username, String email);

	@Query(value = "SELECT * FROM users WHERE username=?1 ", nativeQuery = true)
	public Optional<User> checkUsernameExist(String username);
}
