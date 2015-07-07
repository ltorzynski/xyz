package com.acme.order.notification;

import org.springframework.stereotype.Component;

@Component
public class OrderCancelledTemplate implements Template {

	@Override
	public String getMessage() {
		return "Your super order has been cancelled. Bye";
	}
}
