
package com.craftey.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "customerOrder")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomerOrder {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany

	@JoinColumn(name = "cart_id")
	private List<Cart> cart;

	private LocalDate orderedDate;

}
