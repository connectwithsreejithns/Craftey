package com.craftey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craftey.model.User;
import com.craftey.repository.UserRepository;

@Service
public class CustomerService {
	
	@Autowired
	private UserRepository userRepository;

	public User updateprofileEmail(User user, User existingUser) {
		existingUser.setEmail(user.getEmail());
		return userRepository.save(existingUser);
		
	}

	public User updateprofileUsername(User user, User existingUser) {
		existingUser.setUsername(user.getUsername());
		return userRepository.save(existingUser);
	}
	
}
