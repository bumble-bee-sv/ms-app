package dev.sunny.msproduct.exceptions.handlers;

import dev.sunny.msproduct.exceptions.category.CategoryApiException;
import dev.sunny.msproduct.exceptions.product.ProductApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalApiExceptionHandler {

    @ExceptionHandler(ProductApiException.class)
    public Map<String, Object> handleProductApiException(ProductApiException pae, HttpServletRequest req) {
        return createErrorResponse(pae, req);
    }

    @ExceptionHandler(CategoryApiException.class)
    public Map<String, Object> handleCategoryApiException(CategoryApiException cae, HttpServletRequest req) {
        return createErrorResponse(cae, req);
    }

    private static Map<String, Object> createErrorResponse(RuntimeException re, HttpServletRequest req) {
        return Map.of(
                "error", "Bad Request",
                "message", re.getMessage(),
                "path", req.getRequestURI(),
                "status", 400,
                "timestamp", LocalDateTime.now()
        );
    }

}
