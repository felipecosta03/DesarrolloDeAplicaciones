package com.example.desarrollodeaplicaciones.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desarrollodeaplicaciones.services.IImportDataServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@Validated
@RequestMapping("/api/v1/import")

public class ImportController {
	
	private final IImportDataServices importDataServices;
	
	public ImportController(IImportDataServices importDataServices) {
		this.importDataServices = importDataServices;
	}
	
	
	@PostMapping(value= {"/", ""})
	public ResponseEntity<String> importData() throws JsonMappingException, JsonProcessingException {
		String response = importDataServices.importData();
		return ResponseEntity.status(200).body(response);
		
	}
	

}
