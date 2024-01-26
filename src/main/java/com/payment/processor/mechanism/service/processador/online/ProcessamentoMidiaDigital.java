package com.payment.processor.mechanism.service.processador.online;

import com.payment.processor.mechanism.service.NotaFiscalServiceImpl;
import com.payment.processor.model.MidiaDigital;
import com.payment.processor.model.OrdemPagamento;
import com.payment.processor.model.Produto;
import com.payment.processor.repository.GuiaRemessaRepository;
import com.payment.processor.repository.MidiaDigitalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProcessamentoMidiaDigital extends ProcessamentoProdutoOnlinePadrao {

    private static final Logger logger = LoggerFactory.getLogger(ProcessamentoMidiaDigital.class);

    private final MidiaDigitalRepository midiaDigitalRepository;

    @Autowired
    public ProcessamentoMidiaDigital(NotaFiscalServiceImpl notaFiscalServiceImpl,
                                     GuiaRemessaRepository guiaRemessaRepository,
                                     MidiaDigitalRepository midiaDigitalRepository) {
        super(notaFiscalServiceImpl, guiaRemessaRepository);
        this.midiaDigitalRepository = midiaDigitalRepository;
    }

    @Override
    public void processePagamento(OrdemPagamento ordemPagamento) throws Exception {
        super.processePagamento(ordemPagamento);
        realizeDownloadVideos(ordemPagamento.getProduto());
        System.out.println("Pagamento video processado...");
    }

    public void realizeDownloadVideos(Produto produto) throws Exception {
        logger.info("Buscando vídeo com nome {} para envio.", produto.getNome());

        MidiaDigital midia = buscarMidias(produto);

        if (!midia.getMidiasRelacionadas().isEmpty()) {
            logger.info("{} Videos relacionados encontrados.", midia.getMidiasRelacionadas().size());
        }

        // adicionar videos a guia remessa
    }

    private MidiaDigital buscarMidias(Produto produto) throws Exception {
        Optional<MidiaDigital> midia = midiaDigitalRepository.findByNome(produto.getNome());

        if (midia.isEmpty()) {
            throw new Exception("Midia não encontrada.");
        }
        logger.info("Video encontrado.");
        return midia.get();
    }
}
