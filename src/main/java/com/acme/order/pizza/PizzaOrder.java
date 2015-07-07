package com.acme.order.pizza;

import java.util.Date;

import lombok.Getter;
import lombok.ToString;

import com.acme.order.Customer;
import com.acme.order.OrderStatus;

@ToString
public class PizzaOrder {

	private String id;
	private Date estimatedDeliveryTime;
	private Date finishTime;
	@Getter
	private OrderStatus state;
	private final Customer customer;
	private final PizzaType pizzaType;

	public PizzaOrder(Customer customer, PizzaType pizzaType) {
		this.state = OrderStatus.CREATED;
		this.customer = customer;
		this.pizzaType = pizzaType;
	}

	public void withEstimatedTime(Date date) {
		this.estimatedDeliveryTime = date;
	}

	public Date getEstimatedTime() {
		return estimatedDeliveryTime;
	}

	public void cancel() {
		this.state = OrderStatus.CANCELLED;
	}

	public boolean isCreated() {
		return this.state == OrderStatus.CREATED;
	}

	public boolean isCancelled() {
		return this.state == OrderStatus.CANCELLED;
	}

	public boolean isDelivered() {
		return this.state == OrderStatus.DELIVERED;
	}

	public String getEmail() {
		return customer.getEmail();
	}

	public String getAddress() {
		return customer.getAddress();
	}

	public void deliver() {
		this.state = OrderStatus.DELIVERED;
		finishTime = new Date();
	}

	public boolean wasDeliveredOnTime() {
		if (estimatedDeliveryTime == null) {
			return true;
		}
		if (state == OrderStatus.DELIVERED) {
			return estimatedDeliveryTime.after(finishTime);
		}
		throw new IllegalStateException("The Pizza is not delivered yet!");
	}

	public PizzaType getPizzaType() {
		return pizzaType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
