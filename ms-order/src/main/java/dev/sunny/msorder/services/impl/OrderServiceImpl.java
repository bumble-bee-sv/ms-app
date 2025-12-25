package dev.sunny.msorder.services.impl;

import dev.sunny.msorder.dtos.*;
import dev.sunny.msorder.entities.Order;
import dev.sunny.msorder.entities.OrderItem;
import dev.sunny.msorder.entities.constants.OrderStatus;
import dev.sunny.msorder.repositories.OrderItemRepository;
import dev.sunny.msorder.repositories.OrderRepository;
import dev.sunny.msorder.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestClient restClient;

    @Override
    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) {

//        Calculate total amount
        BigDecimal totalAmountToPay = BigDecimal.ZERO;
//        Store every order item in order to save later
        var orderItems = new java.util.ArrayList<OrderItem>();
//        Validate request DTO and fetch products detail from Product Service in order
        for (OrderItemRequestDTO item : orderRequestDTO.getItems()) {
//            Call Product Service for every item to check availability and update stock
            ProductResponseDTO product = restClient.get()
                    .uri(uriBuilder ->
                            uriBuilder.path("/products/{productId}")
                                    .build(item.getProductId()))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(ProductResponseDTO.class)
                    .getBody();

            if (product == null || product.getStockQuantity().compareTo(item.getQuantity()) < 0)
                throw new RuntimeException("Insufficient stock for product ID: " + item.getProductId());

//            Update stock in Product Service
            ProductResponseDTO updateProduct = restClient.patch()
                    .uri(uriBuilder ->
                            uriBuilder.path("/products/{productId}/update-stock")
                                    .queryParam("quantity", (product.getStockQuantity() - item.getQuantity()))
                                    .build(item.getProductId()))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(ProductResponseDTO.class)
                    .getBody();

            if (updateProduct == null)
                throw new RuntimeException("Failed to update stock for product ID: " + item.getProductId());

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmountToPay = totalAmountToPay.add(itemTotal);

            OrderItem orderItem = OrderItem.builder()
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .itemPrice(itemTotal)
                    .build();
            orderItems.add(orderItem);
        }

        Order order = Order.builder()
                .customerId(orderRequestDTO.getCustomerId())
                .status(OrderStatus.PENDING)
                .totalAmount(totalAmountToPay)
                .build();
        Order savedOrder = orderRepository.save(order);
        List<OrderItem> savedOrderItems = orderItemRepository.saveAll(orderItems);

        return orderResponseMapper(savedOrder, savedOrderItems);

    }

    @Override
    public OrderResponseDTO getOrderById(UUID orderId) {

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
        if (orderItems == null || orderItems.isEmpty())
            throw new RuntimeException("No order found for order ID: " + orderId);

        return orderResponseMapper(existingOrder, orderItems);
    }

    @Override
    public void updateOrderStatus(UUID orderId, OrderStatus status) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        existingOrder.setStatus(status);
        orderRepository.save(existingOrder);
    }

    private OrderResponseDTO orderResponseMapper(Order savedOrder, List<OrderItem> savedOrderItems) {
        return OrderResponseDTO.builder()
                .orderId(savedOrder.getOrderId())
                .orderDate(savedOrder.getModifiedOn())
                .customerId(savedOrder.getCustomerId())
                .totalAmountToPaid(savedOrder.getTotalAmount())
                .status(savedOrder.getStatus())
                .items(savedOrderItems.stream()
                        .map(oi ->
                                OrderItemResponseDTO.builder()
                                        .orderItemId(oi.getOrderItemId())
                                        .itemTotal(oi.getItemPrice())
                                        .productId(oi.getProductId())
                                        .quantity(oi.getQuantity())
                                        .build())
                        .toList())
                .build();
    }

}
