package com.pdv.chaveiro.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

import com.pdv.chaveiro.model.enums.UserRole;

/**
 * Representa um funcionário/dono (user) ligado a uma companhia cadastrada no sistema.
 * <p>Esta entidade mapeia a tabela de usuários, contendo os Dados Pessoais e de Acesso dos usuários do sistema</p>
 *
 * @author Lucas Costa
 * @version 1.0.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  /**
   * Identificador único (Primary Key) da entidade. Gerado automaticamente pelo banco de dados.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * Nome do funcionário/dono de uma companhia cadastrada
   */
  @Column(nullable = false)
  private String name;

  /**
   * Email usado para login
   */
  @Column(nullable = false, unique = true)
  private String email;

  /**
   * Senha usada para login
   */
  @Column(nullable = false)
  private String password;

  /**
   * Relação do usuário com a Empresa
   */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  /**
   * Controle de Sistema e Segurança
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role;

  /**
   * Indica se o usuário está ativo no sistema.
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