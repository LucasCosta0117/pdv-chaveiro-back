package com.pdv.chaveiro.dto;

import java.math.BigDecimal;
import lombok.Data;

/**
 * DTO representando um método de pagamento e o valor pago conforme 
 * dados anexados à uma nova venda.
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Data
public class PaymentDTO {
  private String method;
  private BigDecimal amount;
}