package inventory_service.domain;

import inventory_service.domain.exception.InsufficientStockException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    private UUID productId;

    @Column(nullable = false)
    private Integer AvailableQuantity;

    protected Stock() {
    }

    public Stock(UUID productId, Integer AvailableQuantity) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (AvailableQuantity == null) {
            throw new IllegalArgumentException("Available quantity cannot be null");
        }
        this.productId = productId;
        this.AvailableQuantity = AvailableQuantity;
    }

    public void reserve(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity to reserve must be a positive number");
        }
        if (AvailableQuantity < quantity) {
            throw new InsufficientStockException(AvailableQuantity, quantity);
        }
        this.AvailableQuantity -= quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public Integer getAvailableQuantity() {
        return AvailableQuantity;
    }
}
