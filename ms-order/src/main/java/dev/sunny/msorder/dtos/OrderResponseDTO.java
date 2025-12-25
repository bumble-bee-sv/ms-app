package dev.sunny.msorder.dtos;

import dev.sunny.msorder.entities.constants.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderResponseDTO {

    private UUID orderId;
//    To be changed later to UUID data type after ms-user is created for handling users in the system
    private String customerId;
    private Instant orderDate;
    private BigDecimal totalAmountToPaid;
    private OrderStatus status;
    private List<OrderItemResponseDTO> items;

}
