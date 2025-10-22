package com.pdv.chaveiro.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.chaveiro.model.Product;
import com.pdv.chaveiro.service.ProductService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductController {

  private final ProductService productServ;

  public ProductController(ProductService productService){
    productServ = productService;
  }

  @GetMapping("/all") 
  public List<Product> getAllProducts() {
    return productServ.getAll();
  }
}