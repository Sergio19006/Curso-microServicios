package com.example.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDto;
import com.example.domains.entities.dtos.ActorName;

@RepositoryRestResource(exported = false)
public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> {
	List<Actor> findTop5ByFirstNameStartingWithOrderByLastNameDesc(String prefijo);
	List<Actor> findTop5ByFirstNameStartingWith(String prefijo, Sort orden);

	@Query("SELECT a FROM Actor a where actorId > ?1")
	List<ActorName> nuevos(int id);
	
	@Query(value =  "select * from actor where actor_id > ?1", nativeQuery = true)
	List<Actor> nuevosSQL(int id);

	<T> List<T> findByActorIdIsNotNull(Class<T> proyeccion);
	<T> Iterable<T> findByActorIdIsNotNull(Sort sort, Class<T> proyeccion);
	<T> Page<T> findByActorIdIsNotNull(Pageable pageable, Class<T> proyeccion);
}
