package com.pdv.chaveiro.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pdv.chaveiro.dto.JobRequestDTO;
import com.pdv.chaveiro.model.Job;
import com.pdv.chaveiro.model.Product;
import com.pdv.chaveiro.repository.JobRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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

  /**
   * Retorna um objeto do tipo Job com base no ID fornecido.
   * 
   * @param jobId   ID para identificação e busca do serviço.
   * @return Um objeto {@link Product}.
   * @throws EntityNotFoundException caso o produto não seja encontrado.
   */
  public Job getById(UUID jobId) {
    return jobRepo.findById(jobId).orElseThrow(
      () -> new RuntimeException("Serviço não encontrado com o ID: " + jobId)
    );
  }

  /**
   * Realiza um soft delete do objeto no banco, de modo que é feito apenas um UPDATE na flag 'deleted' da entidade.
   * <p>O método é transacional: se qualquer operação falhar, toda a transação é revertida.</p>
   * 
   * @param id ID do item à ser excluido.
   */
  @Transactional
  public void softDelete(UUID id) {
    Job job = jobRepo.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));

    job.setIsDeleted(true);

    jobRepo.save(job);
  }

  /**
   * Persiste um novo serviço no banco de dados.
   * <p>O método é transacional: se qualquer operação falhar, toda a transação é revertida.</p>
   * 
   * @param dto - Objeto contendo os dados do novo serviço à ser salvo vindo do Fron-end.
   * @return A entidade {@link Job} salva.
   */
  @Transactional
  public Job saveJob(JobRequestDTO dto) {
    Job job = new Job();
    job.setName(dto.getName());
    job.setCode(dto.getCode());
    job.setCategory(dto.getCategory());
    job.setSubcategory(dto.getSubcategory());
    job.setPrice(dto.getPrice());
    job.setIsActive(dto.getIsActive());

    return jobRepo.save(job);
  }
}