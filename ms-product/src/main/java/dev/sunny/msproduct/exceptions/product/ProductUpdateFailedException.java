package dev.sunny.msproduct.exceptions.product;

public class ProductUpdateFailedException extends ProductApiException {
    public ProductUpdateFailedException(String message) {
        super(message);
    }
}
