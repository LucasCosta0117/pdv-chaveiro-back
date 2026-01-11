package com.pdv.chaveiro.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Representa um Produto (product) vendido pelo estabelicimento comercial.
 * <p>Esta entidade mapeia a tabela de produtos, contendo atributos como preço e categoria,
 * e é uma das bases para o registro de transações no PDV.</p>
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Entity
@Data
@SQLRestriction("is_deleted = false")
public class Product {

  /**
   * Identificador único (Primary Key) da entidade. Gerado automaticamente pelo banco de dados.
   */
  @Id
  @GeneratedValue
  private UUID id;

  /**
   * Nome descritivo do produto
   */
  @Column(length = 100, nullable = false)
  private String name;

  /**
   * Marca do produto para identificação.
   */
  @Column(length = 100)
  private String brand;

  /**
   * Código interno de referência ou SKU do produto.
   */
  @Column(length = 100)
  private String code;

  /**
   * Departamento para divisão macro do produto.
   */
  @Column(length = 100)
  private String department;

  /**
   * Categoria principal do produto.
   */
  @Column(length = 100)
  private String category;

  /**
   * Subcategoria do produto para granularidade.
   */
  @Column(length = 100)
  private String subcategory;

  /**
   * Preço padrão do produto.
   * <p>Restrição: Não pode ser negativo (validação @Min(0)).</p>
   */
  @Column
  @Min(value = 0, message = "Price value cannot be negative")
  private BigDecimal price;

  /**
   * Quantidade do produto disponível para venda.
   */
  @Column
  @Min(value = 0, message = "Stock quantity cannot be negative")
  private Integer stock;

  /**
   * URL do banco de arquivos da imagem do produto.
   */
  @Column(name = "img_url", columnDefinition = "TEXT")
  private String imgUrl;

  /**
   * Indica se o produto está disponível para venda no PDV.
   */
  @Column(name = "can_sale")
  private Boolean canSale;

  /**
   * Indica se o produto está ativo no PDV.
   */
  @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
  private Boolean isActive = true;

  /**
   * Flag de exclusão lógica (soft delete).
   */
  @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private Boolean isDeleted = false;

  /**
   * Carimbo de data/hora de criação do registro. Definido automaticamente no PrePersist.
   */
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  /**
   * Carimbo de data/hora da última atualização do registro. Definido automaticamente no PreUpdate.
   */
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  /**
   * Define a data de criação e atualização antes da persistência no banco de dados.
   */
  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  /**
   * Atualiza o carimbo de data/hora de 'updatedAt' antes de qualquer atualização no banco de dados.
   */
  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}