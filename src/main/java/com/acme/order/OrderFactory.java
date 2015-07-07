package com.acme.order;

import com.acme.order.pizza.PizzaOrder;
import com.acme.order.pizza.PizzaType;

public class OrderFactory {

	public PizzaOrder create(Customer customer, PizzaType pizzaType) {
		return new PizzaOrder(customer, pizzaType);
	}

}
