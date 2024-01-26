package com.payment.processor.mechanism;

import com.payment.processor.mechanism.service.NotaFiscalServiceImpl;
import com.payment.processor.mechanism.service.processador.online.ProcessamentoProdutoOnlinePadrao;
import com.payment.processor.model.OrdemPagamento;
import com.payment.processor.model.Produto;
import com.payment.processor.repository.GuiaRemessaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ProcessamentoOnlinePadraoTest {

    @Mock
    private NotaFiscalServiceImpl notaFiscalServiceImpl;

    @Mock
    private GuiaRemessaRepository guiaRemessaRepository;

    @Mock
    private ProcessamentoProdutoOnlinePadrao processamentoProdutoOnlinePadrao;

    @BeforeEach
    void setUp() {
        processamentoProdutoOnlinePadrao = new ProcessamentoProdutoOnlinePadrao(notaFiscalServiceImpl, guiaRemessaRepository);
    }

    @Test
    void testProdutoOnline() throws Exception {
        //GIVEN
        OrdemPagamento ordemPagamento = new OrdemPagamento();
        Produto produto = Produto.builder().nome("livro digital").tipo("livro digital").build();
        ordemPagamento.setProduto(produto);

        //WHEN
        processamentoProdutoOnlinePadrao.processePagamento(ordemPagamento);

        //THEN
        verify(notaFiscalServiceImpl).emiteNotaFiscal(ordemPagamento);
    }
}
