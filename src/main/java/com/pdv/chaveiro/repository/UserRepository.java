package com.pdv.chaveiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.chaveiro.model.entities.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  /**
   * Método para procurar um Usuário pelo seu email.
   * 
   * @param email Email da entidade User.
   * 
   * @return A entidade {@link User}.
   */
  Optional<User> findByEmail(String email);
}
