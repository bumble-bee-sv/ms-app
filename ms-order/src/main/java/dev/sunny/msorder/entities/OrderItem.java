package dev.sunny.msorder.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class OrderItem extends OrdersMsBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID orderItemId;
    private UUID orderId;
    private Long productId;
    private Integer quantity;
    private BigDecimal itemPrice;

    @Builder
    public OrderItem(Instant createdOn, Instant modifiedOn, UUID orderItemId,
                     UUID orderId, Long productId, Integer quantity, BigDecimal itemPrice) {
        super(createdOn, modifiedOn);
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

}
