package com.acme.order.notification;

public class OrderCancelledTemplate implements Template {

	@Override
	public String getMessage() {
		return "Your super order has been cancelled. Bye";
	}
}
