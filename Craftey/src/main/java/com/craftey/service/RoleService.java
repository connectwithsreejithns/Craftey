package com.craftey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craftey.model.Role;
import com.craftey.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}

	/*
	 * public List<Role> findAll() { return roleRepository.findAll(); }
	 */
}
