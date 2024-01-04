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
import com.craftey.repository.CartRepository;
import com.craftey.repository.CustomerOrderRepository;
import com.craftey.service.CartService;
import com.craftey.service.ProductService;
import com.craftey.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;

	@GetMapping("/home")
	public String showUserHome(Model model, HttpServletRequest request) {
		List<Product> products = productService.getAllProducts();

		HttpSession session = request.getSession();
		// Retrieve a session attribute
		String email = (String) session.getAttribute("eMail");
		User user = userService.findByMailUser(email);

		model.addAttribute("user", user);
		model.addAttribute("products", products);
		return "Customer/index";
	}

	@GetMapping("/productView/{id}")
	public String productView(@PathVariable Long id, Model model) {
		Optional<Product> product = productService.getProduct(id);
		model.addAttribute("product", product);
		return "Customer/productView";
	}
	
	

	

}
