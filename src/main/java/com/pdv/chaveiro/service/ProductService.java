package com.pdv.chaveiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pdv.chaveiro.model.Product;
import com.pdv.chaveiro.repository.ProductRepository;

@Service
public class ProductService {

  private final ProductRepository productRepo;

  public ProductService(ProductRepository productRepository) {
    productRepo = productRepository;
  }

  public List<Product> getAll() {
    return productRepo.findAll();
  }
}
