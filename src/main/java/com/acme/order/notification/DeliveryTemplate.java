package com.acme.order.notification;

import java.util.Date;

public class DeliveryTemplate implements Template {

	private Date deliveryDate;
	private String deliveryAddress;

	public void putTime(Date date) {
		this.deliveryDate = date;
	}

	public void putAddress(String address) {
		this.deliveryAddress = address;
	}

	@Override
	public String getMessage() {
		return "Your order will be delivered to: '" + deliveryAddress + "' at:" + deliveryDate.toString();
	}

}
