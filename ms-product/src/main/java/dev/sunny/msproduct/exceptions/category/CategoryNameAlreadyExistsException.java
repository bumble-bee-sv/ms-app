package dev.sunny.msproduct.exceptions.category;

public class CategoryNameAlreadyExistsException extends CategoryApiException {
    public CategoryNameAlreadyExistsException(String s) {
        super(s);
    }
}
