package com.pdv.chaveiro.dto;

import java.math.BigDecimal;
import lombok.Data;

/**
 * DTO para receber os dados de um novo serviço enviado pelo front-end.
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Data
public class JobRequestDTO {
  private String name;
  private String code;
  private String category;
  private String subcategory;
  private BigDecimal price;
  private Boolean isActive;
}