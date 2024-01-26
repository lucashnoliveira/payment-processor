package com.payment.processor.mechanism;

import com.payment.processor.mechanism.service.NotaFiscalServiceImpl;
import com.payment.processor.mechanism.service.processador.fisico.ProcessamentoLivroFisico;
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
public class ProcessamentoLivroFisicoTest {

    @Mock
    private NotaFiscalServiceImpl notaFiscalServiceImpl;

    @Mock
    private GuiaRemessaRepository guiaRemessaRepository;

    @Mock
    private ProcessamentoLivroFisico processamentoLivroFisico;

    @BeforeEach
    void setUp() {
        processamentoLivroFisico = new ProcessamentoLivroFisico(notaFiscalServiceImpl, guiaRemessaRepository);
    }

    @Test
    void testLivroFisico(CapturedOutput output) {
        //GIVEN
        OrdemPagamento ordemPagamento = new OrdemPagamento();
        Produto produto = Produto.builder().nome("Livro Teste").tipo("livro").build();
        ordemPagamento.setProduto(produto);
        ordemPagamento.setIdVendedor(1L);

        //WHEN
        processamentoLivroFisico.processePagamento(ordemPagamento);

        //THEN
        Assertions.assertTrue(output.getOut().contains("Gerando Guia Remessa para o deparamento transportadora"));
        Assertions.assertTrue(output.getOut().contains("Gerando Guia Remessa para o deparamento royalties"));
        Assertions.assertTrue(output.getOut().contains("Gerando pagamento comissão ao vendedor com id 1"));
        verify(notaFiscalServiceImpl).emiteNotaFiscal(ordemPagamento);
    }
}
