package dev.sunny.msorder.repositories;

import dev.sunny.msorder.entities.OrderItem;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<@NonNull OrderItem, @NonNull UUID> {
    List<OrderItem> findAllByOrderId(UUID orderId);
}