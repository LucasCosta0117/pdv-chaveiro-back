package com.pdv.chaveiro.dto;

import java.math.BigDecimal;
import java.util.List;

import com.pdv.chaveiro.model.enums.SaleStatus;

/**
 * DTO para receber os dados de uma nova venda enviados pelo front-end.
 * * @author Lucas Costa
 * @version 1.0.0
 */
public record SaleRequestDTO(
    BigDecimal subtotal,
    BigDecimal discounts,
    BigDecimal total,
    String sellerName,
    SaleStatus status,
    String fiscalNumber,
    String saleNotes,
    List<PaymentDTO> payment,
    List<ItemDTO> items
) {}