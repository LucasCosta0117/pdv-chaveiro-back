package com.pdv.chaveiro.model;

import java.math.BigDecimal;

/**
 * @todo REMOVER. Mock para teste da API
 */
public class MockProduct {

  private Long id;

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

  private Boolean isActive = true;

  private Boolean isDeleted = false;

  // Getters e Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getBrand() { return brand; }
  public void setBrand(String brand) { this.brand = brand; }

  public String getCode() { return code; }
  public void setCode(String code) { this.code = code; }

  public String getDepartment() { return department; }
  public void setDepartment(String department) { this.department = department; }

  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }

  public String getSubcategory() { return subcategory; }
  public void setSubcategory(String subcategory) { this.subcategory = subcategory; }

  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) { this.price = price; }

  public Integer getStock() { return stock; }
  public void setStock(Integer stock) { this.stock = stock; }

  public String getImgUrl() { return imgUrl; }
  public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

  public Boolean getCanSale() { return canSale; }
  public void setCanSale(Boolean canSale) { this.canSale = canSale; }

  public Boolean getIsActive() { return isActive; }
  public void setIsActive(Boolean isActive) { this.isActive = isActive; }

  public Boolean getIsDeleted() { return isDeleted; }
  public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
}