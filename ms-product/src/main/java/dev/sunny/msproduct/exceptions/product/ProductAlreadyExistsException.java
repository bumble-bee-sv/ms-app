package dev.sunny.msproduct.exceptions.product;

public class ProductAlreadyExistsException extends ProductApiException {
    public ProductAlreadyExistsException(String s) {
        super(s);
    }
}
