package com.example.application.resource;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.FilmDetailsDTO;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "peliculas-service", description = "Mantenimiento de peliculas")
@RequestMapping(path = "/v1/peliculas")
public class FilmResource {
	@Autowired
	private FilmRepository dao;

	@GetMapping(params = "page")
	public Page<FilmShortDTO> getAll(@ParameterObject Pageable pageable,
			@Parameter(allowEmptyValue = true, required = false, schema = @Schema(type = "string", allowableValues = {"details","short"}, defaultValue = "short")) 
			@RequestParam(defaultValue = "short") String mode) {
		return dao.findByFilmIdNotNull(pageable, FilmShortDTO.class);
	}

	@GetMapping(params = {"page", "mode=details" })
	@Transactional
	public Page<FilmDetailsDTO> getAllDetailsPage(@ParameterObject Pageable pageable,
			@Parameter(allowEmptyValue = true, required = false, schema = @Schema(type = "string", allowableValues = {"details","short"}, defaultValue = "short")) 
			@RequestParam(defaultValue = "short") String mode) {
		var content = dao.findAll(pageable);
		return new PageImpl<>(content.getContent().stream().map(item -> FilmDetailsDTO.from(item)).toList(), 
				pageable, content.getTotalElements());
	}

	@GetMapping
	public List<FilmShortDTO> getAll(
			@Parameter(allowEmptyValue = true, required = false, schema = @Schema(type = "string", allowableValues = {"details","short"}, defaultValue = "short")) 
			@RequestParam(defaultValue = "short") String mode) {
		return dao.findAll().stream().map(item -> FilmShortDTO.from(item)).toList();
	}

	@Operation(summary = "Listado de las peliculas")
	@GetMapping(params = "mode=details")
	public List<FilmDetailsDTO> getAllDetails(
			@Parameter(allowEmptyValue = true, required = false, schema = @Schema(type = "string", allowableValues = {"details","short"}, defaultValue = "short")) 
			@RequestParam(defaultValue = "short") String mode) {
		return dao.findAll().stream().map(item -> FilmDetailsDTO.from(item)).toList();
	}

	@GetMapping(path = "/{id}", params = "mode=short")
	public FilmShortDTO getOneCorto(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id,
			@Parameter(required = false, allowEmptyValue = true, schema = @Schema(type = "string", allowableValues = {
					"details", "short",
					"edit" }, defaultValue = "edit")) @RequestParam(required = false, defaultValue = "edit") String mode)
			throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return FilmShortDTO.from(rslt.get());
	}

	@GetMapping(path = "/{id}", params = "mode=details")
	public FilmDetailsDTO getOneDetalle(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id,
			@Parameter(required = false, schema = @Schema(type = "string", allowableValues = { "details", "short",
					"edit" }, defaultValue = "edit")) @RequestParam(required = false, defaultValue = "edit") String mode)
			throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return FilmDetailsDTO.from(rslt.get());
	}

	@Operation(summary = "Recupera una pelicula", description = "Están disponibles una versión corta, una versión con los detalles donde se han transformado las dependencias en cadenas y una versión editable donde se han transformado las dependencias en sus identificadores.")
	@GetMapping(path = "/{id}")
	@ApiResponse(responseCode = "200", description = "Pelicula encontrada", 
		content = @Content(schema = @Schema(oneOf = {FilmShortDTO.class, FilmDetailsDTO.class, FilmEditDTO.class})))
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	public FilmEditDTO getOneEditar(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id,
			@Parameter(required = false, schema = @Schema(type = "string", allowableValues = { "details", "short",
					"edit" }, defaultValue = "edit")) @RequestParam(required = false, defaultValue = "edit") String mode)
			throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return FilmEditDTO.from(rslt.get());
	}

	@Operation(summary = "Listado de los actores de la pelicula")
	@ApiResponse(responseCode = "200", description = "Pelicula encontrada")
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	@GetMapping(path = "/{id}/reparto")
	@Transactional
	public List<ActorDTO> getFilms(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id)
			throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get().getFilmActors().stream().map(item -> ActorDTO.from(item.getActor())).toList();
	}

	@Operation(summary = "Listado de las categorias de la pelicula")
	@ApiResponse(responseCode = "200", description = "Pelicula encontrada")
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	@GetMapping(path = "/{id}/categorias")
	@Transactional
	public List<Category> getCategories(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id)
			throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get().getFilmCategories().stream().map(item -> item.getCategory()).toList();
	}
	
	@GetMapping(path = "/clasificaciones")
	@Operation(summary = "Listado de las clasificaciones por edades")
	public List<Map<String, String>> getClasificaciones() {
		return List.of(
				Map.of("key", "G", "value", "Todos los públicos"), 
				Map.of("key", "PG", "value", "Guía paternal sugerida"), 
				Map.of("key", "PG-13", "value", "Guía paternal estricta"), 
				Map.of("key", "R", "value", "Restringido"), 
				Map.of("key", "NC-17", "value", "Prohibido para audiencia de 17 años y menos")
				);
	}


	@Operation(summary = "Añadir una nueva pelicula")
	@ApiResponse(responseCode = "201", description = "Pelicula añadida")
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Transactional
	public ResponseEntity<Object> add(@Valid @RequestBody FilmEditDTO item) throws Exception {
		Film rslt = FilmEditDTO.from(item);
		if (rslt.isInvalid())
			throw new InvalidDataException(rslt.getErrorsString(), rslt.getErrorsFields());
		if (dao.findById(item.getFilmId()).isPresent())
			throw new InvalidDataException("Duplicate key");
		var f = dao.save(rslt);
		item.getActors().stream().forEach(id -> rslt.addFilmActor(new Actor(id)));
		item.getCategories().stream().forEach(id -> rslt.addFilmCategory(new Category(id)));
		dao.save(rslt);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rslt.getFilmId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@Operation(summary = "Modificar una pelicula existente", description = "Los identificadores deben coincidir")
	@ApiResponse(responseCode = "200", description = "Pelicula encontrada")
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	@Transactional
	@PutMapping(path = "/{id}")
	public FilmEditDTO modify(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id,
			@Valid @RequestBody FilmEditDTO item) throws Exception {
		if (item.getFilmId() != id)
			throw new BadRequestException("No coinciden los identificadores");
		Film rslt = FilmEditDTO.from(item);
		if (rslt.isInvalid())
			throw new InvalidDataException(rslt.getErrorsString(), rslt.getErrorsFields());
		Optional<Film> act = dao.findById(item.getFilmId());
		if (!act.isPresent())
			throw new NotFoundException("Missing item");
		rslt = dao.save(item.update(act.get()));
		return FilmEditDTO.from(rslt);
	}

	@Operation(summary = "Borrar una pelicula existente")
	@ApiResponse(responseCode = "204", description = "Pelicula borrada")
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id)
			throws Exception {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			throw new NotFoundException("Missing item", e);
		}
	}

	public List<Film> novedades(Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}
