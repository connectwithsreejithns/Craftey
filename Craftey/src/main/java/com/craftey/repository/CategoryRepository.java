package com.craftey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.craftey.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	public Category findByName(String name);
}

