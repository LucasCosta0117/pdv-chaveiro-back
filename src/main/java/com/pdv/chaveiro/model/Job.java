package com.pdv.chaveiro.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Representa um Serviço (Job) oferecido pelo estabelicimento comercial.
 * <p>Esta entidade mapeia a tabela de serviços, contendo atributos como preço e categoria,
 * e é uma das bases para o registro de transações no PDV.</p>
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Entity
@Data
public class Job {

  /**
   * Identificador único (Primary Key) da entidade. Gerado automaticamente pelo banco de dados.
   */
  @Id
  @GeneratedValue
  private UUID id;

  /**
   * Nome descritivo do serviço
   * <p>Restrição: Não pode ser nulo e tem um limite de 100 caracteres.</p>
   */
  @Column(length = 100, nullable = false)
  private String name;

  /**
   * Código interno de referência.
   */
  @Column(length = 50)
  private String code;

  /**
   * Categoria principal do serviço.
   */
  @Column(length = 50)
  private String category;

  /**
   * Subcategoria do serviço para granularidade.
   */
  @Column(length = 50)
  private String subcategory;

  /**
   * Preço padrão do serviço.
   * <p>Restrição: Não pode ser negativo (validação @Min(0)).</p>
   */
  @Column
  @Min(value = 0, message = "Price value cannot be negative")
  private BigDecimal price;

  /**
   * Indica se o serviço está ativo no PDV.
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
