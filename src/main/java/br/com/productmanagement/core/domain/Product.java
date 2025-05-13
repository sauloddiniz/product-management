package br.com.productmanagement.core.domain;

import br.com.productmanagement.core.domain.enums.Category;
import br.com.productmanagement.core.exception.InvalidProductException;

import java.math.BigDecimal;

import static br.com.productmanagement.core.domain.enums.Category.fromDescription;

public class Product {

    private Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer stockQuantity;
    private final Category category;

    public Product(String name, String description, BigDecimal price, Integer stockQuantity, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = fromDescription(category);
        validProductValue();
        validNameValue();
        validStockValue();
    }

    public Product(Long id, String name, String description, BigDecimal price, Integer stockQuantity, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public Category getCategory() {
        return category;
    }

    private void validProductValue() {
        if (this.price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidProductException("price is invalid, must be greater than zero");
        }
    }

    private void validNameValue() {
        if (this.name == null || this.name.isBlank()) {
            throw new InvalidProductException("name is invalid, must be filled");
        }
    }

    private void validStockValue() {
        if (this.stockQuantity == null || this.stockQuantity < 0) {
            throw new InvalidProductException("stockQuantity is invalid, must be greater than zero");
        }
    }

}
