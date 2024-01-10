
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
import com.craftey.service.CustomerOrderService;
import com.craftey.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller

@RequestMapping("customer/order")
public class CustomerOrderController {

	@Autowired
	private CustomerOrderService orderService;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@GetMapping("/place-order")
	public String placeOrder(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(); // Retrieve a session attribute
		String email = (String) session.getAttribute("eMail");
		User user = userService.findByMailUser(email);

		List<Cart> cart = cartService.getCartItems(user.getId(), user);

		for (Cart item : cart) {
			cartService.setOrderStatus(item);
		}
		orderService.placeOrder(cart);
		model.addAttribute("user", user);
		return "Customer/order";
	}

	@GetMapping("/delivered/{id}")
	public String setDeliveryStatus(@PathVariable("id") Long id, Model model, HttpServletRequest request) {

		Optional<Cart> cart = cartService.getCartById(id);

		
			orderService.setDeliveredStatus(cart);
		
			 return "redirect:/admin/orders";
		
	}

}
