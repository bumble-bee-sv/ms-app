package dev.sunny.mspayment.controllers;

import dev.sunny.mspayment.dtos.PaymentRequestDTO;
import dev.sunny.mspayment.dtos.PaymentResponseDTO;
import dev.sunny.mspayment.exceptions.payment.PaymentApiException;
import dev.sunny.mspayment.services.contracts.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody PaymentRequestDTO paymentRequest)
            throws PaymentApiException {
        return ResponseEntity.ok(paymentService.processPayment(paymentRequest));
    }

}
