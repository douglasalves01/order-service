package orderservice.api;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderRequest(
        @NotNull
        UUID productId,

        @NotNull
        @Positive
        Integer quantity,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal totalAmount
) {
}
