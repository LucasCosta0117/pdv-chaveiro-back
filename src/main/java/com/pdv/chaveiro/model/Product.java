package com.pdv.chaveiro.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;

@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 45, nullable = false)
  private String name;

  @Column(length = 45)
  private String brand;

  @Column(length = 45)
  private String code;

  @Column(length = 45)
  private String department;

  @Column(length = 45)
  private String category;

  @Column(length = 45)
  private String subcategory;

  @Column
  @Min(value = 0, message = "Price value cannot be negative")
  private BigDecimal price;

  @Column
  @Min(value = 0, message = "Stock quantity cannot be negative")
  private Integer stock;

  @Column(name = "img_url", columnDefinition = "TEXT")
  private String imgUrl;

  @Column(name = "can_sale")
  private Boolean canSale;

  @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
  private Boolean isActive = true;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
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