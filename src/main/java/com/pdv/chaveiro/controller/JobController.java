package com.pdv.chaveiro.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.chaveiro.model.Job;
import com.pdv.chaveiro.service.JobService;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador REST responsável por expor os endpoints da API para a gestão e consulta da entidade Job (Serviços).
 * <p>O path base da API é {@value #"/api/job"} e o CORS está configurado para aceitar requisições de qualquer origem.</p>
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/job")
@CrossOrigin(origins = "*" )
public class JobController {
  
  /**
   * Classe Service responsável pela lógica na camada de negócio.
   */
  private final JobService jobServ;

  /**
   * Construtor da classe JobController. (Injeção de Dependência do Service por Construtor)
   * 
   * @param jobService O serviço da camada de negócio, injetado pelo Spring.
   */
  public JobController(JobService jobService){
    jobServ = jobService;
  }

  /**
   * Endpoint GET para recuperar todos os serviços disponíveis (Jobs).
   * <p>Mapeado para: /api/job/all</p>
   * 
   * @return Uma lista {@link List} de objetos {@link Job}.
   */
  @GetMapping("/all")
  public List<Job> findAll() {
    return jobServ.getAll();
  }
}
