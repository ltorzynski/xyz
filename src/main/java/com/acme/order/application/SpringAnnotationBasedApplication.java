package com.acme.order.application;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.acme.order.Customer;
import com.acme.order.HashMapOrderRepository;
import com.acme.order.OrderFactory;
import com.acme.order.OrderRepository;
import com.acme.order.delivery.BasicDeliveryTimeServiceImpl;
import com.acme.order.delivery.DeliveryTimeService;
import com.acme.order.delivery.TimeService;
import com.acme.order.notification.MailSender;
import com.acme.order.notification.MessageTemplateService;
import com.acme.order.notification.SimpleMessageTemplateService;
import com.acme.order.pizza.PizzaOrderService;
import com.acme.order.pizza.PizzaOrderServiceImpl;
import com.acme.order.pizza.PizzaType;

@Slf4j
@Configuration
@ComponentScan("com.acme.order")
public class SpringAnnotationBasedApplication {

	public static void main(String[] args) {
		log.info("Spring XML based application");

		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringAnnotationBasedApplication.class);
		PizzaOrderService orderService = ctx.getBean(PizzaOrderService.class);

		Customer customer1 = new Customer("John Smith", "john@smith.com", "Lodz, Jaracza 74");
		Customer customer2 = new Customer("Jan Kowalski", "jan@kowalski.pl", "Lodz, Piotrkowska 100");

		String orderId1 = orderService.createOrder(customer1, PizzaType.LARGE);
		String orderId2 = orderService.createOrder(customer2, PizzaType.SMALL);

		log.info("Unprocessed orders:{}", orderService.fetchUnprocessed());
		log.info("Delivered orders:{}", orderService.fetchDelivered());

		orderService.deliverOrder(orderId1);
		log.info("Delivered orders:{}", orderService.fetchDelivered());
		orderService.cancelOrder(orderId2);
		log.info("Delivered orders:{}", orderService.fetchDelivered());
		log.info("Cancelled orders:{}", orderService.fetchCancelled());
		log.info("Unprocessed orders:{}", orderService.fetchUnprocessed());
	}

	@Bean
	@Scope
	public PizzaOrderService orderService() {
		return new PizzaOrderServiceImpl(mailSender(), repository(), orderFactory(), deliveryTimeService(),
				messageTemplate());
	}

	@Bean
	public MailSender mailSender() {
		return new MailSender();
	}

	@Bean
	public OrderRepository repository() {
		return new HashMapOrderRepository();
	}

	@Bean
	public OrderFactory orderFactory() {
		return new OrderFactory();
	}

	@Bean
	public DeliveryTimeService deliveryTimeService() {
		BasicDeliveryTimeServiceImpl deliveryTimeService = new BasicDeliveryTimeServiceImpl();
		deliveryTimeService.setTimeService(timeService());
		// deliveryTimeService.setStrategy(deliveryTimeStrategy());
		return deliveryTimeService;
	}

	/*
	 * @Bean
	 * public DeliveryTimeStrategy deliveryTimeStrategy() {
	 * return new SimpleDeliveryTimeStrategy();
	 * }
	 */

	@Bean
	public MessageTemplateService messageTemplate() {
		return new SimpleMessageTemplateService();
	}

	@Bean
	public TimeService timeService() {
		return new TimeService();
	}

}
