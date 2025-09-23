package dev.sunny.msproduct.exceptions.handlers;

import dev.sunny.msproduct.exceptions.ProductApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalProductApiExceptionHandler {

    @ExceptionHandler(ProductApiException.class)
    public Map<String, String> handleProductApiException(ProductApiException pae, HttpServletRequest req) {
        return Map.of(
                "error", pae.getMessage(),
                "timestamp", String.valueOf(LocalDateTime.now()),
                "code", "400",
                "path", req.getRequestURI()
        );
    }

}
