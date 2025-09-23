package dev.sunny.msproduct.exceptions;

public class ProductApiException extends RuntimeException {
    public ProductApiException(String message) {
        super(message);
    }
}
