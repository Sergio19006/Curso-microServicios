package com.example.application.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.domains.entities.dtos.PeliculaCortoDto;

@FeignClient(name = "CATALOGO-SERVICE")
public interface CatalogoProxy {
	@GetMapping("/")
	String getCatalogo();
	@GetMapping("/v1/peliculas")
	public List<PeliculaCortoDto> getPeliculas();
	@GetMapping("/v1/peliculas/{id}")
	public PeliculaCortoDto getPelicula(@PathVariable int id);
}
