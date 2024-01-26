package com.payment.processor.mechanism;

import com.payment.processor.mechanism.interfaces.ProcessadorPagamentoStrategy;
import com.payment.processor.mechanism.service.processador.fisico.ProcessamentoLivroFisico;
import com.payment.processor.mechanism.service.processador.fisico.ProcessamentoProdutoFisicoPadrao;
import com.payment.processor.mechanism.service.processador.online.ProcessamentoAssinaturas;
import com.payment.processor.mechanism.service.processador.online.ProcessamentoProdutoOnlinePadrao;
import com.payment.processor.mechanism.service.processador.online.ProcessamentoMidiaDigital;
import com.payment.processor.model.Produto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ProcessadorPagamentoFactory {
    private final Map<String, ProcessadorPagamentoStrategy> processorsByProductType;

    private final Map<String, ProcessadorPagamentoStrategy> processorsByCategory;

    public ProcessadorPagamentoFactory(ProcessamentoProdutoFisicoPadrao processamentoProdutoFisicoPadrao,
                                       ProcessamentoLivroFisico processamentoLivroFisico,
                                       ProcessamentoProdutoOnlinePadrao processamentoProdutoOnlinePadrao,
                                       ProcessamentoMidiaDigital processamentoMidiaDigital,
                                       ProcessamentoAssinaturas processamentoAssinaturas){

        // add specific processor by product type
        this.processorsByProductType = new HashMap<>();
        processorsByProductType.put("Livro", processamentoLivroFisico);
        processorsByProductType.put("Video", processamentoMidiaDigital);
        processorsByProductType.put("Assinaturas", processamentoAssinaturas);

        // add default processor by category
        this.processorsByCategory = new HashMap<>();
        processorsByCategory.put("fisico", processamentoProdutoFisicoPadrao);
        processorsByCategory.put("online", processamentoProdutoOnlinePadrao);
    }

    public ProcessadorPagamentoStrategy getPaymentProcessor(Produto produto) throws Exception {

        return Optional.ofNullable(processorsByProductType.get(produto.getTipo()))
                .or(() -> Optional.ofNullable(processorsByCategory.get(produto.getCategoria().toLowerCase())))
                .orElseThrow(() -> new Exception("Estratégia não encontrada para o tipo: " + produto.getTipo()));
    }
}
