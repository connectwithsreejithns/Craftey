package com.craftey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craftey.model.Address;
import com.craftey.model.User;
import com.craftey.repository.AddressRepository;
import com.craftey.repository.UserRepository;

import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressService {

	
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;


	public Address saveAddress(Address addr,User user) {
		
		Address address=new Address();
		address.setHouseName(addr.getHouseName());
		address.setStreet(addr.getStreet());
		address.setCity(addr.getCity());
		address.setPinCode(addr.getPinCode());
		address.setPhone(addr.getPhone());
		
	
		user.setAddress(address);
		userRepository.save(user);
		return addressRepository.save(address);

	}
	
	

	
}
