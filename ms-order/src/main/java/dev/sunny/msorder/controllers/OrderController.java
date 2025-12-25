package dev.sunny.msorder.controllers;

import dev.sunny.msorder.dtos.OrderRequestDTO;
import dev.sunny.msorder.dtos.OrderResponseDTO;
import dev.sunny.msorder.entities.constants.OrderStatus;
import dev.sunny.msorder.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController @RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<@NonNull OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO orderResponseDTO = orderService.placeOrder(orderRequestDTO);
        return ResponseEntity.ok(orderResponseDTO);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<@NonNull OrderResponseDTO> getOrderById(@PathVariable @NonNull UUID orderId) {
        OrderResponseDTO orderResponseDTO = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderResponseDTO);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable @NonNull UUID orderId,
                                                    @RequestParam @NonNull String status) {
        orderService.updateOrderStatus(orderId, OrderStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok().body("Order status updated successfully.");
    }

}
