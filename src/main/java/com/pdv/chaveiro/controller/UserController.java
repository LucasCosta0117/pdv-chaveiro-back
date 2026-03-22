package com.pdv.chaveiro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.chaveiro.dto.UserProfileDTO;
import com.pdv.chaveiro.model.entities.User;
import com.pdv.chaveiro.service.UserService;

/**
 * Controlador REST responsável por gerenciar as operações relacionadas aos usuários do sistema.
 * <p>O path base da API é {@value #"/api/users"} e o CORS está configurado apenas para requisições com autenticação prévia via Token JWT.</p>
 * @author Lucas Costa
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
  /**
   * Serviço contendo as regras de negócio de usuários.
   */
  private final UserService userService;

  /**
   * Construtor da classe UserController. (Injeção de Dependência por Construtor)
   * * @param userService Serviço injetado pelo Spring.
   */
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Retorna os dados de perfil do usuário atualmente autenticado.
   * @param loggedUser Entidade {@link User} autenticada injetada pelo Spring Security.
   * @return Um {@link ResponseEntity} contendo o {@link UserProfileDTO} com os dados do usuário (Status 200).
   */
  @GetMapping("/me")
  public ResponseEntity<UserProfileDTO> getCurrentUserProfile(@AuthenticationPrincipal User loggedUser) {
    UserProfileDTO profileDTO = userService.getProfileFromUser(loggedUser);

    return ResponseEntity.ok(profileDTO);
  }
}