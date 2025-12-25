package dev.sunny.msorder.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderRequestDTO {

    private String customerId;
    private List<OrderItemRequestDTO> items;

}
