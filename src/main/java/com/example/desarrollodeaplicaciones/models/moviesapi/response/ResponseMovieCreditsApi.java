package com.example.desarrollodeaplicaciones.models.moviesapi.response;

import com.example.desarrollodeaplicaciones.dtos.PeopleCastDto;
import com.example.desarrollodeaplicaciones.dtos.PeopleCrewDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseMovieCreditsApi {
  private List<PeopleCastDto> cast;
  private List<PeopleCrewDto> crew;
}
