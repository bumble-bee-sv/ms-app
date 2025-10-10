package dev.sunny.msproduct.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@MappedSuperclass
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class BaseEntity {

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdOn;
    @UpdateTimestamp
    @Column(nullable = false)
    private Instant modifiedOn;
    private Instant deletedOn;
    private boolean deleted;

}
