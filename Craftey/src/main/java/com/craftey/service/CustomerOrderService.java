
package com.craftey.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craftey.model.Cart;
import com.craftey.model.CustomerOrder;
import com.craftey.repository.CartRepository;
import com.craftey.repository.CustomerOrderRepository;

@Service
public class CustomerOrderService {

	@Autowired
	private CustomerOrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;

	public CustomerOrder placeOrder(List<Cart> cart) {

		CustomerOrder customerOrder = new CustomerOrder();
		LocalDate date = LocalDate.now();
		customerOrder.setCart(cart);
		customerOrder.setOrderedDate(date);

		return orderRepository.save(customerOrder);

	}

	public Cart setDeliveredStatus(Optional<Cart> cartOptional) {

		List<Cart> cart = optionalToList(cartOptional);
		for (Cart item : cart) {
			item.setStatus("Delivered");
			cartRepository.save(item);
		}

		return null;

	}

	private static <T> List<T> optionalToList(Optional<T> optional) {
		// Use orElse(Collections.emptyList()) if you want an empty list for an empty
		// Optional
		// Use orElseGet(Collections::emptyList) for a more efficient empty list
		// creation
		return optional.map(Collections::singletonList).orElse(Collections.emptyList());
	}

}
