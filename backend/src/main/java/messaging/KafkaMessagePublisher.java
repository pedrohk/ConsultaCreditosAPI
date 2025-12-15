package messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessagePublisher implements MessagePublisher {

    private static final String TOPIC = "creditos-consultados-topic";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaMessagePublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishConsultationEvent(String tipoBusca, String valorBuscado) {
        ConsultaEvent event = new ConsultaEvent(
                tipoBusca,
                valorBuscado,
                System.currentTimeMillis(),
                "API de Creditos"
        );


        kafkaTemplate.send(TOPIC, event);
        System.out.println("Evento de consulta publicado no Kafka: " + event.toString());
    }


    public record ConsultaEvent(
            String tipoBusca,
            String valorBuscado,
            Long timestamp,
            String origem
    ) {
    }
}