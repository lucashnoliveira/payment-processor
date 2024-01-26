package com.payment.processor.mechanism;

import com.payment.processor.model.OrdemPagamento;
import com.payment.processor.model.Produto;
import com.payment.processor.mechanism.service.EmailServiceImpl;
import com.payment.processor.mechanism.service.NotaFiscalServiceImpl;
import com.payment.processor.mechanism.service.processador.online.ProcessamentoAssinaturas;
import com.payment.processor.repository.GuiaRemessaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class ProcessamentoAssinaturasTest {

    @Mock
    private NotaFiscalServiceImpl notaFiscalServiceImpl;

    @Mock
    private GuiaRemessaRepository guiaRemessaRepository;

    @Mock
    private EmailServiceImpl emailServiceImpl;

    @Mock
    private ProcessamentoAssinaturas processamentoAssinaturas;

    @BeforeEach
    void setUp() {
        processamentoAssinaturas = new ProcessamentoAssinaturas(notaFiscalServiceImpl, guiaRemessaRepository, emailServiceImpl);
    }

    @Test
    void testProcesseNovaAssinatura(CapturedOutput output) throws Exception {
        //GIVEN
        OrdemPagamento ordemPagamento = new OrdemPagamento();
        Produto produto = Produto.builder().nome("nova assinatura").tipo("nova_assinatura").build();
        ordemPagamento.setProduto(produto);

        //WHEN
        processamentoAssinaturas.processePagamento(ordemPagamento);

        //THEN
        Assertions.assertTrue(output.getOut().contains("Ativando assinatura para novo membro."));
        verify(emailServiceImpl).envieEmail(ordemPagamento);
    }

    @Test
    void testProcesseAssinaturaUpgrade(CapturedOutput output) throws Exception {
        //GIVEN
        OrdemPagamento ordemPagamento = new OrdemPagamento();
        Produto produto = Produto.builder().nome("upgrade assinatura").tipo("upgrade_assinatura").build();
        ordemPagamento.setProduto(produto);

        //WHEN
        processamentoAssinaturas.processePagamento(ordemPagamento);

        //THEN
        Assertions.assertTrue(output.getOut().contains("Aplicando upgrade de assinatura."));

        verify(emailServiceImpl).envieEmail(ordemPagamento);
    }

    @Test
    void testProcesseAssinaturaException() throws Exception {
        //GIVEN
        OrdemPagamento ordemPagamento = new OrdemPagamento();
        Produto produto = Produto.builder().nome("acao nova assinatura").tipo("acao_nova").build();
        ordemPagamento.setProduto(produto);

        //WHEN
        //THEN
        assertThrows(Exception.class, () -> processamentoAssinaturas.processePagamento(ordemPagamento));
    }
}
