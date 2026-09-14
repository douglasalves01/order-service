package inventory_service.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventPublisher {

    private static final String INVENTORY_RESERVED_TOPIC =
            "inventory.reserved";

    private static final String INVENTORY_REJECTED_TOPIC =
            "inventory.rejected";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryEventPublisher(
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishReserved(InventoryReservedEvent event) {
        kafkaTemplate.send(
                INVENTORY_RESERVED_TOPIC,
                event.orderId().toString(),
                event
        );
    }

    public void publishRejected(InventoryRejectedEvent event) {
        kafkaTemplate.send(
                INVENTORY_REJECTED_TOPIC,
                event.orderId().toString(),
                event
        );
    }
}