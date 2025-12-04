package com.pdv.chaveiro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pdv.chaveiro.dto.SaleRequestDTO;
import com.pdv.chaveiro.model.PaymentStatus;
import com.pdv.chaveiro.model.Sale;
import com.pdv.chaveiro.model.SaleItem;
import com.pdv.chaveiro.model.SalePayment;
import com.pdv.chaveiro.repository.SaleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço responsável pela regra de negócio e gestão da entidade Sale (Vendas).
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SaleService {
  /**
   * Repositório JPA para acesso e manipulação de dados da entidade Job.
   */
  private final SaleRepository saleRepo;
  /**
   * Repositório JPA para acesso e manipulação de dados da entidade SalePayment.
   */
  private final ProductService productServ;

  /**
   * Retorna uma lista contendo todos os serviços (Jobs) cadastrados no sistema.
   * 
   * @return Uma lista {@link List} de objetos {@link Sale}.
   */
  public List<Sale> getAll() {
    return saleRepo.findAll();
  }

  /**
   * Persiste uma nova venda e suas entidades relacionadas (itens e pagamentos).
   * <p>O método é transacional: se qualquer operação falhar, toda a transação é revertida.</p>
   * 
   * @param dto Objeto contendo os dados da nova venda vindos do front-end.
   * @return A entidade {@link Sale} salva, incluindo seus itens e pagamentos.
   */
  @Transactional
  public Sale saveSale(SaleRequestDTO dto) {
    Sale sale = new Sale(); // Venda principal

    sale.setSubtotal(dto.getSubtotal());
    sale.setTotalDiscount(dto.getDiscounts());
    sale.setTotal(dto.getTotal());
    sale.setPaymentStatus(PaymentStatus.PAID);
    sale.setFiscalNumber(null); // @todo valor null temporário, o sistema de notas será implementado futuramente
    sale.setUserId(null); // @todo valor null temporário, o sistema de usuários será implementado futuramente

    // Cria e associa os itens da venda
    List<SaleItem> items = dto.getItems().stream().map(i -> {
      SaleItem item = new SaleItem();

      // Validações/Atualizações para quando o item vendido é um 'Produto'
      if ("product".equals(i.getType())) {
        productServ.updateProductStock(i.getId(), i.getQuantity());
      }

      item.setItemId(i.getId());
      item.setItemType(i.getType());
      item.setQuantity(i.getQuantity());
      item.setUnitPrice(i.getUnit_price());
      item.setUnitDiscount(i.getUnit_discount());
      item.setSale(sale);

      return item;
    }).collect(Collectors.toList());

    sale.setItems(items);

    // Cria e associa os pagamentos
    List<SalePayment> payments = dto.getPayment().stream().map(p -> {
      SalePayment pay = new SalePayment();
      pay.setMethod(p.getMethod());
      pay.setAmount(p.getAmount());
      pay.setSale(sale);

      return pay;
    }).collect(Collectors.toList());

    sale.setPayments(payments);
    
    return saleRepo.save(sale);
  }
}