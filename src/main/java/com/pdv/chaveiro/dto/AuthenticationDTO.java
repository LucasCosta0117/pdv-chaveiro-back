package com.pdv.chaveiro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Record (DTO) responsável por encapsular os dados de login enviados pelo Front-end.
 * Utiliza validações do Jakarta Bean Validation para garantir a integridade dos dados.
 * @author Lucas Costa
 * @version 1.0.0
 */
public record AuthenticationDTO(
  /**
   * E-mail do usuário. Não pode ser vazio e deve ter um formato válido.
   */
  @NotBlank(message = "O e-mail é obrigatório.")
  @Email(message = "Formato de e-mail inválido.")
  String email,
  
  /**
   * Senha de acesso do usuário. Não pode ser vazia.
   */
  @NotBlank(message = "A senha é obrigatória.")
  String password
) {}