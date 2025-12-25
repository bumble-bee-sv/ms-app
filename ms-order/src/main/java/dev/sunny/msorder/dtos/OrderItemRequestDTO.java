package dev.sunny.msorder.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderItemRequestDTO {

    private Long productId;
    private int quantity;

}
