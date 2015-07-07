package com.acme.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import com.acme.order.pizza.PizzaOrder;

@Slf4j
public class HashMapOrderRepository implements OrderRepository {

	private final Map<String, PizzaOrder> database = new HashMap<>();

	private static int sequence = 1;

	public String save(PizzaOrder order) {
		String uniqueId = Integer.valueOf(sequence++)
									.toString();
		order.setId(uniqueId);
		database.put(order.getId(), order);
		return order.getId();

	}

	public void rollback() {
		log.info("Fake db rollback...");
	}

	public PizzaOrder get(String pizzaOrderId) {
		return database.get(pizzaOrderId);
	}

	@Override
	public List<PizzaOrder> findAll() {
		return database.values()
						.stream()
						.collect(Collectors.toList());
	}

	public List<PizzaOrder> findByOrderStatus(OrderStatus orderStatus) {
		return database.values()
						.stream()
						.filter(o -> o.getState() == orderStatus)
						.collect(Collectors.toList());
	}

}
