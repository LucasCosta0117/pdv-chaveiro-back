package com.pdv.chaveiro.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pdv.chaveiro.dto.ProductRequestDTO;
import com.pdv.chaveiro.model.Product;
import com.pdv.chaveiro.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço responsável pela regra de negócio e gestão da entidade Product (Produtos).
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Service
@Slf4j
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

  /**
   * Atualiza o valor do estoque de um produto com base na quantidade informada na requisição.
   * <p>O valor a ser deduzido no estoque precisa ser inferior ou igual ao disponível</p>
   * 
   * @param productId         ID para identificação do produto.
   * @param requestProductQty Quantidade a ser deduzida do estoque do produto.
   * @return A entidade {@link Product} salva, com o valor de 'estoque' atualizado.
   */
  public Product updateProductStock(UUID productId, int requestProductQty) {
    Product product = productRepo.findById(productId).orElseThrow(() -> {
      log.error("Produto não encontrado com ID: {}", productId);
      
      return new RuntimeException("Produto não encontrado com ID: " + productId);
    });

    if (product.getStock() < requestProductQty) {
      log.warn("Estoque insuficiente para '{}' - disponível: {} un, solicitado: {} un",
        product.getName(), product.getStock(), requestProductQty);

      throw new RuntimeException(
        "Estoque insuficiente para '"+ product.getName() +"' - disponível: "+ product.getStock() +" un, solicitado: "+ requestProductQty +" un"
      );
    }

    int newStock = product.getStock() - requestProductQty;
    product.setStock(newStock);

    return productRepo.save(product);
  }

  /**
   * Retorna um objeto do tipo Product com base no ID fornecido.
   * 
   * @param productId   ID para identificação e busca do produto.
   * @return Um objeto {@link Product}.
   * @throws EntityNotFoundException caso o produto não seja encontrado.
   */
  public Product getById(UUID productId) {
    return productRepo.findById(productId).orElseThrow(
      ()-> new RuntimeException("Produto não encontrado com o ID: " + productId)
    );
  }

  /**
   * Realiza um soft delete do objeto no banco, de modo que é feito apenas um UPDATE na flag 'deleted' da entidade.
   * <p>O método é transacional: se qualquer operação falhar, toda a transação é revertida.</p>
   * 
   * @param id ID do item à ser excluido.
   */
  @Transactional
  public void softDelete(UUID id) {
    Product product = productRepo.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

    product.setIsDeleted(true);

    productRepo.save(product);
  }

  /**
   * Persiste um novo produto no banco de dados.
   * <p>O método é transacional: se qualquer operação falhar, toda a transação é revertida.</p>
   * 
   * @param dto Objeto contendo os dados do novo produto vindo do front-end.
   * @return A entidade {@link Product} salvo.
   */
  @Transactional
  public Product saveProduct(ProductRequestDTO dto) {
    Product product = new Product();

    product.setName(dto.getName());
    product.setBrand(dto.getBrand());
    product.setCode(dto.getCode());
    product.setDepartment(dto.getDepartment());
    product.setCategory(dto.getCategory());
    product.setSubcategory(dto.getSubcategory());
    product.setPrice(dto.getPrice());
    product.setStock(dto.getStock());
    product.setImgUrl(dto.getImgUrl());
    product.setCanSale(dto.getCanSale());
    product.setIsActive(dto.getIsActive());

    return productRepo.save(product);
  }

  /**
   * Atualizar os dados de um produto existente.
   * 
   * @param id Identificador do produto.
   * @param dto Dados atualizados.
   * @return A entidade {@link Product} atualizada.
   */
  @Transactional
  public Product updateProduct(UUID id, ProductRequestDTO dto) {
    Product product = productRepo.findById(id)
      .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));

    product.setName(dto.getName());
    product.setBrand(dto.getBrand());
    product.setCode(dto.getCode());
    product.setDepartment(dto.getDepartment());
    product.setCategory(dto.getCategory());
    product.setSubcategory(dto.getSubcategory());
    product.setPrice(dto.getPrice());
    product.setStock(dto.getStock());
    product.setImgUrl(dto.getImgUrl());
    product.setCanSale(dto.getCanSale());
    product.setIsActive(dto.getIsActive());

    return productRepo.save(product);
  }
}