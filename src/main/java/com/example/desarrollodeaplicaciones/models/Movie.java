package com.example.desarrollodeaplicaciones.models;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.Valid;
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
	private ArrayList<String> image;
	private String trailer;
	private String releasedDate;
	private String duration;
	private String director;
	private ArrayList<Actor> actors;




}
