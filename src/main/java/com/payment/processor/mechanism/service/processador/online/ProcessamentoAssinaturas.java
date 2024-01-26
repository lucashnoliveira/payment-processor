package com.payment.processor.mechanism.service.processador.online;

import com.payment.processor.mechanism.interfaces.EmailService;
import com.payment.processor.mechanism.service.EmailServiceImpl;
import com.payment.processor.mechanism.service.NotaFiscalServiceImpl;
import com.payment.processor.mechanism.service.processador.AbstractProcessadorPagamento;
import com.payment.processor.model.OrdemPagamento;
import com.payment.processor.model.Produto;
import com.payment.processor.repository.GuiaRemessaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProcessamentoAssinaturas extends ProcessamentoProdutoOnlinePadrao {

    private static final Logger logger = LoggerFactory.getLogger(ProcessamentoAssinaturas.class);

    private final EmailService emailService;

    public ProcessamentoAssinaturas(NotaFiscalServiceImpl notaFiscalServiceImpl,
                                    GuiaRemessaRepository guiaRemessaRepository,
                                    EmailServiceImpl emailServiceImpl) {
        super(notaFiscalServiceImpl, guiaRemessaRepository);
        this.emailService = emailServiceImpl;
    }

    @Override
    public void processePagamento(OrdemPagamento ordemPagamento) throws Exception {
        processe_assinatura(ordemPagamento);
        super.processePagamento(ordemPagamento);
    }

    private void processe_assinatura(OrdemPagamento ordemPagamento) throws Exception {
        Produto produto = ordemPagamento.getProduto();

        switch (produto.getTipo()) {
            case "nova_assinatura":
                ativarAssinatura(ordemPagamento);
                break;
            case "upgrade_assinatura":
                upgradeAssinatura(ordemPagamento);
                break;
            default:
                throw new Exception("Ação não suportada. Tipo de assinatura não identificada. " + produto.getTipo());
        }
        emailService.envieEmail(ordemPagamento);
    }

    private void ativarAssinatura(OrdemPagamento ordemPagamento) {
        logger.info("Ativando assinatura para novo membro.");
        // adicionar aqui a lógica para ativar assinatura
    }

    private void upgradeAssinatura(OrdemPagamento ordemPagamento) {
        logger.info("Aplicando upgrade de assinatura.");
        // adicionar aqui a lógica para upgrade de assinatura
    }

}
