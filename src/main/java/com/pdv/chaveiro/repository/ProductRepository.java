package com.pdv.chaveiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.chaveiro.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
  // Utilizando métodos nativos JpaRepository. Nenhum método adicional/customizado por enquanto
}
