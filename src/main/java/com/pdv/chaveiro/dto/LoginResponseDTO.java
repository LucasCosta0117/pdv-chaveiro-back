package com.pdv.chaveiro.dto;

/**
 * Record (DTO) responsável por encapsular a resposta de sucesso do login,
 * devolvendo o Token JWT gerado para o Front-end.
 * @author Lucas Costa
 * @version 1.0.0
 */
public record LoginResponseDTO(
  /**
   * O Token JWT criptografado que será armazenado pelo Vue.js.
   */
  String token
) {}