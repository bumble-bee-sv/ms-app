package dev.sunny.mspayment.services.contracts;

import dev.sunny.mspayment.dtos.PaymentRequestDTO;
import dev.sunny.mspayment.dtos.PaymentResponseDTO;
import dev.sunny.mspayment.exceptions.payment.PaymentApiException;

import java.util.UUID;

public interface PaymentService {

    PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequest) throws PaymentApiException;
    PaymentResponseDTO fetchPaymentDetails(UUID orderId) throws PaymentApiException;

}
