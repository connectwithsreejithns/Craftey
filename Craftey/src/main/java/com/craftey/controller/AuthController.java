package com.craftey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.craftey.model.Role;
import com.craftey.model.User;
import com.craftey.service.RoleService;
import com.craftey.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class AuthController {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "user/signup";
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute("user") User user) {
		Role userRole = roleService.findByName("ROLE_CUSTOMER");
		user.setRole(userRole);
		userService.save(user);

		return "user/login";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());

		return "user/login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("user") User user, HttpServletRequest request) {

		User userdata = userService.findByMailUser(user.getEmail());

		if (userdata == null) {
			System.out.println("User doesn't exists...");
			return "redirect:/user/login";

		} else {

			if (userdata.getPassword().equals(user.getPassword())) {

				HttpSession session = request.getSession();
				String email = (String) session.getAttribute("eMail");
				if(email==null) {
					session.setAttribute("eMail", userdata.getEmail());
				}
				
				if ((userdata.getRole().getName()).equals("ROLE_CUSTOMER")) {

					return "redirect:/customer/home";
				} else if (userdata.getRole().getName().equals("ROLE_ADMIN")) {
					return "redirect:/admin/home";
				} else {
					System.out.println("No role Found");
					return "redirect:/user/login";
				}

			} else {
				System.out.println("Wrong Password...");
				return "redirect:/user/login";
			}
		}

	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate();
	    return "redirect:/user/login";
	}
	
	

}
