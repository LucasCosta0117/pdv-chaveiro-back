package com.pdv.chaveiro.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.chaveiro.dto.JobRequestDTO;
import com.pdv.chaveiro.model.Job;
import com.pdv.chaveiro.service.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

  /**
   * Endpoint DELETE para excluir um serviço do sistema.
   * <p>Mapeado para: /api/job/delete/{id}</p>
   * 
   * @param id ID do item à ser excluido.
   * @return Retorna apenas status de sucesso 204 No Content.
   */
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    jobServ.softDelete(id);
    
    return ResponseEntity.noContent().build();
  }

  /**
   * Endpoint POST para salvar um novo serviço.
   * <p>Mapeado para: /api/job/save</p>
   * 
   * @param newJob Objeto JSON contendo os dados do novo serviço.
   * @return O serviço registrado no banco.
   */
  @PostMapping("/save")
  public ResponseEntity<Job> save(@RequestBody JobRequestDTO newJob) {
    Job jobSaved = jobServ.saveJob(newJob);
    
    return ResponseEntity.ok(jobSaved);
  }
}
