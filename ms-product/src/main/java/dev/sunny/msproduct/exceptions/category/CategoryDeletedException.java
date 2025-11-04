package dev.sunny.msproduct.exceptions.category;

public class CategoryDeletedException extends CategoryApiException {
    public CategoryDeletedException(String errorMessage) {
        super(errorMessage);
    }
}
