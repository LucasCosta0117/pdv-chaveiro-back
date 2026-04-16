package com.pdv.chaveiro.service;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pdv.chaveiro.dto.SaleRequestDTO;
import com.pdv.chaveiro.model.entities.Company;
import com.pdv.chaveiro.model.entities.Job;
import com.pdv.chaveiro.model.entities.Product;
import com.pdv.chaveiro.model.entities.Sale;
import com.pdv.chaveiro.model.entities.SaleItem;
import com.pdv.chaveiro.model.entities.SalePayment;
import com.pdv.chaveiro.model.enums.SaleStatus;
import com.pdv.chaveiro.repository.SaleRepository;

import jakarta.persistence.EntityNotFoundException;
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
   * Repositório JPA para acesso e manipulação de dados da entidade SalePayment.
   */
  private final JobService jobServ;

  /**
   * Retorna uma lista contendo todos as vendas (${@link Sale}) ativas da empresa do usuário autenticado.
   * @param company A empresa do usuário autenticado.
   * @return Uma lista {@link List} de objetos {@link Sale}.
   */
  public List<Sale> getAllByCompany(Company company) {
    return saleRepo.findAllByCompanyIdAndIsDeletedFalse(company.getId());
  }

  /**
   * Retorna um objeto do tipo ${@link Sale} com base no ID fornecido, validando a empresa e a permissão do usuário.
   * @param saleId   ID para identificação e busca da venda.
   * @param company A empresa do utilizador autenticado.
   * @return Um objeto {@link Sale}.
   * @throws EntityNotFoundException caso a venda não seja encontrado ou pertença a outra empresa.
   */
  public Sale getByIdAndCompany(UUID saleId, Company company) {
    return saleRepo.findByIdAndCompanyIdAndIsDeletedFalse(saleId, company.getId())
      .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada ou acesso negado."));
  }

  /**
   * Persiste uma nova venda e suas entidades relacionadas (itens e pagamentos).
   * <p>O método é transacional: se qualquer operação falhar, toda a transação é revertida.</p>
   * 
   * @param dto Objeto contendo os dados da nova venda vindos do front-end.
   * @param company A empresa do utilizador que está a criar o registo.
   * @return A entidade {@link Sale} salva, incluindo seus itens e pagamentos.
   */
  @Transactional
  public Sale saveSale(SaleRequestDTO dto, Company company) {
    Sale sale = new Sale();

    sale.setSubtotal(dto.subtotal());
    sale.setTotalDiscount(dto.discounts());
    sale.setTotal(dto.total());
    sale.setStatus(dto.status());
    sale.setFiscalNumber(dto.fiscalNumber());
    sale.setSaleNotes(dto.saleNotes());
    sale.setSellerName(dto.sellerName());
    sale.setCompany(company);
    sale.setIsDeleted(false);
    sale.setCode(this.createNewSaleCode());

    // Cria e associa os itens da venda
    List<SaleItem> items = dto.items().stream().map(i -> {
      SaleItem item = new SaleItem();

      // Validações/Atualizações para quando o item vendido é um 'Produto'
      if ("products".equals(i.getEntity())) {
        productServ.updateProductStock(i.getId(), i.getQuantity(), company);
      }

      item.setItemName(this.findItemName(i.getId(), i.getEntity(), company));
      item.setQuantity(i.getQuantity());
      item.setUnitPrice(i.getUnit_price());
      item.setDiscount(i.getDiscount());
      item.setSale(sale);

      return item;
    }).collect(Collectors.toList());

    sale.setItems(items);

    // Cria e associa os pagamentos
    List<SalePayment> payments = dto.payment().stream().map(p -> {
      SalePayment pay = new SalePayment();
      pay.setMethod(p.getMethod());
      pay.setAmount(p.getAmount());
      pay.setSale(sale);

      return pay;
    }).collect(Collectors.toList());

    sale.setPayments(payments);
    
    return saleRepo.save(sale);
  }

  /**
   * Atualiza os dados de uma venda existente
   * @param id Identificador da venda.
   * @param dto Dados atualizados.
   * @param company A empresa do utilizador.
   * @return A entidade {@link Sale} atualizada.
   */
  @Transactional
  public Sale updateSale(UUID id, SaleRequestDTO dto, Company company) {
    Sale sale = this.getByIdAndCompany(id, company);

    if (sale.getStatus() == SaleStatus.PENDING) {
      // Regra para o fiscal_number
      if (dto.fiscalNumber() != null && !dto.fiscalNumber().isBlank() && !dto.fiscalNumber().equals(sale.getFiscalNumber())) {
        sale.setFiscalNumber(dto.fiscalNumber());
      }
      // Regra para o sale_notes
      if (dto.saleNotes() != null && !dto.saleNotes().isBlank() && !dto.saleNotes().equals(sale.getSaleNotes())) {
        sale.setSaleNotes(dto.saleNotes());
      }
      // Regra para o status
      if (sale.getStatus() != dto.status()) {
        sale.setStatus(dto.status());
      }
    } else if (sale.getStatus() == SaleStatus.COMPLETED) {
      // Regra para o status
      if (sale.getStatus() != dto.status()) {
        sale.setStatus(dto.status());
      }
    }

    return saleRepo.save(sale);
  }

  /**
   * Realiza um soft delete do objeto no banco (UPDATE na flag 'isDeleted').
   * @param id ID do item à ser excluido.
   * @param company A empresa do utilizador autenticado.
   */
  @Transactional
  public void softDelete(UUID id, Company company) {
    Sale sale = saleRepo.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));

    sale.setIsDeleted(true);

    saleRepo.save(sale);
  }

  /**
   * Gera um código identificador único para uma nova venda.
   * <p>
   * O código segue o padrão: {@code IZI-YYMMDD-TIMESTAMP-RANDOM}
   * Onde:
   * <ul>
   * <li><b>IZI</b>: Prefixo fixo do sistema.</li>
   * <li><b>TIMESTAMP</b>: Representação do momento atual em milissegundos (Unix Epoch).</li>
   * <li><b>RANDOM</b>: Sufixo aleatório de 3 caracteres para evitar colisões em processos simultâneos.</li>
   * </ul>
   * </p>
   * 
   * @return Uma {@link String} representando o código único da venda. Exemplo: IZI1735582847123A9Z
   */
  private String createNewSaleCode() {
    long millis = Instant.now().toEpochMilli();
    String randomPart = generateRandomSuffix(3);
    
    return String.format("IZI%d%s", millis, randomPart);
  }

  /**
   * Gera uma string aleatória alfanumérica.
   * 
   * @param length Comprimento da string desejada.
   * @return String aleatória contendo letras maiúsculas e números.
   */
  private String generateRandomSuffix(int length) {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    StringBuilder suffix = new StringBuilder();
    Random random = new Random();
    
    for (int i = 0; i < length; i++) {
      suffix.append(chars.charAt(random.nextInt(chars.length())));
    }
    
    return suffix.toString();
  }

  /**
   * Busca o nome descritivo de um item (Produto ou Serviço) com base no seu ID e tipo.
   * 
   * @param itemId   O {@link UUID} identificador único do item no banco de dados.
   * @param itemType Uma {@link String} indicando a categoria do item (ex: "product" para produtos, 
   * qualquer outro valor será tratado como serviço/job).
   * @param company A empresa do utilizador que está a criar o registo.
   * @return O nome do item encontrado (propriedade {@code name} da entidade).
   * @throws RuntimeException Caso o item não seja encontrado em seus respectivos repositórios.
   */
  private String findItemName(UUID itemId, String itemType, Company company) {
    if ("products".equals(itemType)) {
      Product product = productServ.getByIdAndCompany(itemId, company);
      return product.getName();
    }
    
    Job job = jobServ.getByIdAndCompany(itemId, company);
    return job.getName();
  }
}