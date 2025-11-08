package com.pdv.chaveiro.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.chaveiro.dto.SaleRequestDTO;
import com.pdv.chaveiro.model.Sale;
import com.pdv.chaveiro.service.SaleService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador REST responsável por expor os endpoints da API para a gestão e consulta da entidade Sale (Vendas).
 * <p>O path base da API é {@value #"/api/sale"} e o CORS está configurado para aceitar requisições de qualquer origem.</p>
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/sale")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SaleController {
  /**
   * Classe Service responsável pela lógica na camada de negócio.
   */
  private final SaleService saleServ;

  /**
   * Endpoint GET para recuperar todas as vendas realizadas (Sales).
   * <p>Mapeado para: /api/sale/all</p>
   * 
   * @return Uma lista {@link List} de objetos {@link Sale}.
   */
  @GetMapping("/all")
  public List<Sale> findAll() {
    return saleServ.getAll();
  }

  /**
   * Endpoint POST para salvar uma nova venda realizada.
   * <p>Mapeado para: /api/sale/save</p>
   * 
   * @param newSale Objeto JSON contendo os dados da nova venda.
   * @return A venda registrada no banco.
   */
  @PostMapping("/save")
  public ResponseEntity<Sale> save(@RequestBody SaleRequestDTO newSale) {
    Sale saleSaved = saleServ.saveSale(newSale);

    return ResponseEntity.ok(saleSaved);
  }
}
