package service;

import messaging.MessagePublisher;
import model.Credito;
import org.springframework.stereotype.Service;
import repository.CreditoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CreditoService {

    private final CreditoRepository creditoRepository;
    private final MessagePublisher messagePublisher;


    public CreditoService(CreditoRepository creditoRepository, MessagePublisher messagePublisher) {
        this.creditoRepository = creditoRepository;
        this.messagePublisher = messagePublisher;
    }

    public List<Credito> buscarPorNumeroNfse(String numeroNfse) {

        messagePublisher.publishConsultationEvent("NFS-e", numeroNfse);

        return creditoRepository.findByNumeroNfse(numeroNfse);
    }

    public Optional<Credito> buscarPorNumeroCredito(String numeroCredito) {
        messagePublisher.publishConsultationEvent("Credito", numeroCredito);

        return creditoRepository.findByNumeroCredito(numeroCredito);
    }
}