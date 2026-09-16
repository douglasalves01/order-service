package orderservice.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentRejectedEvent(
        UUID eventId,
        UUID orderId,
        UUID paymentId,
        UUID productId,
        Integer quantity,
        BigDecimal amount,
        Instant occurredAt
) {
}