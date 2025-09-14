package dev.sunny.msproduct.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class BaseDto  {
    private Long uniqueId;
    private LocalDateTime createdOn;
    private LocalDateTime modifiedOn;
    private boolean deleted;
}
