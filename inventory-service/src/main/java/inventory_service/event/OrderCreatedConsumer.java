package inventory_service.event;

import inventory_service.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

    private final InventoryService inventoryService;

    public OrderCreatedConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "orders.created")
    public void consume(OrderCreatedEvent event) {

        inventoryService.reserveStock(event.productId(), event.quantity());
        System.out.println(
                "Stock reserved for order: " + event.orderId()
        );
    }
}