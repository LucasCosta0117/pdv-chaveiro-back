package com.pdv.chaveiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.chaveiro.model.Job;

@Repository
public interface JobRepository extends JpaRepository <Job, Long>{
  // Utilizando métodos nativos JpaRepository. Nenhum método adicional/customizado por enquanto
}
