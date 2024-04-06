package com.example.desarrollodeaplicaciones.dtos.moviesapi.response;

import com.example.desarrollodeaplicaciones.dtos.moviesapi.PeopleCastApiDTO;
import com.example.desarrollodeaplicaciones.dtos.moviesapi.PeopleCrewApiDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseCreditsApiDTO {
    private List<PeopleCastApiDTO> cast;
    private List<PeopleCrewApiDTO> crew;

}
