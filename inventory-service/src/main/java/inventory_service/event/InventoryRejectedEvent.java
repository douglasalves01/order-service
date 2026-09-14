package inventory_service.event;

import java.time.Instant;
import java.util.UUID;

public record InventoryRejectedEvent(
        UUID eventId,
        UUID orderId,
        UUID productId,
        Integer quantity,
        InventoryRejectionReason reason,
        Instant occurredAt
) {
}
