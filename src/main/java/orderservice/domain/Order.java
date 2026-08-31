package orderservice.domain;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private Instant createdAt;

    protected Order() {
        // Default constructor for JPA
    }

    public Order(UUID productId, Integer quantity, BigDecimal totalAmount) {
        this.id = UUID.randomUUID();
        this.productId = productId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.orderStatus = OrderStatus.PENDING;
        this.createdAt = Instant.now();
    }
    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
