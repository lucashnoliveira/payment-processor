package com.payment.processor.mechanism.service.processador.fisico;

import com.payment.processor.mechanism.service.NotaFiscalServiceImpl;
import com.payment.processor.mechanism.service.processador.fisico.ProcessamentoProdutoFisicoPadrao;
import com.payment.processor.model.OrdemPagamento;
import com.payment.processor.repository.GuiaRemessaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessamentoLivroFisico extends ProcessamentoProdutoFisicoPadrao {

    @Autowired
    public ProcessamentoLivroFisico(NotaFiscalServiceImpl notaFiscalServiceImpl,
                                    GuiaRemessaRepository guiaRemessaRepository) {
        super(notaFiscalServiceImpl, guiaRemessaRepository);
    }

    @Override
    public void processePagamento(OrdemPagamento ordemPagamento) {
        super.processePagamento(ordemPagamento);
        gerarGuiaRemessa("royalties", "pagamento_royalties", ordemPagamento);
        System.out.println("Pagamento livro fisico processado...");
    }
}
