
package com.craftey.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.craftey.model.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

}
