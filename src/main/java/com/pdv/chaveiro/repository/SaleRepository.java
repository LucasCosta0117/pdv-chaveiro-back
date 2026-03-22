package com.pdv.chaveiro.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.chaveiro.model.entities.Sale;

/**
 * Repositório de dados para a entidade {@link Sale}.
 * <p>Esta interface estende {@link JpaRepository}, fornecendo métodos CRUD (Create, Read, Update, Delete)
 * e funcionalidades de paginação/ordenção prontos para uso além daqueles implementados manualmente.</p>
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID>{
  /**
   * Retorna uma lista contendo todos as vendas ativas para a empresa informada.
   * @param companyId Identificador da empresa.
   * @return Uma lista {@link List} de objetos {@link Sale}.
   */
  List<Sale> findAllByCompanyIdAndIsDeletedFalse(UUID companyId);

  /**
   * Retorna uma {@link Sale} venda ativo e que pertence a empresa informada.
   * @param id Identificador da venda.
   * @param companyId Identificado da empresa.
   * @return Uma lista {@link List} de objetos {@link Sale}.
   */
  Optional<Sale> findByIdAndCompanyIdAndIsDeletedFalse(UUID id, UUID companyId);
}

