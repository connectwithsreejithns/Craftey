package com.craftey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	public User save(User user) {
		String encodededPassword=passwordEncoder.encode(user.getPassword());
		user.setPassword(encodededPassword);
		return userRepository.save(user);
	}

	public User findByMailUser(String email) {
		return userRepository.findByEmail(email);
	}

	public Role findRoleByName(String name) {
		return roleService.findByName(name);
	}
	public boolean passwordVerification(User user, User userData) {
		return passwordEncoder.matches(user.getPassword(), userData.getPassword());
	}
	
	
	

	
}
