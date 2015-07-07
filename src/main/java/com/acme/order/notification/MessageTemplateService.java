package com.acme.order.notification;

public interface MessageTemplateService {

	DeliveryTemplate getDeliveryTemplate();

	OrderCancelledTemplate getCancelTemplate();

}
