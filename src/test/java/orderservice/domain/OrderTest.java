package orderservice.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class OrderTest {
    @Test
    void shouldCreateOrderWithPendingStatus() {
        UUID productId = UUID.randomUUID();
        BigDecimal totalAmount = new BigDecimal("199.90");

        Order order = new Order(
                productId,
                2,
                totalAmount
        );

        assertThat(order.getId()).isNotNull();
        assertThat(order.getProductId()).isEqualTo(productId);
        assertThat(order.getQuantity()).isEqualTo(2);
        assertThat(order.getTotalAmount())
                .isEqualByComparingTo("199.90");
        assertThat(order.getOrderStatus())
                .isEqualTo(OrderStatus.PENDING);
        assertThat(order.getCreatedAt()).isNotNull();
    }


    @Test
    void shouldNotCreateOrderWithNullProductId() {
        assertThatThrownBy(() ->
                new Order(
                        null,
                        2,
                        new BigDecimal("199.90")
                )
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product ID cannot be null");
    }

    @ParameterizedTest //executa o teste varias vezes com diferentes valores de entrada
    @NullSource
    @ValueSource(ints = {0, -1, -10})
    void shouldNotCreateOrderWithInvalidQuantity(Integer quantity) {
        assertThatThrownBy(() ->
                new Order(
                        UUID.randomUUID(),
                        quantity,
                        new BigDecimal("199.90")
                )
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be greater than zero");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"0.0", "-0.01", "-100.00"})
    void shouldNotCreateOrderWithInvalidTotalAmount(BigDecimal totalAmount) {
        assertThatThrownBy(() ->
                new Order(
                        UUID.randomUUID(),
                        2,
                        totalAmount
                )
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Total amount must be greater than zero");
    }
}