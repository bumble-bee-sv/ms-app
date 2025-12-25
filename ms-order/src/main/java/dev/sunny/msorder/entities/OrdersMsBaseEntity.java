package dev.sunny.msorder.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@MappedSuperclass
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class OrdersMsBaseEntity {

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdOn;
    @UpdateTimestamp
    private Instant modifiedOn;

}
