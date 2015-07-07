package com.acme.order;

import java.util.List;

import com.acme.order.pizza.PizzaOrder;

public interface OrderRepository {

	String save(PizzaOrder order);

	void rollback();

	PizzaOrder get(String pizzaOrderId);

	List<PizzaOrder> findAll();

	List<PizzaOrder> findByOrderStatus(OrderStatus orderStatus);

}
