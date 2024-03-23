package com.example.desarrollodeaplicaciones.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document
public class Movie {
	private String title;
	private String subtitle;
	private String synapsis;
	private String genre; //Esto tiene que venir de un enum
	@Builder.Default
	private List<String> image = new ArrayList<>();
	private String trailer;
	private String releasedDate;
	private String duration;
	private String director;
	@Builder.Default
	private List<Actor> actors = new ArrayList<>();




}
