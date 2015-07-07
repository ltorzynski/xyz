package com.acme.order.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleMessageTemplateService implements MessageTemplateService {
	@Autowired
	private DeliveryTemplate deliveryTemplate;
	@Autowired
	private OrderCancelledTemplate cancelDeliveryTemplate;

	@Override
	public DeliveryTemplate getDeliveryTemplate() {
		return deliveryTemplate;
	}

	@Override
	public OrderCancelledTemplate getCancelTemplate() {
		return cancelDeliveryTemplate;
	}

}
