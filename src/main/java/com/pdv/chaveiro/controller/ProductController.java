package com.pdv.chaveiro.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.chaveiro.dto.ProductRequestDTO;
import com.pdv.chaveiro.model.Product;
import com.pdv.chaveiro.service.ProductService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

  /**
   * Endpoint DELETE para excluir um produto do sistema.
   * <p>Mapeado para: /api/product/delete/{id}</p>
   * 
   * @param id ID do item à ser excluido.
   * @return Retorna apenas status de sucesso 204 No Content.
   */
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    productServ.softDelete(id);
    
    return ResponseEntity.noContent().build();
  }

  /**
   * Endpoint POST para salvar um novo produto.
   * <p>Mapeado para: /api/product/save</p>
   * 
   * @param newProduct Objeto JSON contendo os dados do novo produto.
   * @return O produto registrado no banco.
   */
  @PostMapping("/save")
  public ResponseEntity<Product> save(@RequestBody ProductRequestDTO newProduct) {
    Product productSaved = productServ.saveProduct(newProduct);

    return ResponseEntity.ok(productSaved);
  }

  /**
   * Endpoint PUT para atualizar um produto existente.
   * <p>Mapeado para: /api/product/update/{id}</p>
   * 
   * @param id Identificador único do produto a ser editado.
   * @param editedProduct Objeto JSON contendo os novos dados do produto.
   * @return O produto atualizado no banco.
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<Product> update(@PathVariable UUID id, @RequestBody ProductRequestDTO editedProduct) {
    Product productUpdated = productServ.updateProduct(id, editedProduct);
    
    return ResponseEntity.ok(productUpdated);
  }
}