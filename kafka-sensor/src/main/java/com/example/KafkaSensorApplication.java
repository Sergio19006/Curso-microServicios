package com.example;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@SpringBootApplication
public class KafkaSensorApplication implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(KafkaSensorApplication.class);
	private Random rnd = new Random();
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaSensorApplication.class, args);
	}

	@Bean
	public NewTopic topicLocation() {
		return new NewTopic("sensores", 1, (short) 1);
	}

	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaBootstraServers;
	@Value("${topic.name}")
	private String tema;
	@Value("${sensor.id}")
	private String idSensor;

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 100; i++) {
//			sendMessage(tema, idSensor, "Evento " + i);
			sendObject(tema, idSensor, "Evento " + i);
			Thread.sleep((rnd.nextInt(6) + 1) * 500);
		}
	}

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String topic, String key, String message) {
		var future = kafkaTemplate.send(topic, key, message);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				LOG.info(String.format("TOPIC: %s, KEY: %s, MESSAGE: %s, OFFSET: %s", topic, key, message,
						result.getRecordMetadata().offset()));
			}

			@Override
			public void onFailure(Throwable ex) {
				LOG.error(String.format("TOPIC: %s, KEY: %s, MESSAGE: %s, ERROR: %s", topic, key, message,
						ex.getMessage()));
			}
		});
	}

	@Data
	public class MessageDTO implements Serializable {
		private static final long serialVersionUID = 1L;
		private String msg;
		private String origen;
		private Date enviado = new Date();

		public MessageDTO() {
		}

		public MessageDTO(String msg, String origen) {
			this.msg = msg;
			this.origen = origen;
		}
	}

	private ObjectMapper objectMapper = new ObjectMapper();

	public void sendObject(String topic, String key, String message) {
		try {
			String json = objectMapper.writeValueAsString(new MessageDTO(message, key));
			sendMessage(topic, key, json);
		} catch (JsonProcessingException e) {
			LOG.error("Error send", e);
		}
	}
//	@Bean
//	public <T> ProducerFactory<String, T> producerFactoryJson() {
//		Map<String, Object> config = new HashMap<>();
//		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstraServers);
//		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//		return new DefaultKafkaProducerFactory<>(config);
//	}
//
//	@Bean
//	public KafkaTemplate<String, MessageDTO> generateKafkaTemplateJson() {
//		return new KafkaTemplate<>(producerFactoryJson());
//	}
//
//	@Autowired
//	private KafkaTemplate<String, MessageDTO> kafkaJsonTemplate = generateKafkaTemplateJson();
//
//	public void sendObject(String topic, String key, String message) {
//		var future = kafkaJsonTemplate.send(topic, key, new MessageDTO(message, key));
//		future.addCallback(new ListenableFutureCallback<SendResult<String, MessageDTO>>() {
//			@Override
//			public void onSuccess(SendResult<String, MessageDTO> result) {
//				LOG.info(String.format("TOPIC: %s, KEY: %s, MESSAGE: %s, OFFSET: %s", topic, key, message,
//						result.getRecordMetadata().offset()));
//			}
//
//			@Override
//			public void onFailure(Throwable ex) {
//				LOG.error(String.format("TOPIC: %s, KEY: %s, MESSAGE: %s, ERROR: %s", topic, key, message,
//						ex.getMessage()));
//			}
//		});
//	}

}
