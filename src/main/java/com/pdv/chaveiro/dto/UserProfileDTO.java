package com.pdv.chaveiro.dto;

import java.util.UUID;

import com.pdv.chaveiro.model.enums.UserRole;

/**
 * Record (DTO) responsável por encapsular os dados públicos do usuário autenticado.
 * Utilizado para enviar as informações de perfil e permissões para o Front-end.
 * @author Lucas Costa
 * @version 1.0.0
 */
public record UserProfileDTO(
    /**
     * Identificador único do usuário.
     */
    UUID id,
    
    /**
     * Nome completo ou de exibição do usuário.
     */
    String name,
    
    /**
     * E-mail utilizado para acesso.
     */
    String email,
    
    /**
     * Nível de acesso (Role) do usuário no sistema.
     */
    UserRole role,
    
    /**
     * Identificador único da empresa a qual o usuário pertence.
     */
    UUID companyId
) {}