package com.sneakershop.ratings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sneakershop.ratings.entity.User;

public interface UserRepository
        extends JpaRepository<User,Integer>{

}