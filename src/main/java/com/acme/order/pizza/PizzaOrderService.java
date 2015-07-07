package com.acme.order.pizza;

import java.util.List;

import com.acme.order.Customer;

public interface PizzaOrderService {

	String createOrder(Customer customer, PizzaType type);

	void cancelOrder(String pizzaOrderId);

	void deliverOrder(String pizzaOrderId);

	List<PizzaOrder> fetchOrders();

	List<PizzaOrder> fetchDelivered();

	List<PizzaOrder> fetchUnprocessed();

	List<PizzaOrder> fetchCancelled();

}
