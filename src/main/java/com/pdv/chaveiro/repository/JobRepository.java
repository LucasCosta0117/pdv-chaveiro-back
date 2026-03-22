package com.pdv.chaveiro.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.chaveiro.model.entities.Job;

/**
 * Repositório de dados para a entidade {@link Job}.
 * <p>Esta interface estende {@link JpaRepository}, fornecendo métodos CRUD (Create, Read, Update, Delete)
 * e funcionalidades de paginação/ordenção prontos para uso além daqueles implementados manualmente.</p>
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Repository
public interface JobRepository extends JpaRepository <Job, UUID>{
  /**
   * Retorna uma lista contendo todos os serviços ativos para a empresa informada.
   * @param companyId Identificador da empresa.
   * @return Uma lista {@link List} de objetos {@link Job}.
   */
  List<Job> findAllByCompanyIdAndIsDeletedFalse(UUID companyId);

  /**
   * Retorna um {@link Job} serviço ativo e que pertence a empresa informada.
   * @param id Identificador do serviço.
   * @param companyId Identificado da empresa.
   * @return Uma lista {@link List} de objetos {@link Job}.
   */
  Optional<Job> findByIdAndCompanyIdAndIsDeletedFalse(UUID id, UUID companyId);
}
