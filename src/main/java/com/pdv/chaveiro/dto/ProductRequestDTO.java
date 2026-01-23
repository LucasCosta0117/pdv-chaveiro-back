package com.pdv.chaveiro.dto;

import java.math.BigDecimal;
import lombok.Data;

/**
 * DTO para receber os dados de um novo produto enviado pelo front-end.
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Data
public class ProductRequestDTO {
  private String name;
  private String brand;
  private String code;
  private String department;
  private String category;
  private String subcategory;
  private BigDecimal price;
  private Integer stock;
  private String imgUrl;
  private Boolean canSale;
  private Boolean isActive;
}
