package com.pdv.chaveiro.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.chaveiro.model.entities.Product;

/**
 * Repositório de dados para a entidade {@link Product}.
 * <p>Esta interface estende {@link ProductRepository}, fornecendo métodos CRUD (Create, Read, Update, Delete)
 * e funcionalidades de paginação/ordenção prontos para uso além daqueles implementados manualmente.</p>
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{
  /**
   * Retorna uma lista contendo todos os produtos ativos para a empresa informada.
   * @param companyId Identificador da empresa.
   * @return Uma lista {@link List} de objetos {@link Product}.
   */
  List<Product> findAllByCompanyIdAndIsDeletedFalse(UUID companyId);

  /**
   * Retorna um {@link Product} produtos ativo e que pertence a empresa informada.
   * @param id Identificador do produtos.
   * @param companyId Identificado da empresa.
   * @return Uma lista {@link List} de objetos {@link Product}.
   */
  Optional<Product> findByIdAndCompanyIdAndIsDeletedFalse(UUID id, UUID companyId);
}
