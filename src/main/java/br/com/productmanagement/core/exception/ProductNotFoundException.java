package br.com.productmanagement.core.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String productId) {
        super("Could not find product " + productId);
    }
}
