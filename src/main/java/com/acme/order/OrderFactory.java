package com.acme.order;

import org.springframework.stereotype.Component;

import com.acme.order.pizza.PizzaOrder;
import com.acme.order.pizza.PizzaType;

@Component
public class OrderFactory {

	public PizzaOrder create(Customer customer, PizzaType pizzaType) {
		return new PizzaOrder(customer, pizzaType);
	}

}
