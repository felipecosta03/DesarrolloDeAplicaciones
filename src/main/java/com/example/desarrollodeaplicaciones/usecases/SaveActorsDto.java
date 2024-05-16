package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import java.util.List;
import java.util.function.Consumer;

public interface SaveActorsDto extends Consumer<List<ActorDto>> {}
