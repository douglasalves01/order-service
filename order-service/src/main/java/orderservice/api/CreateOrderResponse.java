package orderservice.api;

import orderservice.domain.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderResponse(
        UUID id,
        UUID productId,
        Integer quantity,
        BigDecimal totalAmount,
        OrderStatus status
) {
}
