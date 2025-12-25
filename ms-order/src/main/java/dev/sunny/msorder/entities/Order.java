package dev.sunny.msorder.entities;

import dev.sunny.msorder.entities.constants.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders", indexes = {
        @Index(name = "IDX_CUST_ID_MOD_ON", columnList = "customerId, modifiedOn")
})
@Getter @Setter @NoArgsConstructor @ToString
public class Order extends OrdersMsBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID orderId;
//    To be changed later in UUID datatype after ms-user is created for handling users in the system
    private String customerId;
    private BigDecimal totalAmount;
    private OrderStatus status;

    @Builder
    public Order(Instant createdOn, Instant modifiedOn, UUID orderId,
                 String customerId, BigDecimal totalAmount, OrderStatus status) {
        super(createdOn, modifiedOn);
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = status;
    }

}
