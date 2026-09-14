package inventory_service.domain.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Integer availableQuantity, Integer requestedQuantity) {
        super("Insufficient stock: available " + availableQuantity + ", requested " + requestedQuantity);
    }
}
