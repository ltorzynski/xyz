package com.acme.order.delivery;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.acme.order.Customer;
import com.acme.order.pizza.PizzaType;

public class BasicDeliveryTimeServiceImpl implements DeliveryTimeService {

	private final TimeService timeService;

	public BasicDeliveryTimeServiceImpl(TimeService timeService) {
		this.timeService = timeService;
	}

	@Override
	public Date getTime(Customer customer, PizzaType type) {
		Date now = timeService.now();

		return (type == PizzaType.BIG || type == PizzaType.LARGE) ? DateUtils.addHours(now, 2) : DateUtils.addHours(
				now, 1);
	}
}
