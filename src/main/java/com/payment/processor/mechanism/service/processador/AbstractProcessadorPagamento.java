package com.payment.processor.mechanism.service.processador;

import com.payment.processor.mechanism.interfaces.NotaFiscalService;
import com.payment.processor.mechanism.service.NotaFiscalServiceImpl;
import com.payment.processor.mechanism.service.PaymentServiceImpl;
import com.payment.processor.model.GuiaRemessa;
import com.payment.processor.model.OrdemPagamento;
import com.payment.processor.model.Produto;
import com.payment.processor.repository.GuiaRemessaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractProcessadorPagamento {

    private static final Logger logger = LoggerFactory.getLogger(AbstractProcessadorPagamento.class);

    private final NotaFiscalService notaFiscalService;

    private final GuiaRemessaRepository guiaRemessaRepository;

    @Autowired
    public AbstractProcessadorPagamento(NotaFiscalServiceImpl notaFiscalServiceImpl,
                                         GuiaRemessaRepository guiaRemessaRepository) {
         this.notaFiscalService = notaFiscalServiceImpl;
         this.guiaRemessaRepository = guiaRemessaRepository;
     }

    public void emitirNotaFiscal(OrdemPagamento ordemPagamento) {
        notaFiscalService.emiteNotaFiscal(ordemPagamento);
    }

    /*Seria necessário e válido colocar uma validação para verificar se o departamento
    * existe para gerar a guia de remessa.
    */
    public void gerarGuiaRemessa(String departamento,
                                 String motivoGuiaRemessa,
                                 OrdemPagamento ordemPagamento) {
        logger.info("Gerando Guia Remessa para o deparamento {}, motivo {}", departamento, motivoGuiaRemessa);

        GuiaRemessa novaGuiaRemessa = GuiaRemessa.builder()
                .ordemPagamento(ordemPagamento)
                .departamento(departamento)
                .motivo(motivoGuiaRemessa)
                .build();

        guiaRemessaRepository.save(novaGuiaRemessa);
    }
}
