package payment_service.service;

import payment_service.domain.Payment;
import payment_service.domain.PaymentStatus;
import payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentService {

    private static final BigDecimal PAYMENT_LIMIT =
            new BigDecimal("1000.00");

    private final PaymentRepository paymentRepository;

    public PaymentService(
            PaymentRepository paymentRepository
    ) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment processPayment(
            UUID orderId,
            BigDecimal amount
    ) {

        PaymentStatus status;

        if (amount.compareTo(PAYMENT_LIMIT) <= 0) {
            status = PaymentStatus.APPROVED;
        } else {
            status = PaymentStatus.REJECTED;
        }

        Payment payment = new Payment(
                orderId,
                amount,
                status
        );

        return paymentRepository.save(payment);
    }
}