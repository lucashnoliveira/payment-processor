package com.payment.processor.mechanism.interfaces;

import com.payment.processor.model.OrdemPagamento;

public interface EmailService {

    public void envieEmail(OrdemPagamento ordemPagamento);

}
