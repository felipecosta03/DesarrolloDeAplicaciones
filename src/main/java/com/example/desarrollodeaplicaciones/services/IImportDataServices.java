package com.example.desarrollodeaplicaciones.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IImportDataServices {
	String importData() throws JsonMappingException, JsonProcessingException;
}
