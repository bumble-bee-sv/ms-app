package dev.sunny.msproduct.mappers;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class DateTimeMapper {

    private final ZoneId zoneId = ZoneId.systemDefault();

    public LocalDateTime toLocalDateTime(Instant instant) {

        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, zoneId);

    }

    public Instant toInstant(LocalDateTime localDateTime) {

        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atZone(zoneId).toInstant();

    }

}
