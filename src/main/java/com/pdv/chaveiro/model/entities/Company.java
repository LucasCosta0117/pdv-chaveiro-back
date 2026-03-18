package com.pdv.chaveiro.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa uma empresa (Company) cadastrada no sistema.
 * <p>Esta entidade registra os dados gerais da empresa, e condece conexão com as demais tabela do sistema, 
 * linkando cada produto, serviço, venda e usuário a apenas uma companhia.</p>
 *
 * @author Lucas Costa
 * @version 1.0.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
  /**
   * Identificador único (Primary Key) da entidade. Gerado automaticamente pelo banco de dados.
  */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * Razão Social.
   */
  @Column(name = "corporate_name", nullable = false)
  private String corporateName;

  /**
   * Nome Fantasia.
   */
  @Column(name = "trade_name", nullable = false)
  private String tradeName;

  /**
   * Número de CNPJ (Registro de pessoa jurídica no Brasil).
   */
  @Column(nullable = false, unique = true, length = 18)
  private String cnpj;

  /**
   * Telefone para contato.
   */
  @Column(length = 20)
  private String phone;

  /**
   * Endereço completo do empresa.
   */
  @Column(columnDefinition = "TEXT")
  private String address;

  /**
   * Indica se a companhia está ativo no sistema.
   */
  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

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