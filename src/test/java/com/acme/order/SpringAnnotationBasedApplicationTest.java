package com.acme.order;

import static org.junit.Assert.assertEquals;
import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoAnnotatedContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acme.order.application.SpringAnnotationBasedApplication;
import com.acme.order.delivery.strategy.DeliveryTimeStrategy;
import com.acme.order.delivery.strategy.PizzaTypeDeliveryTimeStrategy;
import com.acme.order.pizza.PizzaOrderService;
import com.acme.order.pizza.PizzaType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoAnnotatedContextLoader.class, classes = SpringAnnotationBasedApplication.class)
@ActiveProfiles("pizzaType")
@Slf4j
public class SpringAnnotationBasedApplicationTest {

	@Autowired
	private PizzaOrderService orderService;

	@Autowired
	private DeliveryTimeStrategy deliveryTimeStrategy;

	@Before
	public void before() {
		assertEquals(PizzaTypeDeliveryTimeStrategy.class, deliveryTimeStrategy.getClass());
	}

	@Test
	public void testApplication() {
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

}
