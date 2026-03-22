package com.pdv.chaveiro.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pdv.chaveiro.dto.JobRequestDTO;
import com.pdv.chaveiro.model.entities.Company;
import com.pdv.chaveiro.model.entities.Job;
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
   * Retorna uma lista contendo todos os serviços (${@link Job}) ativos da empresa do usuário autenticado.
   * @param company A empresa do usuário autenticado.
   * @return Uma lista {@link List} de objetos {@link Job}.
   */
  public List<Job> getAllByCompany(Company company) {
    return jobRepo.findAllByCompanyIdAndIsDeletedFalse(company.getId());
  }

  /**
   * Retorna um objeto do tipo {@link Job} com base no ID fornecido, validando a empresa e a permissão do usuário.
   * @param jobId   ID para identificação e busca do serviço.
   * @param company A empresa do utilizador autenticado.
   * @return Um objeto {@link Job}.
   * @throws EntityNotFoundException caso o serviço não seja encontrado ou pertença a outra empresa.
   */
  public Job getByIdAndCompany(UUID jobId, Company company) {
    return jobRepo.findByIdAndCompanyIdAndIsDeletedFalse(jobId, company.getId())
      .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado ou acesso negado."));
  }

  /**
   * Persiste um novo serviço no banco de dados, vinculando-o à empresa atual.
   * @param dto Objeto contendo os dados do novo serviço.
   * @param company A empresa do utilizador que está a criar o registo.
   * @return A entidade {@link Job} guardada.
   */
  @Transactional
  public Job saveJob(JobRequestDTO dto, Company company) {
    Job job = new Job();
    job.setName(dto.getName());
    job.setCode(dto.getCode());
    job.setCategory(dto.getCategory());
    job.setSubcategory(dto.getSubcategory());
    job.setPrice(dto.getPrice());
    job.setIsActive(dto.getIsActive());
    job.setCompany(company);
    job.setIsDeleted(false);

    return jobRepo.save(job);
  }

  /**
   * Atualiza os dados de um serviço existente.
   * @param id Identificador do serviço.
   * @param dto Dados atualizados.
   * @param company A empresa do utilizador.
   * @return A entidade {@link Job} atualizada.
   */
  @Transactional
  public Job updateJob(UUID id, JobRequestDTO dto, Company company) {
    Job job = this.getByIdAndCompany(id, company);

    job.setName(dto.getName());
    job.setCode(dto.getCode());
    job.setCategory(dto.getCategory());
    job.setSubcategory(dto.getSubcategory());
    job.setPrice(dto.getPrice());
    job.setIsActive(dto.getIsActive());

    return jobRepo.save(job);
  }

  /**
   * Realiza um soft delete do objeto na base de dados (UPDATE na flag 'isDeleted').
   * @param id ID do item a ser excluído.
   * @param company A empresa do utilizador autenticado.
   */
  @Transactional
  public void softDelete(UUID id, Company company) {
    Job job = this.getByIdAndCompany(id, company);
    job.setIsDeleted(true);
    jobRepo.save(job);
  }
}