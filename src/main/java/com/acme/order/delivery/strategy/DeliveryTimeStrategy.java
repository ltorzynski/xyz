package com.acme.order.delivery.strategy;

import com.acme.order.Customer;
import com.acme.order.pizza.PizzaType;

public interface DeliveryTimeStrategy {

	int provideDeliveryMinutesOffset(Customer customer, PizzaType pizzaType);

}
