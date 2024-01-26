package com.payment.processor.mechanism.interfaces;

import com.payment.processor.model.OrdemPagamento;


public interface ProcessadorPagamentoStrategy {
    void processePagamento(OrdemPagamento ordemPagamento) throws Exception;
}
