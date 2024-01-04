package com.craftey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.craftey.model.User;

@Controller
@RequestMapping("/")
public class IndexController {
	@GetMapping
    public String home() {
        
        return "index";
    }
}
