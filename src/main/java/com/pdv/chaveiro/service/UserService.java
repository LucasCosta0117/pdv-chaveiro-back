package com.pdv.chaveiro.service;

import org.springframework.stereotype.Service;

import com.pdv.chaveiro.dto.UserProfileDTO;
import com.pdv.chaveiro.model.entities.User;

/**
 * Serviço responsável por encapsular as regras de negócio relacionadas aos usuários.
 *
 * @author Lucas Costa
 * @version 1.0.0
 */
@Service
public class UserService {
  /**
   * Converte a entidade do usuário autenticado em um DTO de perfil seguro para o Front-end.
   * @param loggedUser A entidade {@link User} previamente carregada pelo contexto de segurança.
   * @return Um objeto {@link UserProfileDTO} contendo apenas os dados públicos necessários.
   */
  public UserProfileDTO getProfileFromUser(User loggedUser) {
    return new UserProfileDTO(
        loggedUser.getId(),
        loggedUser.getName(),
        loggedUser.getEmail(),
        loggedUser.getRole(),
        loggedUser.getCompany().getId()
    );
  }
}