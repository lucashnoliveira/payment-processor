package com.payment.processor.mechanism;

import com.payment.processor.mechanism.interfaces.ProcessadorPagamentoStrategy;
import com.payment.processor.mechanism.service.processador.fisico.ProcessamentoLivroFisico;
import com.payment.processor.mechanism.service.processador.fisico.ProcessamentoProdutoFisicoPadrao;
import com.payment.processor.mechanism.service.processador.online.ProcessamentoAssinaturas;
import com.payment.processor.mechanism.service.processador.online.ProcessamentoProdutoOnlinePadrao;
import com.payment.processor.mechanism.service.processador.online.ProcessamentoMidiaDigital;
import com.payment.processor.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessadorPagamentoFactoryTest {

    @Mock
    private ProcessamentoProdutoFisicoPadrao processamentoProdutoFisicoPadraoMock;

    @Mock
    private ProcessamentoLivroFisico processamentoLivroFisicoMock;

    @Mock
    private ProcessamentoProdutoOnlinePadrao processamentoProdutoOnlinePadraoMock;

    @Mock
    private ProcessamentoMidiaDigital processamentoMidiaDigitalMock;

    @Mock
    private ProcessamentoAssinaturas processamentoAssinaturasMock;

    private ProcessadorPagamentoFactory factory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        factory = new ProcessadorPagamentoFactory(
                processamentoProdutoFisicoPadraoMock,
                processamentoLivroFisicoMock,
                processamentoProdutoOnlinePadraoMock,
                processamentoMidiaDigitalMock,
                processamentoAssinaturasMock);
    }


    @Test
    void testGetPaymentProcessorCorrectly() throws Exception {

        // GIVEN
        // produto livro
        Produto livro = Produto.builder().nome("Livro Teste").categoria("Fisico").tipo("Livro").build();
        // produto assinatura
        Produto assinatura = Produto.builder().nome("Nova Assinatura").categoria("Online").tipo("Assinaturas").build();
        // produto video
        Produto video = Produto.builder().nome("Video 1").categoria("Online").tipo("Video").build();

        // produto indefinido fisico
        Produto fisicoIndefinido = Produto.builder().nome("Produto X").tipo("eletronico").categoria("Fisico").build();

        // produto indefinido online
        Produto onlineIndefinido = Produto.builder().nome("Produto O").tipo("audio").categoria("Online").build();

        // WHEN
        ProcessadorPagamentoStrategy processor_livro = factory.getPaymentProcessor(livro);
        ProcessadorPagamentoStrategy processor_assinatura = factory.getPaymentProcessor(assinatura);
        ProcessadorPagamentoStrategy processor_video = factory.getPaymentProcessor(video);
        ProcessadorPagamentoStrategy processor_fisico = factory.getPaymentProcessor(fisicoIndefinido);
        ProcessadorPagamentoStrategy processor_online = factory.getPaymentProcessor(onlineIndefinido);

        // Assert
        assertEquals(processamentoLivroFisicoMock, processor_livro);
        assertEquals(processamentoAssinaturasMock, processor_assinatura);
        assertEquals(processamentoMidiaDigitalMock, processor_video);
        assertEquals(processamentoProdutoFisicoPadraoMock, processor_fisico);
        assertEquals(processamentoProdutoOnlinePadraoMock, processor_online);
    }
}
