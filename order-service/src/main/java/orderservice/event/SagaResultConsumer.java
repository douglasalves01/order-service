package orderservice.event;

import orderservice.application.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SagaResultConsumer {

    private final OrderService orderService;

    public SagaResultConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(
            topics = "payments.approved",
            properties = {
                    "spring.json.value.default.type=orderservice.event.PaymentApprovedEvent"
            }
    )
    public void consumePaymentApproved(
            PaymentApprovedEvent event
    ) {
        orderService.confirmOrder(event.orderId());

        System.out.println(
                "Order confirmed: " + event.orderId()
        );
    }

    @KafkaListener(
            topics = "payments.rejected",
            properties = {
                    "spring.json.value.default.type=orderservice.event.PaymentRejectedEvent"
            }
    )
    public void consumePaymentRejected(
            PaymentRejectedEvent event
    ) {
        orderService.cancelOrder(event.orderId());

        System.out.println(
                "Order cancelled after payment rejection: "
                        + event.orderId()
        );
    }

    @KafkaListener(
            topics = "inventory.rejected",
            properties = {
                    "spring.json.value.default.type=orderservice.event.InventoryRejectedEvent"
            }
    )
    public void consumeInventoryRejected(
            InventoryRejectedEvent event
    ) {
        orderService.cancelOrder(event.orderId());

        System.out.println(
                "Order cancelled after inventory rejection: "
                        + event.orderId()
        );
    }
}