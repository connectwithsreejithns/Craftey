package com.craftey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		return "redirect:/user/login?successMessage=Login now";
		/* return "user/login"; */
	}

	@GetMapping("/login")
	public String login(@RequestParam(required = false) String errorMessage,@RequestParam(required = false) String successMessage, Model model) {
		if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }
		if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }
		model.addAttribute("user", new User());
		return "user/login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("user") User user, HttpServletRequest request) {

		User userdata = userService.findByMailUser(user.getEmail());

		if (userdata == null) {
			return "redirect:/user/login?errorMessage=User doesn't exists...";

		} else {

			if (userdata.getPassword().equals(user.getPassword())) {

				HttpSession session = request.getSession();
				String email = (String) session.getAttribute("eMail");
				if (email == null) {
					session.setAttribute("eMail", userdata.getEmail());
				}

				if ((userdata.getRole().getName()).equals("ROLE_CUSTOMER")) {

					return "redirect:/customer/home";
				} else if (userdata.getRole().getName().equals("ROLE_ADMIN")) {
					return "redirect:/admin/home";
				} else {
					return "redirect:/user/login?errorMessage=User doesn't exists...";
				}

			} else {
				return "redirect:/user/login?errorMessage=Wrong Password...";
			}
		}

	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/user/login";
	}

}
