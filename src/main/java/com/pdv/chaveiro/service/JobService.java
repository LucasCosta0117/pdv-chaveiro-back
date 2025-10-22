package com.pdv.chaveiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pdv.chaveiro.model.Job;
import com.pdv.chaveiro.repository.JobRepository;

@Service
public class JobService {
  
  private final JobRepository jobRepo;

  public JobService(JobRepository jobRepository) {
    jobRepo = jobRepository;
  }

  public List<Job> getAll() {
    return jobRepo.findAll();
  }
}