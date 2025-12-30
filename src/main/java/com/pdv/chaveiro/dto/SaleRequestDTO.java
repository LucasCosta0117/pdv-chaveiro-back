package com.pdv.chaveiro.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * DTO para receber os dados de uma nova venda enviados pelo front-end.
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Data
public class SaleRequestDTO {
  private BigDecimal subtotal;
  private BigDecimal discounts;
  private BigDecimal total;
  private String sellerName;
  private List<PaymentDTO> payment;
  private List<ItemDTO> items;
}
