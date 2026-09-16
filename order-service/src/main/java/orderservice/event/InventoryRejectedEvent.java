package orderservice.event;

import java.time.Instant;
import java.util.UUID;

public record InventoryRejectedEvent(
        UUID eventId,
        UUID orderId,
        UUID productId,
        Integer quantity,
        String reason,
        Instant occurredAt
) {
}