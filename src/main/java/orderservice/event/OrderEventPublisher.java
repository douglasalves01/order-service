package orderservice.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    private static final String ORDER_CREATED_TOPIC =
            "orders.created";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventPublisher(
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(OrderCreatedEvent event) {
        kafkaTemplate.send(
                ORDER_CREATED_TOPIC,
                event.orderId().toString(),
                event
        );
    }
}