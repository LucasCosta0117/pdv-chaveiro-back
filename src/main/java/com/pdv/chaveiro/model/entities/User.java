package com.pdv.chaveiro.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pdv.chaveiro.model.enums.UserRole;

/**
 * Representa um funcionário/dono (user) ligado a uma companhia cadastrada no sistema.
 * <p>Esta entidade mapeia a tabela de usuários, contendo os Dados Pessoais e de Acesso dos usuários do sistema</p>
 *
 * @author Lucas Costa
 * @version 1.0.0
 */
@Entity
@Table(name = "tb_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

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

  /**
   * Retorna as permissões (roles) que o usuário tem.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      // O Spring Security usa o prefixo "ROLE_" por padrão para entender que é um papel.
      return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
  }

  /**
   * Define o email como o "username" padrão do o Spring Security
   */
  @Override
  public String getUsername() {
      return this.email;
  }

  /**
   * Define se a conta não está expirada.
   * <p>Permite definições de validade na conta.</p>
   */
  @Override
  public boolean isAccountNonExpired() {
      return true;
  }

  /**
   * Define se a conta não está bloqueada
   * <p>Permite defições de bloqueio após X tentativas de senha, por exemplo.</p>
   */
  @Override
  public boolean isAccountNonLocked() {
      return true;
  }

  /**
   * Define se as senhas não estão expiradas,
   * <p>Permite obrigar a troca de senhas a cada 90 dias, por exemplo.</p>
   */
  @Override
  public boolean isCredentialsNonExpired() {
      return true;
  }

  /**
   * Define se a conta está habilitada.
   * <p>Define se uma conta está ativa conforme flag de controle 'isActive'.</p>
   */
  @Override
  public boolean isEnabled() {
      return this.isActive;
  }
}