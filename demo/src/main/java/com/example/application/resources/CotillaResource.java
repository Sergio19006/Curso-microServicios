package com.example.application.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.application.proxies.CatalogoProxy;
import com.example.domains.entities.dtos.PeliculaCortoDto;

@RestController
@RequestMapping("/api/v1/cotilla")
public class CotillaResource {
	@Autowired 
	RestTemplate srvRest;

	@GetMapping("/balanceo/rt")
	public String balanceoRt() {
		return srvRest.getForObject("lb://CATALOGO-SERVICE/", String.class);
//		return srvRest.getForObject("http://localhost:8010/", String.class);
	}
	@GetMapping("/peliculas/rt")
	public List<PeliculaCortoDto> peliculasRt() {
		ResponseEntity<List<PeliculaCortoDto>> response = srvRest.exchange(
				"lb://CATALOGO-SERVICE/v1/peliculas?mode=short", 
//				"http://localhost:8010/v1/peliculas?mode=short", 
				HttpMethod.GET,
				HttpEntity.EMPTY, 
				new ParameterizedTypeReference<List<PeliculaCortoDto>>() {
				});

		return response.getBody();
	}
	@GetMapping("/peliculas/rt/{id}")
	public PeliculaCortoDto peliculaRt(@PathVariable int id) {
		return srvRest.getForObject("lb://CATALOGO-SERVICE/v1/peliculas/{id}?mode=short", PeliculaCortoDto.class, id);
//		return srvRest.getForObject("http://localhost:8010/v1/peliculas/{id}?mode=short", PeliculaCortoDto.class, id);
	}
	
	@Autowired
	CatalogoProxy proxy;
	
	@GetMapping("/balanceo/proxy")
	public String balanceoProxy() {
		return proxy.getCatalogo();
	}
	@GetMapping("/peliculas/proxy")
	public List<PeliculaCortoDto> peliculasProxy() {
		return proxy.getPeliculas();
	}
	@GetMapping("/peliculas/proxy/{id}")
	public PeliculaCortoDto peliculaProxy(@PathVariable int id) {
		return proxy.getPelicula(id);
	}
	
	@Value("${particular.para.demos}")
	String configString;
	
	@Value("${comun.para.todos}")
	String configComun;

	@GetMapping("/config")
	public String getConfig() {
		return configString + "<--->" + configComun;
	}

	
}
