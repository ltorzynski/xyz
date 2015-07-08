package com.acme.order.delivery.strategy;

import com.acme.order.Customer;
import com.acme.order.pizza.PizzaType;

public class SimpleDeliveryTimeStrategy implements DeliveryTimeStrategy {

	@Override
	public int provideDeliveryMinutesOffset(Customer customer, PizzaType pizzaType) {
		return 30;
	}
}
