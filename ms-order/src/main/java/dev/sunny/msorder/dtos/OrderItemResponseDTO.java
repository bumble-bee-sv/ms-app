package dev.sunny.msorder.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderItemResponseDTO {

    private UUID orderItemId;
    private Long productId;
    private Integer quantity;
    private BigDecimal itemTotal;

}
