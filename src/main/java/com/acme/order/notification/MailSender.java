package com.acme.order.notification;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSender {

	public void send(Template message, String email) {
		log.info("\n\nSending message: {} \n Mail recipient: {}\n", message.getMessage(), email);
	}

}
