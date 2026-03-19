package com.pdv.chaveiro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.chaveiro.dto.AuthenticationDTO;
import com.pdv.chaveiro.dto.LoginResponseDTO;
import com.pdv.chaveiro.model.entities.User;
import com.pdv.chaveiro.service.TokenService;

import jakarta.validation.Valid;

/**
 * Controlador REST responsável por gerenciar as requisições de autenticação e login.
 * É a porta de entrada pública para a geração de tokens JWT.
 * @author Lucas Costa
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  /**
   * Gerenciador de autenticação nativo do Spring Security.
   */
  private final AuthenticationManager authenticationManager;

  /**
   * Serviço customizado para geração de tokens JWT.
   */
  private final TokenService tokenService;

  /**
   * Construtor da classe AuthController. (Injeção de Dependência por Construtor)
   * @param authenticationManager Gerenciador injetado a partir das configurações de segurança.
   * @param tokenService Serviço injetado para criar o token de resposta.
   */
  public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  /**
   * Recebe as credenciais do usuário, valida no banco de dados e retorna um Token JWT.
   * @param data Objeto {@link AuthenticationDTO} contendo e-mail e senha validados.
   * @return Um {@link ResponseEntity} contendo o {@link LoginResponseDTO} com o token gerado (Status 200),
   * ou um erro de acesso negado (Status 401/403) caso as credenciais sejam inválidas.
   */
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
    // Cria um token temporário com o e-mail e a senha digitados (Ainda não é o JWT)
    UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
    // Busca o usuário no banco e compara as senhas criptografadas.
    Authentication auth = this.authenticationManager.authenticate(usernamePassword);
    //Se a senha está correta, extrai o usuário validado.
    User user = (User) auth.getPrincipal();
    // Gera o crachá definitivo (Token JWT) para este usuário.
    String token = tokenService.generateToken(user);

    //  Devolve o token na resposta HTTP 200 (OK).
    return ResponseEntity.ok(new LoginResponseDTO(token));
  }
}