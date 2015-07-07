package com.acme.order.pizza;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.order.Customer;
import com.acme.order.OrderFactory;
import com.acme.order.OrderRepository;
import com.acme.order.OrderStatus;
import com.acme.order.delivery.DeliveryTimeService;
import com.acme.order.notification.DeliveryTemplate;
import com.acme.order.notification.MailSender;
import com.acme.order.notification.MessageTemplateService;
import com.acme.order.notification.OrderCancelledTemplate;

@Slf4j
@Service
public class PizzaOrderServiceImpl implements PizzaOrderService {

	private final MailSender mailSender;

	private final OrderRepository orderRepository;

	private final OrderFactory orderFactory;

	private final DeliveryTimeService deliveryTimeService;

	private final MessageTemplateService messageTemplate;

	@Autowired
	public PizzaOrderServiceImpl(MailSender mailSender, OrderRepository orderRepository, OrderFactory orderFactory,
			DeliveryTimeService deliveryTimeService, MessageTemplateService messageTemplate) {
		this.orderFactory = orderFactory;
		this.orderRepository = orderRepository;
		this.deliveryTimeService = deliveryTimeService;
		this.messageTemplate = messageTemplate;
		this.mailSender = mailSender;
	}

	@Override
	public String createOrder(Customer customer, PizzaType type) {
		PizzaOrder order = null;
		try {
			log.info("##############################");
			log.info("Ordering pizza type: {} for customer: {}", type, customer);
			order = orderFactory.create(customer, type);
			Date date = deliveryTimeService.getTime(customer, type);
			order.withEstimatedTime(date);
			String orderId = orderRepository.save(order);
			mailSender.send(createMessage(order), customer.getEmail());
			return orderId;
		} catch (Exception e) {
			log.error("Error while creating order", e);
			orderRepository.rollback();
			return null;
		} finally {
			log.info("##############################\n");
		}
	}

	@Override
	public void cancelOrder(String pizzaOrderId) {
		log.info("Cancelling order with id: {}", pizzaOrderId);
		PizzaOrder order = orderRepository.get(pizzaOrderId);
		order.cancel();
		orderRepository.save(order);
		OrderCancelledTemplate template = messageTemplate.getCancelTemplate();
		mailSender.send(template, order.getEmail());
	}

	@Override
	public void deliverOrder(String pizzaOrderId) {
		log.info("Delivering order with id: {}", pizzaOrderId);
		PizzaOrder order = orderRepository.get(pizzaOrderId);
		order.deliver();
	}

	private DeliveryTemplate createMessage(PizzaOrder order) {
		DeliveryTemplate template = messageTemplate.getDeliveryTemplate();
		template.putTime(order.getEstimatedTime());
		template.putAddress(order.getAddress());
		return template;
	}

	@Override
	public List<PizzaOrder> fetchOrders() {
		return orderRepository.findAll();
	}

	@Override
	public List<PizzaOrder> fetchDelivered() {
		return orderRepository.findByOrderStatus(OrderStatus.DELIVERED);
	}

	@Override
	public List<PizzaOrder> fetchUnprocessed() {
		return orderRepository.findByOrderStatus(OrderStatus.CREATED);
	}

	@Override
	public List<PizzaOrder> fetchCancelled() {
		return orderRepository.findByOrderStatus(OrderStatus.CANCELLED);
	}
}
