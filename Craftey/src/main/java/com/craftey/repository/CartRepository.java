package com.craftey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.craftey.model.Cart;
import com.craftey.model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	public List<Cart> findByUserId(Long userId);

	@Query("SELECT c FROM Cart c WHERE c.user=:user")
	List<Cart> getAllCartByUser(@Param("user") User user);

}
