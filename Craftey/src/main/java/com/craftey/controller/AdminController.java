package com.craftey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.craftey.model.Cart;
import com.craftey.service.CartService;

@Controller
@RequestMapping("admin/orders")
public class AdminController {

	@Autowired
	private CartService cartService;

	@GetMapping
	public String seeOrders(Model model) {
		List<Cart> cart = cartService.getCart();
		model.addAttribute("cart", cart);
		System.out.println(cart);
		return "Admin/seeOrders";
	}
	
	
	@GetMapping("/orderHistory")
	public String seeOrderHistory(Model model) {
		List<Cart> cart = cartService.getCart();
		model.addAttribute("cart", cart);
		System.out.println(cart);
		return "Admin/orderHistory";
	}
}
