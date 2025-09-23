package dev.sunny.msproduct.exceptions;

public class ProductNotFoundException extends ProductApiException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
