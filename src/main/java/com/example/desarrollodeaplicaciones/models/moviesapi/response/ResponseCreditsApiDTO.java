package com.example.desarrollodeaplicaciones.models.moviesapi.response;

import com.example.desarrollodeaplicaciones.models.moviesapi.PeopleCast;
import com.example.desarrollodeaplicaciones.models.moviesapi.PeopleCrew;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseCreditsApiDTO {
    private List<PeopleCast> cast;
    private List<PeopleCrew> crew;

}
