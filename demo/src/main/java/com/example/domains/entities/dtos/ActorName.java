package com.example.domains.entities.dtos;

import org.springframework.beans.factory.annotation.Value;

public interface ActorName {
	@Value("#{target.lastName + ', ' + target.firstName}")
	String getNombre();
}
