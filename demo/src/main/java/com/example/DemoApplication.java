package com.example;

import java.util.TreeMap;

import javax.transaction.Transactional;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.RestTemplate;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDto;
import com.example.domains.entities.dtos.ActorName;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.Data;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Microservicio: Demos",  version = "1.0",
                description = "**Demos** de Microservicios.",
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(name = "Javier Martín", url = "https://github.com/jmagit", email = "support@example.com")
        ),
        externalDocs = @ExternalDocumentation(description = "Documentación del proyecto", url = "https://github.com/jmagit/REM20220919")
)
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.example.application.proxies")
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	ActorRepository dao;

	@Autowired
	ActorService srv;
	
	@Override
//	@Transactional
	public void run(String... args) throws Exception {
//		System.out.println("Hola mundo");
//		dao.save(new Actor(0, "Pepito", "Grillo"));
//		var item = dao.findById(202);
//		if(item.isPresent()) {
//			var actor = item.get();
//			actor.setLastName(actor.getLastName().toUpperCase());
//			dao.save(actor);
//		}
//		dao.deleteById(202);
//		dao.findAll().forEach(System.out::println);
//		dao.findTop5ByFirstNameStartingWithOrderByLastNameDesc("P").forEach(System.out::println);
//		dao.findTop5ByFirstNameStartingWith("P", Sort.by("actorId").descending()).forEach(System.out::println);
//		dao.nuevos(200).forEach(System.out::println);
//		dao.nuevosSQL(190).forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.lessThan(root.get("actorId"), 10)).forEach(System.out::println);
//		dao.findAll(Sort.by("lastName")).forEach(System.out::println);
//		dao.findAll(PageRequest.of(1, 10, Sort.by("actorId"))).getContent().forEach(System.out::println);
//		var item = dao.findById(1);
//		if(item.isPresent()) {
//			var actor = item.get();
////			System.out.println(actor.getClass().getName());
//			actor.getFilmActors().forEach(fa -> System.out.println(fa.getFilm().getTitle()));
//		}
		
//		var actor = new Actor(0, "algo", "12345678z");
//		if(actor.isInvalid()) {
//			System.out.println(actor.getErrorsMessage());
//		} else {
//			System.out.println("Es valido");
//		}
//		dao.save(actor);
//		dao.findAll().forEach(item -> System.out.println(ActorDto.from(item)));
//		System.out.println(ActorDto.from(new ActorDto(1, "kk", "AA")));
//		dao.nuevos(200).forEach(item -> System.out.println(item.getNombre()));
//		dao.nuevos(200).forEach(System.out::println);
		
//		dao.findByActorIdIsNotNull(ActorDto.class).forEach(System.out::println);
//		dao.findByActorIdIsNotNull(ActorName.class).forEach(item -> System.out.println(item.getNombre()));
//		srv.getByProjection(ActorName.class).forEach(item -> System.out.println(item.getNombre()));
	}
	@Bean
	public OpenApiCustomiser sortSchemasAlphabetically() {
	    return openApi -> {
	        var schemas = openApi.getComponents().getSchemas();
	        openApi.getComponents().setSchemas(new TreeMap<>(schemas));
	    };
	}

	@Bean 
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
