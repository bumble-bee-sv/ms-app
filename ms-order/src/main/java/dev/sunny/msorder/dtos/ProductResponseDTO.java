package dev.sunny.msorder.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProductResponseDTO {

    @JsonProperty(value = "id")
    private Long productId;
    @JsonProperty(value = "title")
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;

}
