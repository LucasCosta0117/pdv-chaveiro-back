package com.pdv.chaveiro.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidade para registro dos itens associados a uma venda (Produtos/Serviços).
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Entity
@Data
@Table(name = "sale_items")
public class SaleItem {
  /**
   * Identificador único (Primary Key) da entidade. Gerado automaticamente pelo banco de dados.
   */
  @Id
  @GeneratedValue
  private UUID id;

  /**
   * Chave estrangeira (FOREIGN_KEY) para conexão com a tabela de vendas (sale)
   */
  @ManyToOne
  @JoinColumn(name = "sale_id", nullable = false)
  private Sale sale;

  /**
   * ID do item vendido, Produto ou Serviço (product_id / job_id).
   */
  @Column(name = "item_id", nullable = false)
  private UUID itemId;

  /**
   * Identifica o tipo do item vendido, se Produto ou Serviço
   * Facilita buscar pelo item dentro da sua respectiva tabela ('product' ou 'job').
   */
  @Column(name = "item_type", length = 10)
  private String itemType;

  /**
   * Quantidade do Produto/Serviço vendida.
   */
  @Column(nullable = false)
  private Integer quantity = 1;

  /**
   * Valor unitário no momento da venda para o Produto/Serviço.
   */
  @Column(name = "unit_price", nullable = false)
  private BigDecimal unitPrice;

  /**
   * Desconto aplicado sobre o Produto/Serviço.
   */
  @Column(name = "unit_discount", nullable = false)
  private BigDecimal unitDiscount;

  /**
   * Carimbo de data/hora de criação do registro. Definido automaticamente no PrePersist.
   */
  @Column(name="created_at", updatable = false)
  private LocalDateTime createdAt;

  /**
   * Carimbo de data/hora da última atualização do registro. Definido automaticamente no PreUpdate.
   */
  @Column(name="update_at")
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
