package com.acme.order.notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailSender {

	public void send(Template message, String email) {
		log.info("\n\nSending message: {} \n Mail recipient: {}\n", message.getMessage(), email);
	}

}
