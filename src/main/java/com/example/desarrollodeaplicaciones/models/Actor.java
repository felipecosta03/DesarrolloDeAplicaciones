package com.example.desarrollodeaplicaciones.models;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;

@Data
@Valid
@Builder
@Document
public class Actor {
	private String firstName;
	private String lastName;
	@Override
	public String toString() {
		return (this.firstName   +  ' ' + this.lastName);
	}
}
