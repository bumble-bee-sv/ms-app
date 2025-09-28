package dev.sunny.msproduct.exceptions.product;

public class ProductNotFoundException extends ProductApiException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
