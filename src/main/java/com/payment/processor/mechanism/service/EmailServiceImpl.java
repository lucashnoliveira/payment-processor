package com.payment.processor.mechanism.service;

import com.payment.processor.mechanism.interfaces.EmailService;
import com.payment.processor.mechanism.interfaces.NotaFiscalService;
import com.payment.processor.model.OrdemPagamento;
import com.payment.processor.model.Produto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Override
    public void envieEmail(OrdemPagamento ordemPagamento) {
        Produto produto = ordemPagamento.getProduto();
        logger.info("Enviando email para o produto - Nome: {}, Id: {}, Ação: {}"
                ,produto.getNome(), produto.getId(), produto.getTipo());
    }
}
