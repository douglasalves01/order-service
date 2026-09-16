package inventory_service.event;

import inventory_service.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentRejectedConsumer {

    private final InventoryService inventoryService;

    public PaymentRejectedConsumer(
            InventoryService inventoryService
    ) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(
            topics = "payments.rejected",
            properties = {
                    "spring.json.value.default.type=inventory_service.event.PaymentRejectedEvent"
            }
    )
    public void consume(PaymentRejectedEvent event) {

        inventoryService.releaseStock(
                event.productId(),
                event.quantity()
        );

        System.out.println(
                "Stock released for order: " + event.orderId()
        );
    }
}