package com.pdv.chaveiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.chaveiro.model.entities.Company;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório de dados para a entidade {@link Company}.
 * <p>Esta interface estende {@link JpaRepository}, fornecendo métodos CRUD (Create, Read, Update, Delete)
 * e funcionalidades de paginação/ordenção prontos para uso além daqueles implementados manualmente.</p>
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Repository
public interface CompanyRepository extends JpaRepository<com.pdv.chaveiro.model.entities.Company, UUID> {
  // Utilizando métodos nativos JpaRepository. Nenhum método adicional/customizado por enquanto
}
