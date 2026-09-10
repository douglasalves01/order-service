package orderservice.application;

import jakarta.transaction.Transactional;
import orderservice.domain.Order;
import orderservice.event.OrderCreatedEvent;
import orderservice.event.OrderEventPublisher;
import orderservice.infrastructure.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    public OrderService(OrderRepository orderRepository, OrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.orderEventPublisher = orderEventPublisher;
    }

    @Transactional
    public Order createOrder(UUID productId, Integer quantity, BigDecimal totalAmount) {
        Order order = new Order(productId, quantity, totalAmount);

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID(),
                savedOrder.getId(),
                savedOrder.getProductId(),
                savedOrder.getQuantity(),
                savedOrder.getTotalAmount(),
                Instant.now()
        );
        orderEventPublisher.publish(event);
        return savedOrder;
    }
}
