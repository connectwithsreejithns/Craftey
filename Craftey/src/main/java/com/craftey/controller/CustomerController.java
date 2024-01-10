package com.craftey.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.craftey.model.Product;
import com.craftey.model.User;
import com.craftey.service.CartService;
import com.craftey.service.CustomerService;
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

	@Autowired
	private CustomerService customerService;

	@GetMapping("/home")
	public String showUserHome(@RequestParam(required = false) String message, Model model,
			HttpServletRequest request) {
		if (message != null) {
			model.addAttribute("message", message);
		}
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
	public String productView(@PathVariable Long id, Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		// Retrieve a session attribute
		String email = (String) session.getAttribute("eMail");
		User user = userService.findByMailUser(email);
		Optional<Product> product = productService.getProduct(id);
		model.addAttribute("product", product);
		model.addAttribute("user", user);
		return "Customer/productView";
	}

	@GetMapping("/profile")
	public String viewProfile(@RequestParam(required = false) String message, Model model, HttpServletRequest request) {
		if (message != null) {

			model.addAttribute("message", message);
		}

		HttpSession session = request.getSession();
		// Retrieve a session attribute
		String email = (String) session.getAttribute("eMail");
		User user = userService.findByMailUser(email);
		model.addAttribute("user", user);
		return "Customer/customerProfile";
	}

	@GetMapping("/editProfile")
	public String editProfile(@RequestParam(required = false) String message,
			@RequestParam(required = false) String errorMessage, Model model, HttpServletRequest request) {
		if (message != null) {

			model.addAttribute("message", message);
		}
		if (errorMessage != null) {

			model.addAttribute("errorMessage", errorMessage);
		}
		HttpSession session = request.getSession();
		// Retrieve a session attribute
		String email = (String) session.getAttribute("eMail");
		User user = userService.findByMailUser(email);
		model.addAttribute("user", user);
		return "Customer/editProfile";
	}

	@PostMapping("/editProfile/username")
	public String updateProfileUsername(User user, HttpServletRequest request) {

		HttpSession session = request.getSession();
		// Retrieve a session attribute
		String email = (String) session.getAttribute("eMail");
		User existingUser = userService.findByMailUser(email);

		customerService.updateprofileUsername(user, existingUser);

		return "redirect:/customer/editProfile?message=username updated successfully";
	}

	@PostMapping("/editProfile/email")
	public String updateProfile(User user, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// Retrieve a session attribute
		String email = (String) session.getAttribute("eMail");
		User existingUser = userService.findByMailUser(email);

		User anyUserOnDB = userService.findByMailUser(user.getEmail());
		if (anyUserOnDB != null) {
			return "redirect:/customer/editProfile?errorMessage=This mail id is already taken";
		}
		
		session.setAttribute("eMail", user.getEmail());
		customerService.updateprofileEmail(user, existingUser);
		

		return "redirect:/customer/editProfile?message=Email updated successfully";
	}

}
