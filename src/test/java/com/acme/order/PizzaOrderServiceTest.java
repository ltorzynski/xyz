package com.acme.order;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.acme.order.delivery.DeliveryTimeService;
import com.acme.order.notification.MailSender;
import com.acme.order.notification.MessageTemplateService;
import com.acme.order.notification.OrderCancelledTemplate;
import com.acme.order.notification.Template;
import com.acme.order.pizza.PizzaOrder;
import com.acme.order.pizza.PizzaOrderService;
import com.acme.order.pizza.PizzaOrderServiceImpl;
import com.acme.order.pizza.PizzaType;

public class PizzaOrderServiceTest {

	private PizzaOrderService pizzaOrderService;
	private MailSender mailSender;
	private OrderRepository orderRepository;
	private OrderFactory orderFactory;
	private DeliveryTimeService deliveryTimeService;
	private MessageTemplateService messageTemplate;

	@Before
	public void init() {
		mailSender = Mockito.mock(MailSender.class);
		orderRepository = Mockito.mock(OrderRepository.class);
		orderFactory = Mockito.mock(OrderFactory.class);
		deliveryTimeService = Mockito.mock(DeliveryTimeService.class);
		messageTemplate = Mockito.mock(MessageTemplateService.class);
		pizzaOrderService = new PizzaOrderServiceImpl(mailSender, orderRepository, orderFactory, deliveryTimeService,
				messageTemplate);
	}

	@Test
	public void cancelledOrderShouldBePersistedAndEmailShoudlBeSent() {
		// given
		String pizzaOrderId = "fake_id";
		PizzaOrder givenPizzaOrder = givenPizzaOrder();
		// stub
		OrderCancelledTemplate template = Mockito.mock(OrderCancelledTemplate.class);
		Mockito.when(orderRepository.get(Mockito.anyString()))
				.thenReturn(givenPizzaOrder);
		Mockito.when(messageTemplate.getCancelTemplate())
				.thenReturn(template);
		// when
		pizzaOrderService.cancelOrder(pizzaOrderId);
		// then
		Assert.assertTrue(givenPizzaOrder.isCancelled());
		ArgumentCaptor<String> sentEmailAddress = ArgumentCaptor.forClass(String.class);
		Mockito.verify(mailSender)
				.send(Mockito.any(Template.class), sentEmailAddress.capture());
		Assert.assertTrue(sentEmailAddress.getValue()
											.equals(givenPizzaOrder.getEmail()));

		Mockito.verify(orderRepository)
				.get(pizzaOrderId);
		ArgumentCaptor<PizzaOrder> savedPizzaOrder = ArgumentCaptor.forClass(PizzaOrder.class);
		Mockito.verify(orderRepository)
				.save(savedPizzaOrder.capture());

		Assert.assertTrue(savedPizzaOrder.getValue()
											.equals(givenPizzaOrder));

	}

	private PizzaOrder givenPizzaOrder() {
		Customer customer = givenCustomer();
		PizzaType type = PizzaType.NORMAL;
		PizzaOrder givenOrder = new PizzaOrder(customer, type);
		return givenOrder;
	}

	private Customer givenCustomer() {
		// String customerEmail = "fake_email";
		Customer customer = new Customer("John Smith", "john@smith.com", "Lodz,Jaracza 74");
		return customer;
	}
}
