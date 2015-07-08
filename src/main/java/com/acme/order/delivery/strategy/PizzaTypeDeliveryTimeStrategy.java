package com.acme.order.delivery.strategy;

import com.acme.order.Customer;
import com.acme.order.pizza.PizzaType;

public class PizzaTypeDeliveryTimeStrategy implements DeliveryTimeStrategy {

	@Override
	public int provideDeliveryMinutesOffset(Customer customer, PizzaType pizzaType) {
		return (pizzaType == PizzaType.BIG || pizzaType == PizzaType.LARGE) ? 120 : 60;
	}

}
