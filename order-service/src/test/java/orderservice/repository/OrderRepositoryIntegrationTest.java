package orderservice.repository;

import jakarta.persistence.EntityManager;
import orderservice.domain.Order;
import orderservice.domain.OrderStatus;
import orderservice.infrastructure.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest //Quero iniciar somente a parte necessaria para testar JPA
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class OrderRepositoryIntegrationTest {

    @Container //esse postgre pertence ao lifecycle dos testes
    @ServiceConnection //essa anotação é para o spring boot saber que esse container é um banco de dados que ele pode se conectar
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17");

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldSaveAndFindOrderById() {
        UUID productId = UUID.randomUUID();

        Order order = new Order(
                productId,
                2,
                new BigDecimal("199.90")
        );

        orderRepository.saveAndFlush(order);

        UUID orderId = order.getId();

        entityManager.clear();

        Optional<Order> result =
                orderRepository.findById(orderId);

        assertThat(result).isPresent();

        Order persistedOrder = result.get();

        assertThat(persistedOrder.getId())
                .isEqualTo(orderId);

        assertThat(persistedOrder.getProductId())
                .isEqualTo(productId);

        assertThat(persistedOrder.getQuantity())
                .isEqualTo(2);

        assertThat(persistedOrder.getTotalAmount())
                .isEqualByComparingTo("199.90");

        assertThat(persistedOrder.getOrderStatus())
                .isEqualTo(OrderStatus.PENDING);

        assertThat(persistedOrder.getCreatedAt())
                .isNotNull();
    }
}