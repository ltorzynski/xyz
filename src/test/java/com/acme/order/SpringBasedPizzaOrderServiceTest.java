package com.acme.order;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoAnnotatedContextLoader;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acme.order.application.SpringAnnotationBasedApplication;
import com.acme.order.notification.MailSender;
import com.acme.order.notification.MessageTemplateService;
import com.acme.order.notification.Template;
import com.acme.order.pizza.PizzaOrder;
import com.acme.order.pizza.PizzaOrderService;
import com.acme.order.pizza.PizzaType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoAnnotatedContextLoader.class, classes = SpringAnnotationBasedApplication.class)
@ActiveProfiles("pizzaType")
public class SpringBasedPizzaOrderServiceTest {

	@Autowired
	private PizzaOrderService pizzaOrderService;
	@ReplaceWithMock
	@Autowired
	private MailSender mailSender;
	@ReplaceWithMock
	@Autowired
	private OrderRepository orderRepository;
	@ReplaceWithMock
	private OrderFactory orderFactory;
	@ReplaceWithMock
	private MessageTemplateService messageTemplate;

	@Before
	public void init() {
	}

	@Test
	public void cancelledOrderShouldBePersistedAndEmailShoudlBeSent() {
		// given
		String pizzaOrderId = "fake_id";
		PizzaOrder givenPizzaOrder = givenPizzaOrder();
		// stub
		// OrderCancelledTemplate template = Mockito.mock(OrderCancelledTemplate.class);
		Mockito.when(orderRepository.get(Mockito.anyString()))
				.thenReturn(givenPizzaOrder);
		// Mockito.when(messageTemplate.getCancelTemplate())
		// .thenReturn(template);
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
