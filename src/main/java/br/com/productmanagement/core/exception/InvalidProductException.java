package br.com.productmanagement.core.exception;

public class InvalidProductException extends RuntimeException {

    public InvalidProductException(String message) {
        super("Invalid product: " + message);
    }
}
