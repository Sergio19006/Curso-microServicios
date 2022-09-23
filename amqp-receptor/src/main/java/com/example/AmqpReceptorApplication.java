package com.example;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Microservicio: AMQP Receptor",
                version = "1.0",
                description = "Monta diferentes escenacios para las pruebas de concepto de Microservicio.",
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(name = "Javier Martín", url = "https://github.com/jmagit", email = "support@example.com")
        ),
        externalDocs = @ExternalDocumentation(description = "Documentación del proyecto", url = "https://github.com/jmagit/REM20220725")
)
@SpringBootApplication
public class AmqpReceptorApplication {
	private static final Logger LOGGER = Logger.getLogger(AmqpReceptorApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(AmqpReceptorApplication.class, args);
	}
	@Bean
	public MessageConverter jsonConverter() {
		return new Jackson2JsonMessageConverter();
	}
	@Bean 
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@RabbitListener(queues = "demo.saludos")
	public void listen(MessageDTO in) {
		Store.add(new Message(in));
		LOGGER.warning("MENSAJE RECIBIDO: " + in);
	}
	
	@Bean
	public Queue peticionQueue() {
		return new Queue("demo.peticiones");
	}

	@Bean
	public DirectExchange exchange() {
		return new DirectExchange("demo.rpc");
	}

	@Bean
	public Binding binding(DirectExchange exchange, Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with("solicitud");
	}
	
	@Value("${spring.application.name}:${server.port}")
	private String origen;

	@RabbitListener(queues = "demo.peticiones")
	public MessageDTO responde(MessageDTO in) throws InterruptedException {
		LOGGER.warning("SOLICITUD RECIBIDA: " + in);
		Thread.sleep(in.getMsg().length() * 1000);
		LOGGER.warning("RESPONDIENDO:" + new Date(System.currentTimeMillis()));
		return new MessageDTO(in.toString(), origen);
	}
}
