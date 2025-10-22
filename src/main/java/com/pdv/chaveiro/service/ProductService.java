package com.pdv.chaveiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pdv.chaveiro.model.Product;
import com.pdv.chaveiro.repository.ProductRepository;

/**
 * Serviço responsável pela regra de negócio e gestão da entidade Product (Produtos).
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Service
public class ProductService {

  /**
   * Repositório JPA para acesso e manipulação de dados da entidade Product.
   */
  private final ProductRepository productRepo;

  /**
   * Construtor da classe ProductService. (Injeção de Dependência por Construtor)
   * 
   * @param jobRepository Repositório de dados de Product, injetado pelo Spring.
   */
  public ProductService(ProductRepository productRepository) {
    productRepo = productRepository;
  }

  /**
   * Retorna uma lista contendo todos os produtos (Products) cadastrados no sistema.
   * 
   * @return Uma lista {@link List} de objetos {@link Product}.
   */
  public List<Product> getAll() {
    return productRepo.findAll();
  }
}
