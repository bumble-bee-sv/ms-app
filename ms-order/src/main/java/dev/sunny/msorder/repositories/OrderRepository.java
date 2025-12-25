package dev.sunny.msorder.repositories;

import dev.sunny.msorder.entities.Order;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<@NonNull Order, @NonNull UUID> {
}