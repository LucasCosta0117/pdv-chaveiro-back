package com.pdv.chaveiro.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pdv.chaveiro.repository.UserRepository;
import com.pdv.chaveiro.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de segurança que intercepta todas as requisições HTTP da aplicação.
 * Responsável por extrair, validar o Token JWT e autenticar o usuário no contexto do Spring Security.
 * * @author Lucas Costa
 * @version 1.0.0
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

  /**
   * Serviço de geração e validação de tokens JWT.
   */
  private final TokenService tokenService;

  /**
   * Repositório JPA para busca de dados do usuário autenticado.
   */
  private final UserRepository userRepository;

  /**
   * Construtor da classe SecurityFilter. (Injeção de Dependência por Construtor)
   * 
   * @param tokenService Serviço responsável pela manipulação de tokens.
   * @param userRepository Repositório para acesso aos dados da entidade User.
   */
  public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
    this.tokenService = tokenService;
    this.userRepository = userRepository;
  }

  /**
   * Método principal do filtro, executado uma vez a cada requisição HTTP.
   * Ele recupera o token, valida a assinatura e insere os dados do usuário no contexto de segurança.
   * 
   * @param request O objeto que contém os dados da requisição HTTP recebida.
   * @param response O objeto de resposta HTTP.
   * @param filterChain A cadeia de filtros do Spring, permitindo que a requisição siga seu fluxo.
   * @throws ServletException caso ocorra um erro de processamento do Servlet.
   * @throws IOException caso ocorra um erro de I/O na requisição/resposta.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String token = this.recoverToken(request);

    if (token != null) {
      String login = tokenService.validateToken(token);

      if (!login.isEmpty()) {
        UserDetails user = userRepository.findByEmail(login).orElse(null);

        if (user != null) {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            user, null, user.getAuthorities()
          );

          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }

    filterChain.doFilter(request, response);
  }

  /**
   * Extrai o token JWT do cabeçalho "Authorization" da requisição HTTP.
   * <p>O padrão esperado pelo protocolo HTTP é "Bearer {token}".</p>
   * 
   * @param request O objeto da requisição HTTP.
   * @return A string do token sem o prefixo "Bearer ", ou null caso não seja enviado.
   */
  private String recoverToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return null;
    }

    // Remove os primeiros 7 caracteres ("Bearer ") para isolar apenas o Token
    return authHeader.replace("Bearer ", "");
  }
}