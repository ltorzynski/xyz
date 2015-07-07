package com.acme.order;

import java.util.Date;

public class PizzaOrderService {

	private MailSender mailSender;

	private OrderDatabase orderDatabase;

	private OrderFactory orderFactory;

	private DeliveryTimeService deliveryTimeService;
	
	private MessageTemplateService messageTemplate;
	
	

	public PizzaOrderService(MailSender mailSender,
			OrderDatabase orderDatabase, OrderFactory orderFactory,
			DeliveryTimeService deliveryTimeService,
			MessageTemplateService messageTemplate) {
		super();
		this.mailSender = mailSender;
		this.orderDatabase = orderDatabase;
		this.orderFactory = orderFactory;
		this.deliveryTimeService = deliveryTimeService;
		this.messageTemplate = messageTemplate;
	}

	public void createOrder(Customer customer, PizzaType type) {
		try {
			PizzaOrder order = orderFactory.create(customer, type);
			Date date = deliveryTimeService.getTime(customer, type);
			order.withEstimatedTime(date);
			orderDatabase.save(order);
			mailSender.send(createMessage(order), customer.getEmail());
		} catch (Exception e) {
			orderDatabase.rollback();
		}
	}

	private DeliveryTemplate createMessage(PizzaOrder order) {
		DeliveryTemplate template = messageTemplate.getDeliveryTemplpate();
		template.putTime(order.getEstimatedTime());
		template.putAddress(order.getAddress());
		return template;
	}

	public void cancelOrder(String pizzaOrderId) {
		PizzaOrder order = orderDatabase.get(pizzaOrderId);
		OrderCanceledTemplate template = messageTemplate.getCancelTemplate();
		order.cancel();
		mailSender.send(template, order.getEmail());
		orderDatabase.save(order);
	}

	public void deliverOrder(String pizzaOrderId) {
		PizzaOrder order = orderDatabase.get(pizzaOrderId);
		order.deliver();
	}
}
