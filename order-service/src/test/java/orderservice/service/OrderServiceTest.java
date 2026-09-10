package orderservice.service;


import orderservice.application.OrderService;
import orderservice.domain.Order;
import orderservice.domain.OrderStatus;
import orderservice.infrastructure.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock //cria mock da interface OrderRepository para simular o comportamento do repositório de pedidos
    private OrderRepository orderRepository;

    @InjectMocks //cria uma instância da classe OrderService e injeta o mock do OrderRepository nela
    private OrderService orderService;

    @Test
    void shouldCreateAndSaveOrder() {
        UUID productId = UUID.randomUUID();
        BigDecimal totalAmount = new BigDecimal("199.90");

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.createOrder(
                productId,
                2,
                totalAmount
        );

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getProductId()).isEqualTo(productId);
        assertThat(result.getQuantity()).isEqualTo(2);
        assertThat(result.getTotalAmount())
                .isEqualByComparingTo(totalAmount);
        assertThat(result.getOrderStatus())
                .isEqualTo(OrderStatus.PENDING);

        verify(orderRepository, times(1))
                .save(any(Order.class));
    }

    @Test
    void shouldNotSaveOrderWhenOrderIsInvalid() {
        assertThatThrownBy(() ->
                orderService.createOrder(
                        UUID.randomUUID(),
                        0,
                        new BigDecimal("199.90")
                )
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be greater than zero");

        verify(orderRepository, never())
                .save(any(Order.class));
    }
}
