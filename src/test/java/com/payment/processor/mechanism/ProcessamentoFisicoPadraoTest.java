package com.payment.processor.mechanism;

import com.payment.processor.mechanism.service.NotaFiscalServiceImpl;
import com.payment.processor.mechanism.service.processador.fisico.ProcessamentoProdutoFisicoPadrao;
import com.payment.processor.model.OrdemPagamento;
import com.payment.processor.model.Produto;
import com.payment.processor.repository.GuiaRemessaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class ProcessamentoFisicoPadraoTest {

    @Mock
    private NotaFiscalServiceImpl notaFiscalServiceImpl;

    @Mock
    private GuiaRemessaRepository guiaRemessaRepository;

    @Mock
    private ProcessamentoProdutoFisicoPadrao processamentoProdutoFisicoPadrao;

    @BeforeEach
    void setUp() {
        processamentoProdutoFisicoPadrao = new ProcessamentoProdutoFisicoPadrao(notaFiscalServiceImpl, guiaRemessaRepository);
    }

    @Test
    void testProdutoFisico(CapturedOutput output) {
        //GIVEN
        OrdemPagamento ordemPagamento = new OrdemPagamento();
        Produto produto = Produto.builder()
                .nome("Geladeira")
                .tipo("eletronico")
                .build();
        ordemPagamento.setProduto(produto);
        ordemPagamento.setIdVendedor(1L);

        //WHEN
        processamentoProdutoFisicoPadrao.processePagamento(ordemPagamento);

        //THEN
        Assertions.assertTrue(output.getOut().contains("Gerando Guia Remessa para o deparamento transportadora"));
        Assertions.assertTrue(output.getOut().contains("Gerando pagamento comissão ao vendedor com id 1"));
        verify(notaFiscalServiceImpl).emiteNotaFiscal(ordemPagamento);
    }
}
