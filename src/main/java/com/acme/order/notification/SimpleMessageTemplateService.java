package com.acme.order.notification;

public class SimpleMessageTemplateService implements MessageTemplateService {

	private final DeliveryTemplate deliveryTemplate;
	private final OrderCancelledTemplate cancelDeliveryTemplate;

	public SimpleMessageTemplateService() {
		this.deliveryTemplate = new DeliveryTemplate();
		this.cancelDeliveryTemplate = new OrderCancelledTemplate();
	}

	@Override
	public DeliveryTemplate getDeliveryTemplate() {
		return deliveryTemplate;
	}

	@Override
	public OrderCancelledTemplate getCancelTemplate() {
		return cancelDeliveryTemplate;
	}

}
