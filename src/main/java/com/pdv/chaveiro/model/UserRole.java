package com.pdv.chaveiro.model;

public enum UserRole {
    ADMIN,    // Acesso total: acesso a todas as telas, configurações do sistema, exclusão de dados.
    MANAGER,  // Acesso gerencial: catálogo de produtos, histórico de vendas, estoque (não vê config. do sistema).
    CASHIER   // Acesso operacional: apenas tela de Caixa (PDV) e visualização básica do catálogo.
}