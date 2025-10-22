package com.pdv.chaveiro.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.chaveiro.model.Product;
import com.pdv.chaveiro.service.ProductService;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador REST responsável por expor os endpoints da API para a gestão e consulta da entidade Product (Produtos).
 * <p>O path base da API é {@value #"/api/product"} e o CORS está configurado para aceitar requisições de qualquer origem.</p>
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductController {

  /**
   * Classe Service responsável pela lógica na camada de negócio.
   */
  private final ProductService productServ;

  /**
   * Construtor da classe ProductController. (Injeção de Dependência do Service por Construtor)
   * 
   * @param productService O serviço da camada de negócio, injetado pelo Spring.
   */
  public ProductController(ProductService productService){
    productServ = productService;
  }

  /**
   * Endpoint GET para recuperar todos os produtos disponíveis (Products).
   * <p>Mapeado para: /api/product/all</p>
   * 
   * @return Uma lista {@link List} de objetos {@link Product}.
   */
  @GetMapping("/all") 
  public List<Product> getAllProducts() {
    return productServ.getAll();
  }
}