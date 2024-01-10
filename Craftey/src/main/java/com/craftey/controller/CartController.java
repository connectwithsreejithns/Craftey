package com.craftey.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.craftey.model.Cart;
import com.craftey.model.Product;
import com.craftey.model.User;
import com.craftey.service.CartService;
import com.craftey.service.ProductService;
import com.craftey.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer/cart")
public class CartController {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@GetMapping
	public String showCart(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		// Retrieve a session attribute
		String email = (String) session.getAttribute("eMail");
		User user = userService.findByMailUser(email);
		List<Cart> cart = cartService.getCartItems(user.getId(), user);
		model.addAttribute("cart", cart);
		model.addAttribute("user", user);
		return "Customer/cart";

	}

	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable Long id, Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		// Retrieve a session attribute
		String email = (String) session.getAttribute("eMail");
		User user = userService.findByMailUser(email);

		if (!cartService.checkAddrEntry(user)) {
			return "redirect:/customer/address";
		}

		Optional<Product> product = productService.getProduct(id);
		List<Cart> cart = cartService.getCartItems(user.getId(), user);

		if (cart.isEmpty()) {
			cartService.addToCart(user, product, 1);
		} else {
			int count = 0;
			for (Cart cartItem : cart) {
				if (id == cartItem.getProduct().getId()) {
					if(cartItem.getStatus().equals("inCart")) {
						count++;
					}
				}
			}
			if (count == 0) {
				cartService.addToCart(user, product, 1);
			}
		}
		return "redirect:/customer/cart";
	}

	
	@GetMapping("/addCartItemQuantity/{id}")
	public String addCartItemQuantity(@PathVariable Long id, Model model) {
		cartService.updateCart(id, 1);
		return "redirect:/customer/cart";
	}
	
	@GetMapping("/reduceCartItemQuantity/{id}")
	public String reduceItemQuantity(@PathVariable Long id, Model model) {
		cartService.updateCart(id, -1);
		return "redirect:/customer/cart";
	}
	
	@GetMapping("/deleteCart/{id}")
	public String deleteCart(@PathVariable Long id, Model model) {
		
		cartService.deleteCart(cartService.getCartById(id));
		return "redirect:/customer/cart";
	}

	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/deleteFromCart/{id}")
	public String deleteFromCart(@PathVariable Long id, Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		// Retrieve a session attribute
		String email = (String) session.getAttribute("eMail");
		User user = userService.findByMailUser(email);
		Optional<Product> product = productService.getProduct(id);

		List<Cart> cart = cartService.getCartItems(user.getId(), user);

		int count = 1;
		Long cartId = 0L;
		for (Cart cartItem : cart) {
			if (id == cartItem.getProduct().getId()) {
				count++;
				cartId = cartItem.getId();
				break;
			}
		}
		if (count == 1) {
			cartService.addToCart(user, product, 1);
		} else {
			cartService.updateCart(cartId, -1);
		}

		return "redirect:/customer/cart";

	}

}
