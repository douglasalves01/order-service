package payment_service.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    private static final String PAYMENT_APPROVED_TOPIC =
            "payments.approved";

    private static final String PAYMENT_REJECTED_TOPIC =
            "payments.rejected";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentEventPublisher(
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishApproved(
            PaymentApprovedEvent event
    ) {
        kafkaTemplate.send(
                PAYMENT_APPROVED_TOPIC,
                event.orderId().toString(),
                event
        );
    }

    public void publishRejected(
            PaymentRejectedEvent event
    ) {
        kafkaTemplate.send(
                PAYMENT_REJECTED_TOPIC,
                event.orderId().toString(),
                event
        );
    }
}