package dev.sunny.mspayment.entities;

import dev.sunny.mspayment.entities.constants.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment")
@Getter @Setter @ToString @NoArgsConstructor
public class Payment extends PaymentMsBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID paymentId;
    private UUID orderId;
    private String customerId;
    private BigDecimal amount;
    private PaymentStatus status;
    private String transactionId;

    @Builder
    public Payment(Instant createdOn, Instant modifiedOn, String createdBy, String modifiedBy, UUID paymentId,
                   UUID orderId, String customerId, BigDecimal amount, PaymentStatus status, String transactionId) {
        super(createdOn, modifiedOn, createdBy, modifiedBy);
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.status = status;
        this.transactionId = transactionId;
    }

}