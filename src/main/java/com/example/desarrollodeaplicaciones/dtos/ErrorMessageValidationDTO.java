package com.example.desarrollodeaplicaciones.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessageValidationDTO {
	private int status;
	private ErrorCode code;
	List<String> messages;
}
		

