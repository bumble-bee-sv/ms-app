package dev.sunny.msproduct.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ProductDto(Long uniqueId, String title, BigDecimal price,
                         String description, String category, String image,
                         LocalDateTime createdOn, LocalDateTime modifiedOn) {}