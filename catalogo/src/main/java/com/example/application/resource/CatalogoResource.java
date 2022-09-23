package com.example.application.resource;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@RestController
@RequestMapping(path = "/")
public class CatalogoResource {
	@Autowired
	private FilmResource filmSrv;
	@Autowired
	private ActorService artorSrv;
	@Autowired
	private CategoryService categorySrv;
	@Autowired
	private LanguageResource languageSrv;

	@Value
	public static class CatalogoResources {
		@Value
		public class CatalogoLinks {
			public class Href {
				private String href;
				public String getHref() { return href; }
				public Href(String path) {
					href = ServletUriComponentsBuilder.fromCurrentRequest().path(path).toUriString();
				}
			}
			private Href self = new Href("");
			private Href actores = new Href("/v1/actores");
			private Href peliculas = new Href("/v1/peliculas");
			private Href categorias = new Href("/v1/categorias");
			private Href idiomas = new Href("/v1/idiomas");
			private Href novedades = new Href("/v1/novedades");
		}

		private CatalogoLinks _links = new CatalogoLinks();
	}
	
	@Data @AllArgsConstructor @NoArgsConstructor
	public static class NovedadesDTO {
		private List<FilmEditDTO> films;
		private List<Actor> actors;
		private List<Category> categories;
		@JsonView(Language.Complete.class)
		private List<Language> idiomas;
	}

	@GetMapping(path = "/")
	public ResponseEntity<CatalogoResources> getResources() {
		return ResponseEntity.ok().header("Content-Type", "application/hal+json").body(new CatalogoResources());
	}
	
	@GetMapping(path = "/v1/novedades")
	public NovedadesDTO novedades(@Parameter(example = "2021-01-01 00:00:00") @RequestParam(required = false) Timestamp fecha) {
		// Timestamp fecha = Timestamp.valueOf("2019-01-01 00:00:00");
		if(fecha == null)
			fecha = Timestamp.from(Instant.now().minusSeconds(36000));
		return new NovedadesDTO(
				filmSrv.novedades(fecha).stream().map(item -> FilmEditDTO.from(item)).toList(), 
				artorSrv.novedades(fecha), 
				categorySrv.novedades(fecha), 
				languageSrv.novedades(fecha)
				);
	}

}
