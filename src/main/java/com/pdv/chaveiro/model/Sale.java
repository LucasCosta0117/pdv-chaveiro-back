package com.pdv.chaveiro.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

/**
 * Representa uma Venda (sale) seja de produto ou serviço realizado pelo estabelicimento.
 * <p>Esta entidade mapeia a tabela de vendas, contendo atributos como total, itens vendidos e metodos de pagamentos,
 * e é a principal base do registro de transações no PDV.</p>
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Entity
@Data
public class Sale {
  /**
   * Identificador único (Primary Key) da entidade. Gerado automaticamente pelo banco de dados.
   */
  @Id
  @GeneratedValue
  private UUID id;

  /**
   * Total da venda sem considerar os descontos.
   */
  @Column
  private BigDecimal subtotal;

  /**
   * Total dos descontos aplicads na venda.
   */
  @Column(name = "total_discount")
  private BigDecimal totalDiscount;

  /**
   * Total da venda considerando o total dos descontos.
   */
  @Column
  private BigDecimal total;

  /**
   * Registra o status da ordem de venda.
   */
  @Enumerated(EnumType.STRING)
  @Column(name="payment_status", nullable = false, length = 20)
  private PaymentStatus paymentStatus = PaymentStatus.PENDING;

  /**
   * Número da Nota fiscal.
   */
  @Column(name="fiscal_number", length = 50, unique = true)
  private String fiscalNumber;

  /**
   * Número da Nota fiscal.
   */
  @Column(name="fiscal_notes")
  private String fiscalNotes;

  /**
   * Usuário (ou vendedor) que realizou a venda.
   */
  @Column(name = "user_id")
  private UUID userId;

  /**
   * Registra os itens (Produtos/Serviços) vendidos.
   */
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "sale")
  @JsonManagedReference //@todo remover após criar um SaleResponseDTO
  private List<SaleItem> items;

  /**
   * Registra os métodos de pagamentos aplicados na venda.
   */
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "sale")
  @JsonManagedReference //@todo remover após criar um SaleResponseDTO
  private List<SalePayment> payments;

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
