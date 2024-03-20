package com.example.desarrollodeaplicaciones.configs;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(
            auth -> {
              auth.requestMatchers("/", "/error", "/auth/**", "/oauth/**").permitAll();
              auth.anyRequest().authenticated();
            })
        .oauth2Login(o->o.defaultSuccessUrl("/loginSuccess"))
        .build();
  }
}
