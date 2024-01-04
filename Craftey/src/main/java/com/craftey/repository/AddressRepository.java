package com.craftey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.craftey.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
