package orderservice.application;

import jakarta.transaction.Transactional;
import orderservice.domain.Order;
import orderservice.infrastructure.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(UUID productId, Integer quantity, BigDecimal totalAmount) {
        Order order = new Order(productId, quantity, totalAmount);

        return orderRepository.save(order);
    }
}
