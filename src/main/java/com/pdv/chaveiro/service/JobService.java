package com.pdv.chaveiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pdv.chaveiro.model.Job;
import com.pdv.chaveiro.repository.JobRepository;

/**
 * Serviço responsável pela regra de negócio e gestão da entidade Job (Serviços).
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Service
public class JobService {
  
  /**
   * Repositório JPA para acesso e manipulação de dados da entidade Job.
   */
  private final JobRepository jobRepo;

  /**
   * Construtor da classe JobService. (Injeção de Dependência por Construtor)
   * 
   * @param jobRepository Repositório de dados de Job, injetado pelo Spring.
   */
  public JobService(JobRepository jobRepository) {
    jobRepo = jobRepository;
  }

  /**
   * Retorna uma lista contendo todos os serviços (Jobs) cadastrados no sistema.
   * 
   * @return Uma lista {@link List} de objetos {@link Job}.
   */
  public List<Job> getAll() {
    return jobRepo.findAll();
  }
}