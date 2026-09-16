package payment_service.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record InventoryReservedEvent(
        UUID eventId,
        UUID orderId,
        UUID productId,
        Integer quantity,
        BigDecimal totalAmount,
        Instant occurredAt
) {
}