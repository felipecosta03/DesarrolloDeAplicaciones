package com.example.desarrollodeaplicaciones.dtos;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Valid
@Builder
public class ActorDTO {
	private String firstName;
	private String lastName;
	@Override
	public String toString() {
		return (this.firstName   +  ' ' + this.lastName);
	}
}
