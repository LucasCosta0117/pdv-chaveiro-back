package com.pdv.chaveiro.service;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pdv.chaveiro.dto.SaleRequestDTO;
import com.pdv.chaveiro.model.Job;
import com.pdv.chaveiro.model.SaleStatus;
import com.pdv.chaveiro.model.Product;
import com.pdv.chaveiro.model.Sale;
import com.pdv.chaveiro.model.SaleItem;
import com.pdv.chaveiro.model.SalePayment;
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
    sale.setStatus(SaleStatus.COMPLETED);
    sale.setFiscalNumber(null); // @todo valor null temporário, o sistema de notas será implementado futuramente
    sale.setSellerName(dto.getSellerName()); // @todo valor null temporário, o sistema de usuários será implementado futuramente

    sale.setCode(this.createNewSaleCode());

    // Cria e associa os itens da venda
    List<SaleItem> items = dto.getItems().stream().map(i -> {
      SaleItem item = new SaleItem();

      // Validações/Atualizações para quando o item vendido é um 'Produto'
      if ("product".equals(i.getType())) {
        productServ.updateProductStock(i.getId(), i.getQuantity());
      }

      item.setItemName(this.findItemName(i.getId(), i.getType()));
      item.setQuantity(i.getQuantity());
      item.setUnitPrice(i.getUnit_price());
      item.setDiscount(i.getDiscount());
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

  /**
   * Realiza um soft delete do objeto no banco, de modo que é feito apenas um UPDATE na flag 'deleted' da entidade.
   * <p>O método é transacional: se qualquer operação falhar, toda a transação é revertida.</p>
   * 
   * @param id ID do item à ser excluido.
   */
  @Transactional
  public void softDelete(UUID id) {
    Sale sale = saleRepo.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));

    sale.setIsDeleted(true);

    saleRepo.save(sale);
  }

  /**
   * Lógica de negócio para atualizar uma venda existente.
   * * @param id Identificador da venda.
   * @param dto Dados atualizados.
   * @return A venda atualizada.
   */
  @Transactional
  public Sale updateSale(UUID id, SaleRequestDTO dto) {
    Sale sale = saleRepo.findById(id)
      .orElseThrow(() -> new RuntimeException("Venda não encontrada com o ID: " + id));

    // @TODO - Passar corretamente os valores do dto para a entidade encontrada.

    return saleRepo.save(sale);
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
   * @return O nome do item encontrado (propriedade {@code name} da entidade).
   * @throws RuntimeException Caso o item não seja encontrado em seus respectivos repositórios.
   */
  private String findItemName(UUID itemId, String itemType) {
    if ("product".equals(itemType)) {
        Product product = productServ.getById(itemId);
        return product.getName();
    } 
    
    Job job = jobServ.getById(itemId);
    return job.getName();
  }
}