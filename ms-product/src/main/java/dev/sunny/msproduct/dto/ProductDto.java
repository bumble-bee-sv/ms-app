package dev.sunny.msproduct.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDto(Integer uniqueId, String title, BigDecimal price,
                         String description, String category, String image) {}