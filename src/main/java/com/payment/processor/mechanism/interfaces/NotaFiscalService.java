package com.payment.processor.mechanism.interfaces;

import com.payment.processor.model.OrdemPagamento;

public interface NotaFiscalService {

    public void emiteNotaFiscal(OrdemPagamento ordemPagamento);

}
