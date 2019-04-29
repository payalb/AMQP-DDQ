package com.java.consumer;

import java.util.Random;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class MyConsumer {

	@Autowired RabbitTemplate template;
	@RabbitHandler
	@RabbitListener(queues="queue-one-payment")
	public void onMessage(Order message) {
		System.out.println("Message is being processed "+message);
		//If no balance, invalid card details, processing should fail
		//TODO code for it
		double random=Math.random();
		if(random<0.5) {
			if(message.getCount()>=12) {
				template.convertAndSend("exchange-customer", "failure", message);
				System.out.println("Message sent to customer service "+ message);
				return;
			}
			message.setCount(message.getCount()+1);
			throw new AmqpRejectAndDontRequeueException("Message processing failed"+ message);
		}else {
		System.out.println("Message processed successfully "+message);
		}
	}
	
	
}
