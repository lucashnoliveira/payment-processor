package com.payment.processor.mechanism.service.processador.online;

import com.payment.processor.mechanism.interfaces.ProcessadorPagamentoStrategy;
import com.payment.processor.mechanism.service.NotaFiscalServiceImpl;
import com.payment.processor.mechanism.service.processador.AbstractProcessadorPagamento;
import com.payment.processor.model.OrdemPagamento;
import com.payment.processor.repository.GuiaRemessaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProcessamentoProdutoOnlinePadrao extends AbstractProcessadorPagamento implements ProcessadorPagamentoStrategy {

    public ProcessamentoProdutoOnlinePadrao(NotaFiscalServiceImpl notaFiscalServiceImpl, GuiaRemessaRepository guiaRemessaRepository) {
        super(notaFiscalServiceImpl, guiaRemessaRepository);
    }

    @Override
    public void processePagamento(OrdemPagamento ordemPagamento) throws Exception {
        emitirNotaFiscal(ordemPagamento);
    }

}
