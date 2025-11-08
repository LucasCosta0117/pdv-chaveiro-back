package com.pdv.chaveiro.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
 * Entidade para registro dos métodos de pagamentos e seus respectivos valores associados a uma venda.
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Entity
@Data
@Table(name = "sale_payments")
public class SalePayment {
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
  @JsonBackReference //@todo remover após criar um SaleResponseDTO
  private Sale sale;

  /**
   * Registra o nome do método de pagamento.
   */
  @Column(nullable = false, length = 20)
  private String method; // ex: 'PIX', 'CASH', 'CREDIT', 'DEBIT'

  /**
   * Registra o valor pago através deste método de pagamento.
   */
  @Column(nullable = false)
  private BigDecimal amount;

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
