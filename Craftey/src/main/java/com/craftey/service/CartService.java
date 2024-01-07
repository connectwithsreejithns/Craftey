package com.craftey.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craftey.model.Cart;
import com.craftey.model.Product;
import com.craftey.model.User;
import com.craftey.repository.CartRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserService userService;

	public Cart addToCart(User user, Optional<Product> product, int quantity) {
		Cart cart = new Cart();
		Product item = new Product();
		if (product.isPresent()) {
			item = product.get();
		}

		cart.setUser(user);
		cart.setQuantity(quantity);
		cart.setProduct(item);
		cart.setStatus("inCart");

		return cartRepository.save(cart);
	}

	public List<Cart> getCartItems(Long id, User user) {

		return cartRepository.getAllCartByUser(user);

	}

	public void updateCart(Long id, int num) {
		Optional<Cart> cart = cartRepository.findById(id);
		Cart itemCart = cart.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		int quantity = itemCart.getQuantity();
		if(num==1) {
			if(quantity!=5) {
				itemCart.setQuantity(quantity + num);
			}
		}else {
			if(quantity!=1) {
				itemCart.setQuantity(quantity + num);
			}
		}
	}

	public List<Cart> getCart() {

		return cartRepository.findAll();

	}
	
	public Cart setOrderStatus(Cart cart) {
		cart.setStatus("Ordered");
		return cartRepository.save(cart);
	}
	
	public Optional<Cart> getCartById(Long id) {
		return cartRepository.findById(id);
		
		
	}
	
	public boolean checkAddrEntry(User user) {
		
		if(user.getAddress()==null) {
			return false;
		}		
		return true;		
	}

	public void deleteCart(Optional<Cart> optionalCart) {
		optionalCart.ifPresent(cart -> cart.setStatus("Cancelled"));
	}
	
	

}
