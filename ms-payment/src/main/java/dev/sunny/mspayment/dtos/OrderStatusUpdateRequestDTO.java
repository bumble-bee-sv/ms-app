package dev.sunny.mspayment.dtos;

import dev.sunny.mspayment.entities.constants.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderStatusUpdateRequestDTO {

    private UUID orderId;
    private PaymentStatus paymentStatus;

}
