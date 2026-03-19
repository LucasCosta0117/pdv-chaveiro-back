package com.pdv.chaveiro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe de configuração principal do Spring Security.
 * Define as regras de acesso às rotas da API, política de sessão e injeta o filtro JWT.
 * @author Lucas Costa
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

  /**
   * Filtro customizado para validação de Tokens JWT.
   */
  private final SecurityFilter securityFilter;

  /**
   * Construtor da classe SecurityConfigurations. (Injeção de Dependência por Construtor)
   * 
   * @param securityFilter Filtro de segurança criado para interceptar requisições.
   */
  public SecurityConfigurations(SecurityFilter securityFilter) {
    this.securityFilter = securityFilter;
  }

  /**
   * Configura a corrente de filtros de segurança (SecurityFilterChain).
   * <p>Desabilita proteções desnecessárias para APIs REST (como CSRF), define a sessão como Stateless
   * e mapeia quais rotas são públicas e quais exigem autenticação.</p>
   * 
   * @param http Objeto HttpSecurity fornecido pelo Spring para configuração.
   * @return A corrente de filtros configurada.
   * @throws Exception caso ocorra um erro durante a configuração.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(csrf -> csrf.disable())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
        .anyRequest().authenticated()
      )
      .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
      .build();
  }

  /**
   * Expõe o gerenciador de autenticação do Spring para que possamos usá-lo no nosso AuthController.
   * 
   * @param authenticationConfiguration Configuração de autenticação do Spring.
   * @return O objeto {@link AuthenticationManager} pronto para uso.
   * @throws Exception caso não seja possível obter o gerenciador.
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  /**
   * Define o algoritmo de hash (criptografia) que será usado para as senhas no banco de dados.
   * 
   * @return Um objeto {@link PasswordEncoder} configurado com o algoritmo BCrypt.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}