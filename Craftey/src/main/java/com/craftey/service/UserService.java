package com.craftey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craftey.model.Address;
import com.craftey.model.Role;
import com.craftey.model.User;
import com.craftey.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleService roleService;

	public User save(User user) {
		// Perform password hashing, validation, etc., before saving
		return userRepository.save(user);
	}

	public User findByMailUser(String email) {
		return userRepository.findByEmail(email);
	}

	public Role findRoleByName(String name) {
		return roleService.findByName(name);
	}
	
	

	
}
