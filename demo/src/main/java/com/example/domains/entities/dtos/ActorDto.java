package com.example.domains.entities.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.domains.entities.Actor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ActorDto {
	@NotNull
	private int actorId;
	@NotBlank
	@Size(max = 45, min=2)
	private String firstName;
	@NotBlank
	@Size(max = 45, min=2)
	private String lastName;

	public static ActorDto from(Actor target) {
		return new ActorDto(
				target.getActorId(),
				target.getFirstName(),
				target.getLastName()
				);
	}

	public static Actor from(ActorDto target) {
		return new Actor(
				target.getActorId(),
				target.getFirstName(),
				target.getLastName());
	}
}
