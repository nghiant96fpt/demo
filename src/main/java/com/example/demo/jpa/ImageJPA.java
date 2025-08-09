package com.example.demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Image;

public interface ImageJPA extends JpaRepository<Image, Integer> {

}
