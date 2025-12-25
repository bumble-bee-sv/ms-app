package dev.sunny.msorder.services;

import dev.sunny.msorder.dtos.OrderRequestDTO;
import dev.sunny.msorder.dtos.OrderResponseDTO;
import dev.sunny.msorder.entities.constants.OrderStatus;

import java.util.UUID;

public interface OrderService {

    OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO getOrderById(UUID orderId);
    void updateOrderStatus(UUID orderId, OrderStatus status);

}
