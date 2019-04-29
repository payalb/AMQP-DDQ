package com.java;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyClient {

	public static void main(String[] args) {
		
		SpringApplication.run(MyClient.class, args);
	}
	
	//If the queue has x-message-ttl set,if the message comes to queue and 
	//is not picked up by any consumer in that time, message would be dropped
	
	//If u specify x-dead-letter-exchange, instead of being dropped, 
	//after expiry, they would be moved to this exchange
	
	//Message reaches a consumer and  consumer does not acknowledge it, it
	//would not be removed from the queue. When u create message listener, and 
	//some xeception occurs, the message is not acknowledged by default
	
	//When i get a  message and payment fails, we want to send the message to
	//dead letter exchange ad we will bring it back to this queue for processing
	//after 6 hours
	
	@Bean
	public Queue queue() {
		return QueueBuilder.durable("queue-one-payment")
				.withArgument("x-dead-letter-exchange", "exchange-ddl")
				.build();
	}

	@Bean
	public Exchange exchange() {
		return new TopicExchange("payment-exchange");
	}
	@Bean
	public Exchange deadLetterExchange() {
		return new TopicExchange("exchange-ddl");
	}
	
	@Bean
	public Queue ddlQueue() {
		return QueueBuilder.durable("queue-ddl")
				.withArgument("x-dead-letter-exchange", "payment-exchange")
				.withArgument("x-message-ttl", 1*60*1000)
				.build();
	}
	@Bean
	public Binding binding1() {
		return BindingBuilder.bind(ddlQueue()).to(deadLetterExchange()).with("process").noargs();
	}
	
	@Bean
	public Binding binding2() {
		return BindingBuilder.bind(queue()).to(exchange()).with("process").noargs();
	}
	
	@Bean
	public Queue queueCustomer() {
		return QueueBuilder.durable("queue-customer")
				.build();
	}

	@Bean
	public Exchange exchangeCustomer() {
		return new TopicExchange("exchange-customer");
	}
	
	@Bean
	public Binding getBindingForCustomer() {
		return BindingBuilder.bind(queueCustomer()).to(exchangeCustomer()).with("process").noargs();
	}
}
