package com.example.equipment_booker;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class EquipmentBookerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EquipmentBookerApplication.class, args);
	}

	@Value("${myqueue2}")
	String queue2;

	@Value("${myqueue4}")
	String queue4;

	@Value("${myqueue6}")
	String queue6;

	@Bean
	Queue queue() {
		return new Queue(queue2, true);
	}

	@Bean
	Queue queue4() {
		return new Queue(queue4, true);
	}

	@Bean
	Queue queue6() {
		return new Queue(queue6, true);
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		return connectionFactory;
	}
}
