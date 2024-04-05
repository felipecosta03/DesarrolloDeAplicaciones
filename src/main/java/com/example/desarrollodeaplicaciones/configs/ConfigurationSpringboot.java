package com.example.desarrollodeaplicaciones.configs;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationSpringboot {

    @Bean
    public Dotenv getDotenv(){
        return Dotenv.load();
    }
}
