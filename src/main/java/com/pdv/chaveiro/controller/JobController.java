package com.pdv.chaveiro.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.chaveiro.model.Job;
import com.pdv.chaveiro.service.JobService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/job")
@CrossOrigin(origins = "*" )
public class JobController {
  
  private final JobService jobServ;

  public JobController(JobService jobService){
    jobServ = jobService;
  }

  @GetMapping("/all")
  public List<Job> findAll() {
    return jobServ.getAll();
  }
}
