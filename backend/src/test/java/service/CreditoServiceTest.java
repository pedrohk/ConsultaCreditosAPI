package service;

import messaging.MessagePublisher;
import model.Credito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.CreditoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditoServiceTest {

    
    @Mock
    private CreditoRepository creditoRepository;

    @Mock
    private MessagePublisher messagePublisher;

    @InjectMocks
    private CreditoService creditoService;

    private Credito creditoMock;

    @BeforeEach
    void setup() {

        creditoMock = new Credito(
                "123456", "7891011", LocalDate.of(2024, 2, 25),
                new BigDecimal("1500.75"), "ISSQN", true,
                new BigDecimal("5.00"), new BigDecimal("30000.00"),
                new BigDecimal("5000.00"), new BigDecimal("25000.00")
        );
    }


    @Test
    void deveRetornarCreditosQuandoNfseExistir() {

        String nfse = "7891011";
        List<Credito> creditosEsperados = List.of(creditoMock);


        when(creditoRepository.findByNumeroNfse(nfse)).thenReturn(creditosEsperados);


        List<Credito> resultado = creditoService.buscarPorNumeroNfse(nfse);


        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(nfse, resultado.get(0).getNumeroNfse());


        verify(messagePublisher, times(1)).publishConsultationEvent(eq("NFS-e"), eq(nfse));


        verify(creditoRepository, times(1)).findByNumeroNfse(nfse);
    }

    @Test
    void deveRetornarListaVaziaQuandoNfseNaoExistir() {

        String nfseInexistente = "000000";
        when(creditoRepository.findByNumeroNfse(nfseInexistente)).thenReturn(Collections.emptyList());


        List<Credito> resultado = creditoService.buscarPorNumeroNfse(nfseInexistente);

        assertTrue(resultado.isEmpty());

        verify(messagePublisher, times(1)).publishConsultationEvent(eq("NFS-e"), eq(nfseInexistente));
    }


    @Test
    void deveRetornarCreditoQuandoNumeroExistir() {
        String numeroCredito = "123456";
        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.of(creditoMock));


        Optional<Credito> resultado = creditoService.buscarPorNumeroCredito(numeroCredito);


        assertTrue(resultado.isPresent());
        assertEquals(numeroCredito, resultado.get().getNumeroCredito());


        verify(messagePublisher, times(1)).publishConsultationEvent(eq("Credito"), eq(numeroCredito));
        verify(creditoRepository, times(1)).findByNumeroCredito(numeroCredito);
    }

    @Test
    void deveRetornarOptionalVazioQuandoCreditoNaoExistir() {

        String numeroCreditoInexistente = "999999";
        when(creditoRepository.findByNumeroCredito(numeroCreditoInexistente)).thenReturn(Optional.empty());


        Optional<Credito> resultado = creditoService.buscarPorNumeroCredito(numeroCreditoInexistente);


        assertTrue(resultado.isEmpty());


        verify(messagePublisher, times(1)).publishConsultationEvent(eq("Credito"), eq(numeroCreditoInexistente));
    }
}