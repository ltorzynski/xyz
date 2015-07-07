package com.acme.order.delivery.strategy;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.acme.order.Customer;
import com.acme.order.pizza.PizzaType;

@Component
@Primary
@Profile("pizzaType")
public class PizzaTypeDeliveryTimeStrategy implements DeliveryTimeStrategy {

	@Override
	public int provideDeliveryMinutesOffset(Customer customer, PizzaType pizzaType) {
		return (pizzaType == PizzaType.BIG || pizzaType == PizzaType.LARGE) ? 120 : 60;
	}

}
