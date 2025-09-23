package dev.sunny.msproduct.exceptions;

public class ProductUpdateFailedException extends ProductApiException {
    public ProductUpdateFailedException(String message) {
        super(message);
    }
}
