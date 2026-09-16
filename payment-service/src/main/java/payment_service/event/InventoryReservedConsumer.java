package payment_service.event;

import payment_service.domain.Payment;
import payment_service.domain.PaymentStatus;
import payment_service.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class InventoryReservedConsumer {

    private final PaymentService paymentService;
    private final PaymentEventPublisher eventPublisher;

    public InventoryReservedConsumer(
            PaymentService paymentService,
            PaymentEventPublisher eventPublisher
    ) {
        this.paymentService = paymentService;
        this.eventPublisher = eventPublisher;
    }

    @KafkaListener(topics = "inventory.reserved")
    public void consume(InventoryReservedEvent event) {

        Payment payment = paymentService.processPayment(
                event.orderId(),
                event.totalAmount()
        );

        if (payment.getStatus() == PaymentStatus.APPROVED) {

            PaymentApprovedEvent approvedEvent =
                    new PaymentApprovedEvent(
                            UUID.randomUUID(),
                            event.orderId(),
                            payment.getId(),
                            payment.getAmount(),
                            Instant.now()
                    );

            eventPublisher.publishApproved(approvedEvent);

        } else {

            PaymentRejectedEvent rejectedEvent =
                    new PaymentRejectedEvent(
                            UUID.randomUUID(),
                            event.orderId(),
                            payment.getId(),
                            event.productId(),
                            event.quantity(),
                            payment.getAmount(),
                            Instant.now()
                    );

            eventPublisher.publishRejected(rejectedEvent);
        }
    }
}