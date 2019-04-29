package com.java.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.consumer.Order;

@RestController
public class PublisherController {
	
	@Autowired RabbitTemplate template;
	@PostMapping("/publish")
	public void sendMessage() {
		Order o= new Order();
		o.setCustId("c123");
		o.setAmount("40000");
		o.setOrderId("o101");
			template.convertAndSend("payment-exchange","process",o);
	}
}
