package com.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmisorResource {
	private List<MessageDTO> respuestas = new ArrayList<>();
	
	@Value("${spring.application.name}:${server.port}")
	private String origen;

	@Autowired
	private AmqpTemplate amqp;
	@Autowired
    private DirectExchange exchange;

	@GetMapping(path = "/saludo/{nombre}")
	public String saluda(@PathVariable String nombre) {
		String msg = "Hola " + nombre;
		amqp.convertAndSend("demo.saludos", new MessageDTO(msg, origen));
		return "SEND: " + msg;
	}
	
	@GetMapping(path = "/respuestas")
	public List<MessageDTO> respuestas() {
        return respuestas;
	}
	
	@GetMapping(path = "/solicita/{nombre}")
	public String solicita(@PathVariable String nombre) {
		String msg = "Hola " + nombre + " (con respuesta)";
		new Thread(() -> respuestas.add((MessageDTO) amqp.convertSendAndReceive(exchange.getName(), "solicitud", new MessageDTO(msg, origen)))).run();
        return "SEND: " + msg + " (esperando respuesta)";
	}
	
	@Autowired
	private AsyncRabbitTemplate amqpAsync;
	
	@GetMapping(path = "/solicita-async/{nombre}")
	public String solicitaAsync(@PathVariable String nombre) {
		String msg = "Hola " + nombre + " (con respuesta)";
		amqpAsync.convertSendAndReceive(exchange.getName(), "solicitud", new MessageDTO(msg, origen))
			.addCallback(result -> respuestas.add((MessageDTO) result), ex -> System.out.println(ex.getMessage()));
        return "SEND: " + msg + " (esperando respuesta)";
	}
	
	
	
	
}
