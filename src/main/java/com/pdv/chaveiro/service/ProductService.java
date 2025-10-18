package com.pdv.chaveiro.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.pdv.chaveiro.model.MockProduct;
import com.pdv.chaveiro.model.Product;
import com.pdv.chaveiro.repository.ProductRepository;

@Service
public class ProductService {

  // private final ProductRepository productRepo;

  // public ProductService(ProductRepository productRepository) {
  //   productRepo = productRepository;
  // }

  // public List<Product> getAll() {
  //   return productRepo.findAll();
  // }

  /**
   * @todo REMOVER. Mock para teste da API
   */
  public ProductService() {}

  public List<MockProduct> getAll() {
    // return productRepo.findAll();

    /**
     * @todo Mock do retorno da Repository de produtos.
     * 
     * !REMOVER APÓS MODELAR O BANCO!
     */
    List<MockProduct> mockProducts = new ArrayList<>();

    MockProduct p1 = new MockProduct();
    p1.setId(1L);
    p1.setName("Chave Yale");
    p1.setBrand("Yale");
    p1.setCode("YAL-001");
    p1.setDepartment("Insumo");
    p1.setCategory("Residencial");
    p1.setSubcategory("Portas");
    p1.setPrice(new BigDecimal("0"));
    p1.setStock(100);
    p1.setImgUrl("https://cdn.awsli.com.br/2500x2500/746/746414/produto/268223984/532---gold-cad--gde--ch--l-y---79--m667nywnhz.png");
    p1.setCanSale(false);

    MockProduct p2 = new MockProduct();
    p2.setId(2L);
    p2.setName("Controle Remoto PPA");
    p2.setBrand("PPA");
    p2.setCode("PPA-CTRL");
    p2.setDepartment("Vitrine");
    p2.setCategory("Controle");
    p2.setSubcategory("Portão");
    p2.setPrice(new BigDecimal("25.90"));
    p2.setStock(40);
    p2.setImgUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTWDMB2_hEh9ggHmHeC1fGM2T4wlpHZnhF76w&s");
    p2.setCanSale(true);

    MockProduct p3 = new MockProduct();
    p3.setId(3L);
    p3.setName("Fechadura Elétrica AGL");
    p3.setBrand("AGL");
    p3.setCode("AGL-FECH");
    p3.setDepartment("Vitrine");
    p3.setCategory("Fechadura");
    p3.setSubcategory("Elétrica");
    p3.setPrice(new BigDecimal("189.00"));
    p3.setStock(2);
    p3.setImgUrl("https://framerusercontent.com/images/0UwYaRN1moBCPBDqJkA4loQ3vo.png?width=1200&height=1200");
    p3.setCanSale(true);

    MockProduct p4 = new MockProduct();
    p4.setId(4L);
    p4.setName("Chave Paddo");
    p4.setBrand("Paddo");
    p4.setCode("PDD-001");
    p4.setDepartment("Insumo");
    p4.setCategory("Residencial");
    p4.setSubcategory("Portas");
    p4.setPrice(new BigDecimal("0"));
    p4.setStock(100);
    p4.setImgUrl("https://cdn.awsli.com.br/2500x2500/746/746414/produto/268223984/532---gold-cad--gde--ch--l-y---79--m667nywnhz.png");
    p4.setCanSale(false);

    MockProduct p5 = new MockProduct();
    p5.setId(5L);
    p5.setName("Controle Remoto PPA");
    p5.setBrand("PPA");
    p5.setCode("PPA-CTRL");
    p5.setDepartment("Vitrine");
    p5.setCategory("Controle");
    p5.setSubcategory("Portão");
    p5.setPrice(new BigDecimal("25.90"));
    p5.setStock(40);
    p5.setImgUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTWDMB2_hEh9ggHmHeC1fGM2T4wlpHZnhF76w&s");
    p5.setCanSale(true);

    MockProduct p6 = new MockProduct();
    p6.setId(6L);
    p6.setName("Fechadura Elétrica AGL");
    p6.setBrand("AGL");
    p6.setCode("AGL-FECH");
    p6.setDepartment("Vitrine");
    p6.setCategory("Fechadura");
    p6.setSubcategory("Elétrica");
    p6.setPrice(new BigDecimal("189.00"));
    p6.setStock(2);
    p6.setImgUrl("https://framerusercontent.com/images/0UwYaRN1moBCPBDqJkA4loQ3vo.png?width=1200&height=1200");
    p6.setCanSale(true);

    mockProducts.add(p1);
    mockProducts.add(p2);
    mockProducts.add(p3);
    mockProducts.add(p4);
    mockProducts.add(p5);
    mockProducts.add(p6);

    return mockProducts;
  }
}
