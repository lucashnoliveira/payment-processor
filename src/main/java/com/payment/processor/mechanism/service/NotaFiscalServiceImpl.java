package com.payment.processor.mechanism.service;

import com.payment.processor.mechanism.interfaces.NotaFiscalService;
import com.payment.processor.model.OrdemPagamento;
import com.payment.processor.model.Produto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotaFiscalServiceImpl implements NotaFiscalService {

    private static final Logger logger = LoggerFactory.getLogger(NotaFiscalServiceImpl.class);
    @Override
    public void emiteNotaFiscal(OrdemPagamento ordemPagamento) {
        Produto produto = ordemPagamento.getProduto();
        logger.info("Emitindo nota fiscal para o produto - Nome: {}, Id: {}..."
                ,produto.getNome(), produto.getId());
    }
}
