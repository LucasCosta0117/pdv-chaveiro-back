package com.pdv.chaveiro.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pdv.chaveiro.model.entities.User;

/**
 * Serviço responsável pela geração e validação de Tokens JWT (JSON Web Tokens).
 * Utiliza a biblioteca auth0 para criptografia e verificação de autenticidade.
 * * @author Lucas Costa
 * @version 1.0.0
 */
@Service
public class TokenService {

  /**
   * Chave secreta utilizada para assinar o Token JWT.
   * O valor é injetado a partir do arquivo application.properties.
   */
  @Value("${api.security.token.secret}")
  private String secret;

  /**
   * Gera um novo Token JWT para o usuário autenticado.
   * <p>O token inclui o e-mail do usuário como assunto (Subject), a role e a empresa associada 
   * em claims adicionais, além de definir um prazo de validade.</p>
   * * @param user O objeto {@link User} contendo os dados do usuário recém-autenticado.
   * @return Uma String representando o Token JWT gerado.
   * @throws RuntimeException caso ocorra um erro durante a criação ou assinatura do token.
   */
  public String generateToken(User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer("pdv-chaveiro-api")
          .withSubject(user.getEmail())
          .withClaim("role", user.getRole().name())
          .withClaim("companyId", user.getCompany().getId().toString())
          .withExpiresAt(genExpirationDate())
          .sign(algorithm);
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Erro ao gerar o token JWT", exception);
    }
  }

  /**
   * Valida a assinatura de um Token JWT e extrai o e-mail do usuário (Subject).
   * <p>Se o token for inválido, expirado ou forjado, o método retorna uma string vazia.</p>
   * * @param token O Token JWT enviado pelo Front-end no cabeçalho da requisição.
   * @return O e-mail do usuário contido no token, ou uma string vazia caso a validação falhe.
   */
  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer("pdv-chaveiro-api")
          .build()
          .verify(token)
          .getSubject();
    } catch (JWTVerificationException exception) {
      return "";
    }
  }

  /**
   * Calcula a data e hora de expiração do token.
   * <p>A validade padrão configurada é de 2 horas a partir do momento da geração.</p>
   * * @return Um objeto {@link Instant} com o exato momento de expiração no fuso horário local.
   */
  private Instant genExpirationDate() {
    // Retorna o horário atual + 12 horas no fuso horário do Brasil (UTC-3) - 
    // @todo Implementar Access Token + Refresh Token, garantindo segurança. 
    return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-03:00"));
  }
}