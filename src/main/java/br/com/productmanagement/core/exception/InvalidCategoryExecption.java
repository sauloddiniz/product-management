package br.com.productmanagement.core.exception;

public class InvalidCategoryExecption extends RuntimeException {
    public InvalidCategoryExecption(String message) {
        super("Invalid category: " + message);
    }
}
