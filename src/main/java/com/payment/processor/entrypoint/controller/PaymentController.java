package com.payment.processor.entrypoint.controller;

import com.payment.processor.mechanism.interfaces.PagamentoService;
import com.payment.processor.model.OrdemPagamento;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pagamentos")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PagamentoService pagamentoService;

    public PaymentController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/processarPagamento")
    public ResponseEntity<String> processarPagamento(@Valid @RequestBody OrdemPagamento ordemPagamento) {
        try {
            logger.info("Iniciando processamento da ordem de pagamento.");
            pagamentoService.processePagamento(ordemPagamento);
            return ResponseEntity.ok("Pagamento processado com sucesso");
        } catch (Exception e) {
            logger.error("Falha no processamento.");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
