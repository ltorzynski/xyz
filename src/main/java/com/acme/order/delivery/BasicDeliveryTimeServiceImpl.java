package com.acme.order.delivery;

import java.util.Date;

import lombok.Setter;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.order.Customer;
import com.acme.order.delivery.strategy.DeliveryTimeStrategy;
import com.acme.order.pizza.PizzaType;

@Service
public class BasicDeliveryTimeServiceImpl implements DeliveryTimeService {

	@Setter
	@Autowired
	private TimeService timeService;
	@Setter
	@Autowired
	private DeliveryTimeStrategy strategy;

	@Override
	public Date getTime(Customer customer, PizzaType type) {

		int minutes = strategy.provideDeliveryMinutesOffset(customer, type);

		Date now = timeService.now();

		return DateUtils.addMinutes(now, minutes);
	}
}
