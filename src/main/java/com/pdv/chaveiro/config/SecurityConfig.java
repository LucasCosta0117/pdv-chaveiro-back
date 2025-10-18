package com.pdv.chaveiro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Opcional, mas boa prática
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Adicionar esta anotação é opcional mas é a prática recomendada
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    
    http
      // 1. Desativa a proteção CSRF (necessário para APIs Stateless)
      .csrf(csrf -> csrf.disable()) 
      
      // 2. Configura as autorizações para requisições HTTP
      .authorizeHttpRequests(auth -> auth
          // Permite todas as requisições para qualquer URL
          .anyRequest().permitAll()
      );

    return http.build();
  }
}