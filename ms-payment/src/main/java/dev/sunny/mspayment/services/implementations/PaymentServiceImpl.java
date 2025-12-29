package dev.sunny.mspayment.services.implementations;

import dev.sunny.mspayment.dtos.PaymentRequestDTO;
import dev.sunny.mspayment.dtos.PaymentResponseDTO;
import dev.sunny.mspayment.entities.Payment;
import dev.sunny.mspayment.entities.constants.PaymentStatus;
import dev.sunny.mspayment.exceptions.payment.PaymentApiException;
import dev.sunny.mspayment.mappers.PaymentMapper;
import dev.sunny.mspayment.repositories.PaymentRepository;
import dev.sunny.mspayment.services.clients.OrderClient;
import dev.sunny.mspayment.services.contracts.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.random.RandomGenerator;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;

    @Override
    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequest) throws PaymentApiException {

        Payment payment = paymentMapper.toPayment(paymentRequest);
//        Simulate payment processing logic here (e.g., interacting with a payment gateway)
        boolean isPaymentSuccessful = RandomGenerator.of("L128X256MixRandom").nextBoolean();
        if (isPaymentSuccessful) {
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setTransactionId(UUID.randomUUID().toString());
            orderClient.notifyOrderService(payment.getOrderId(), "CONFIRMED");
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setTransactionId("N/A");
            orderClient.notifyOrderService(payment.getOrderId(), "CANCELLED");
        }

        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toPaymentResponse(savedPayment);

    }

    @Override
    public PaymentResponseDTO fetchPaymentDetails(UUID orderId) throws PaymentApiException {

        Payment completedPayment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentApiException("Payment not found for order ID: " + orderId));
        return paymentMapper.toPaymentResponse(completedPayment);

    }

}
