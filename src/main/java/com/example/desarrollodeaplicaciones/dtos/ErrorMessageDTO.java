package com.example.desarrollodeaplicaciones.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessageDTO {
    private String message;
    private int status;
}
