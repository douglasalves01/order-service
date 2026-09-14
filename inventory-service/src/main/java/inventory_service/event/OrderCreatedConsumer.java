package inventory_service.event;

import inventory_service.domain.exception.InsufficientStockException;
import inventory_service.domain.exception.StockNotFoundException;
import inventory_service.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class OrderCreatedConsumer {

    private final InventoryService inventoryService;
    private final InventoryEventPublisher eventPublisher;

    public OrderCreatedConsumer(
            InventoryService inventoryService,
            InventoryEventPublisher eventPublisher
    ) {
        this.inventoryService = inventoryService;
        this.eventPublisher = eventPublisher;
    }

    @KafkaListener(topics = "orders.created")
    public void consume(OrderCreatedEvent event) {

        try {
            inventoryService.reserveStock(
                    event.productId(),
                    event.quantity()
            );

            InventoryReservedEvent reservedEvent =
                    new InventoryReservedEvent(
                            UUID.randomUUID(),
                            event.orderId(),
                            event.productId(),
                            event.quantity(),
                            event.totalAmount(),
                            Instant.now()
                    );

            eventPublisher.publishReserved(reservedEvent);

        } catch (InsufficientStockException e) {

            publishRejection(
                    event,
                    InventoryRejectionReason.INSUFFICIENT_STOCK
            );

        } catch (StockNotFoundException e) {

            publishRejection(
                    event,
                    InventoryRejectionReason.STOCK_NOT_FOUND
            );
        }
    }

    private void publishRejection(
            OrderCreatedEvent event,
            InventoryRejectionReason reason
    ) {

        InventoryRejectedEvent rejectedEvent =
                new InventoryRejectedEvent(
                        UUID.randomUUID(),
                        event.orderId(),
                        event.productId(),
                        event.quantity(),
                        reason,
                        Instant.now()
                );

        eventPublisher.publishRejected(rejectedEvent);
    }
}