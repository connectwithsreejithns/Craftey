package com.craftey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.craftey.model.Address;
import com.craftey.model.User;
import com.craftey.service.AddressService;
import com.craftey.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("customer/address")
public class AddressController {
	
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddressService addressService;
	
	
	@GetMapping
	public String addAddress(Model model) {
		Address addr=new Address();
		model.addAttribute("addr", addr);
		
		return "customer/address";
	}

	
	@PostMapping
	public String addAddress(@ModelAttribute("addr") Address addr, HttpServletRequest request ) {
		HttpSession session = request.getSession();
		// Retrieve a session attribute
		String email = (String) session.getAttribute("eMail");
		User user = userService.findByMailUser(email);
		
		
		addressService.saveAddress(addr, user);
		
		
		return "redirect:/customer/home?message=Address set successfully";
	}
	
	@GetMapping("/edit")
	public String editAddress(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// Retrieve a session attribute
		String email = (String) session.getAttribute("eMail");
		User user = userService.findByMailUser(email);
		
		Address addr=addressService.getAddressByUser(user);
		model.addAttribute("addr", addr);
		
		return "Customer/editAddress";
	}
	
	@PostMapping("/update")
	public String updateAddress(@ModelAttribute("addr") Address addr, HttpServletRequest request ) {
		HttpSession session = request.getSession();
		// Retrieve a session attribute
		String email = (String) session.getAttribute("eMail");
		User user = userService.findByMailUser(email);
		Address existingAddr=addressService.getAddressByUser(user);
		
		addressService.updateAddress(addr,existingAddr);
		
		
		return "redirect:/customer/profile?message=Address updated";
	}

}
