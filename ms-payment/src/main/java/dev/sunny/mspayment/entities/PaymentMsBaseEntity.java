package dev.sunny.mspayment.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@MappedSuperclass
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class PaymentMsBaseEntity {

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdOn;
    @UpdateTimestamp
    private Instant modifiedOn;
    private String createdBy;
    private String modifiedBy;

}