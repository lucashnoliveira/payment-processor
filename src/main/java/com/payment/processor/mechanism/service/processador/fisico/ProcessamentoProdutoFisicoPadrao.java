package com.payment.processor.mechanism.service.processador.fisico;

import com.payment.processor.mechanism.interfaces.ProcessadorPagamentoStrategy;
import com.payment.processor.mechanism.service.NotaFiscalServiceImpl;
import com.payment.processor.mechanism.service.processador.AbstractProcessadorPagamento;
import com.payment.processor.model.OrdemPagamento;
import com.payment.processor.repository.GuiaRemessaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessamentoProdutoFisicoPadrao extends AbstractProcessadorPagamento implements ProcessadorPagamentoStrategy {

    private static final Logger logger = LoggerFactory.getLogger(ProcessamentoProdutoFisicoPadrao.class);
    private final static String DEPARTAMENTO_TRANSPORTE = "transportadora";
    private final static String MOTIVO_ENVIO = "envio_cliente";

    @Autowired
    public ProcessamentoProdutoFisicoPadrao(NotaFiscalServiceImpl notaFiscalServiceImpl,
                                            GuiaRemessaRepository guiaRemessaRepository) {
        super(notaFiscalServiceImpl, guiaRemessaRepository);
    }

    @Override
    public void processePagamento(OrdemPagamento ordemPagamento) {
        emitirNotaFiscal(ordemPagamento);
        gerarGuiaRemessa(DEPARTAMENTO_TRANSPORTE, MOTIVO_ENVIO, ordemPagamento);
        gerePagamentoComissao(ordemPagamento);
    }

    private void gerePagamentoComissao(OrdemPagamento ordemPagamento) {
        Long idVendedor = ordemPagamento.getIdVendedor();

        logger.info("Gerando pagamento comissão ao vendedor com id {}", idVendedor);
        //adicione aqui a logica para pagamento comissão

    }
}
