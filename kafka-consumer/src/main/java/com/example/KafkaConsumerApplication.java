package com.example;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

import org.springframework.kafka.support.KafkaHeaders;

@EnableKafka
@SpringBootApplication
public class KafkaConsumerApplication {
	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerApplication.class);
	@Value("${consumer.mode:desconocido}")
	private String modo;

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerApplication.class, args);
	}

	Map<String, Integer> contadores = new HashMap<>();

	@KafkaListener(topics = "${topic.name}", topicPattern = "${topic.name}")
	public void listenWithHeaders(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Payload String message,
			@Header(KafkaHeaders.OFFSET) String offset) {
		switch (modo.substring(0, 4).toLowerCase()) {
		case "list":
			//LOG.info(String.format("KEY: %s, MESSAGE: %s, OFFSET: %s", key, message, offset));
			System.out.println(String.format("KEY: %s, MESSAGE: %s, OFFSET: %s", key, message, offset));
			break;
		case "calc":
			int count = contadores.containsKey(key) ? contadores.get(key) + 1 : 1;
			contadores.put(key, count);
			//LOG.info("Nuevo resumen");
			System.out.println("Nuevo resumen");
			contadores.forEach((clave, valor) -> {
				//LOG.info(String.format("KEY: %s, COUNT: %d", clave, valor));
				System.out.println(String.format("KEY: %s, COUNT: %d", clave, valor));
			});
			break;
		default:
			throw new IllegalArgumentException("Unexpected mode: " + modo.substring(0, 4).toLowerCase());
		}
	}

}
