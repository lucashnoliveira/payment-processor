package com.payment.processor.mechanism.service;

import com.payment.processor.mechanism.ProcessadorPagamentoFactory;
import com.payment.processor.mechanism.interfaces.PagamentoService;
import com.payment.processor.mechanism.interfaces.ProcessadorPagamentoStrategy;
import com.payment.processor.model.OrdemPagamento;
import com.payment.processor.model.Produto;
import com.payment.processor.repository.OrdemPagamentoRepository;
import com.payment.processor.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PagamentoService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final ProcessadorPagamentoFactory processorFactory;

    private final ProdutoRepository produtoRepository;

    private final OrdemPagamentoRepository ordemPagamentoRepository;

    @Autowired
    public PaymentServiceImpl(ProcessadorPagamentoFactory processorFactory,
                              ProdutoRepository produtoRepository,
                              OrdemPagamentoRepository ordemPagamentoRepository) {
        this.processorFactory = processorFactory;
        this.produtoRepository = produtoRepository;
        this.ordemPagamentoRepository = ordemPagamentoRepository;
    }


    @Override
    @Transactional
    public void processePagamento(OrdemPagamento ordemPagamento) throws Exception {
        ProcessadorPagamentoStrategy processadorPagamentoStrategy = processorFactory.getPaymentProcessor(ordemPagamento.getProduto());
        busqueOuCrieProduto(ordemPagamento.getProduto());
        salveOrdemPagamento(ordemPagamento);
        processadorPagamentoStrategy.processePagamento(ordemPagamento);
    }

    /* Método responsável por verificar se o produto existe no banco de dados.
    *  Para facilitar os testes que cria o produto caso não exista.
    */
    private void busqueOuCrieProduto(Produto produto) {
        Optional<Produto> produto_banco = produtoRepository.findById(produto.getId());

        if (produto_banco.isPresent()) {
            logger.info("Produto com id: {} encontrado no banco.", produto.getId());
        } else {
            Produto novoProduto = Produto.builder()
                    .nome(produto.getNome())
                    .categoria(produto.getCategoria())
                    .tipo(produto.getTipo())
                    .valor(produto.getValor())
                    .build();

            Produto produtoSalvo = produtoRepository.save(novoProduto);
            produto.setId(produtoSalvo.getId());
            logger.info("Produto com id: {} criado no banco.", produto.getId());

            //throw new Exception("Produto não encontrado...")
        }
    }

    private void salveOrdemPagamento(OrdemPagamento ordemPagamento) {
        logger.info("Salvando ordem de pagamento no banco de dados..");
        OrdemPagamento ordemSalva = ordemPagamentoRepository.save(ordemPagamento);
        ordemPagamento.setId(ordemSalva.getId());
    }
}
