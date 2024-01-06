package com.craftey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.craftey.model.Address;
import com.craftey.model.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
