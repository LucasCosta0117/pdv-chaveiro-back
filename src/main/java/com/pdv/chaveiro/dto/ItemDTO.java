package com.pdv.chaveiro.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

/**
 * DTO representando um item da venda (Produto ou Serviço).
 */
@Data
public class ItemDTO {
  private UUID id;
  private String type;
  private Integer quantity;
  private BigDecimal unit_price;
  private BigDecimal discount;
}