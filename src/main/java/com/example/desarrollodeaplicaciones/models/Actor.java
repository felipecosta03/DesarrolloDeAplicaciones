package com.example.desarrollodeaplicaciones.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document
public class Actor {
	private String firstName;
	private String lastName;
	@Override
	public String toString() {
		return (this.firstName   +  ' ' + this.lastName);
	}
	public Actor(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
