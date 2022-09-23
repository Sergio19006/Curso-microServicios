package com.example;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping(path = "/me-gusta")
public class MeGustaService {
	public final String ME_GUSTA_CONT = "megusta";
	@Autowired
	private StringRedisTemplate template;
	private ValueOperations<String, String> redisValue;

	@PostConstruct
	private void inicializa() {
		redisValue = template.opsForValue();
		if (redisValue.get(ME_GUSTA_CONT) == null)
			redisValue.set(ME_GUSTA_CONT, "0");
	}

	@GetMapping
	@Operation(summary = "Informa de cuantos Me Gusta lleva actualmente")
	private String get() {
		return "Llevas " + redisValue.get(ME_GUSTA_CONT);
	}

	@PostMapping
	@Operation(summary = "Manda un Me Gusta")
	private String add() {
		return "Llevas " + redisValue.increment(ME_GUSTA_CONT);
	}

	@PutMapping("/{miles}")
	@Operation(summary = "Manda miles de Me Gusta")
	private String add(@Parameter(description = "Número de miles a enviar") @PathVariable int miles) {
		long r = 0;
		Date ini = new Date();
		for (int i = 0; i++ < miles * 1000; r = redisValue.increment(ME_GUSTA_CONT));
		return "Llevas " + r + ". Ha tardado " + ((new Date()).getTime() - ini.getTime()) + " ms.";
	}

}
