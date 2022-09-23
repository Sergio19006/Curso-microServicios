package com.example.infraextructure.repositories;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorName;

//@Repository
public class ActorMockRopsitoryImpl implements ActorRepository {

	@Override
	public List<Actor> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Actor> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Actor> findAllById(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Actor> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Actor> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Actor> List<S> saveAllAndFlush(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<Actor> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Actor getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Actor getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Actor getReferenceById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Actor> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Actor> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Actor> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Actor> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Actor> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Actor entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Actor> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Actor> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public <S extends Actor> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Actor> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Actor> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends Actor, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Actor> findOne(Specification<Actor> spec) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Actor> findAll(Specification<Actor> spec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Actor> findAll(Specification<Actor> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Actor> findAll(Specification<Actor> spec, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count(Specification<Actor> spec) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean exists(Specification<Actor> spec) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Actor> findTop5ByFirstNameStartingWithOrderByLastNameDesc(String prefijo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Actor> findTop5ByFirstNameStartingWith(String prefijo, Sort orden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActorName> nuevos(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Actor> nuevosSQL(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findByActorIdIsNotNull(Class<T> proyeccion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Iterable<T> findByActorIdIsNotNull(Sort sort, Class<T> proyeccion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Page<T> findByActorIdIsNotNull(Pageable pageable, Class<T> proyeccion) {
		// TODO Auto-generated method stub
		return null;
	}

}
