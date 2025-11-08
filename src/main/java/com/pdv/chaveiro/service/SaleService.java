package com.pdv.chaveiro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pdv.chaveiro.dto.SaleRequestDTO;
import com.pdv.chaveiro.model.PaymentStatus;
import com.pdv.chaveiro.model.Sale;
import com.pdv.chaveiro.model.SaleItem;
import com.pdv.chaveiro.model.SalePayment;
import com.pdv.chaveiro.repository.SaleItemRepository;
import com.pdv.chaveiro.repository.SalePaymentRepository;
import com.pdv.chaveiro.repository.SaleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Serviço responsável pela regra de negócio e gestão da entidade Sale (Vendas).
 * 
 * @author Lucas Costa
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SaleService {
  /**
   * Repositório JPA para acesso e manipulação de dados da entidade Job.
   */
  private final SaleRepository saleRepo;
  /**
   * Repositório JPA para acesso e manipulação de dados da entidade SaleItem.
   */
  private final SaleItemRepository itemRepo;
  /**
   * Repositório JPA para acesso e manipulação de dados da entidade SalePayment.
   */
  private final SalePaymentRepository paymentRepo;

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

    Sale savedSale = saleRepo.save(sale);

    // Cria e associa os itens da venda
    List<SaleItem> items = dto.getItems().stream().map(i -> {
      SaleItem item = new SaleItem();
      item.setSale(savedSale);
      item.setItemId(i.getId());
      item.setItemType(i.getType());
      item.setQuantity(i.getQuantity());
      item.setUnitPrice(i.getUnit_price());
      item.setUnitDiscount(i.getUnit_discount());
      return itemRepo.save(item);
    }).collect(Collectors.toList());

    savedSale.setItems(items);

    // Cria e associa os pagamentos
    List<SalePayment> payments = dto.getPayment().stream().map(p -> {
      SalePayment pay = new SalePayment();
      pay.setSale(savedSale);
      pay.setMethod(p.getMethod());
      pay.setAmount(p.getAmount());
      return paymentRepo.save(pay);
    }).collect(Collectors.toList());

    savedSale.setPayments(payments);

    return savedSale;
  }
}