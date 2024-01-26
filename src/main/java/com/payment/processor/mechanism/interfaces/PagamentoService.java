package com.payment.processor.mechanism.interfaces;

import com.payment.processor.model.OrdemPagamento;

public interface PagamentoService {
   
    /**
     * Processa pagamento para cada tipo especifico de produto
     * @param ordemPagamento
     * @return
     */
    public void processePagamento(OrdemPagamento ordemPagamento) throws Exception;

}
