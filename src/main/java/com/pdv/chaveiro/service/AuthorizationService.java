package com.pdv.chaveiro.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pdv.chaveiro.repository.UserRepository;

/**
 * Serviço responsável por integrar a base de dados com o mecanismo de autenticação do Spring Security.
 * Ele implementa a interface UserDetailsService exigida pelo framework.
 * * @author Lucas Costa
 * @version 1.0.0
 */
@Service
public class AuthorizationService implements UserDetailsService {

  /**
   * Repositório JPA para acesso e manipulação de dados da entidade User.
   */
  private final UserRepository userRepository;

  /**
   * Construtor da classe AuthorizationService. (Injeção de Dependência por Construtor)
   * * @param userRepository Repositório de dados de User, injetado pelo Spring.
   */
  public AuthorizationService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Carrega os dados do usuário com base no e-mail (username) fornecido durante o login.
   * <p>O Spring Security chama este método automaticamente para validar as credenciais.</p>
   * * @param username E-mail do usuário utilizado para autenticação.
   * @return Um objeto que implementa a interface {@link UserDetails}.
   * @throws UsernameNotFoundException caso o usuário não seja encontrado na base de dados.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username).orElseThrow(
      () -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + username)
    );
  }
  
}