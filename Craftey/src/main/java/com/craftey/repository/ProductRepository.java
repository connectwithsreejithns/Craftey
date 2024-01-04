package com.craftey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.craftey.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
