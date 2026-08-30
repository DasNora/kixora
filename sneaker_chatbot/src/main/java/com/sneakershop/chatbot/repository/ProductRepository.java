package com.sneakershop.chatbot.repository;

import com.sneakershop.chatbot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
        extends JpaRepository<Product,Integer> {

}