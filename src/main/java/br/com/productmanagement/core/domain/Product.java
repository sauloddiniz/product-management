package br.com.productmanagement.core.domain;

import br.com.productmanagement.core.domain.enums.Category;
import br.com.productmanagement.core.exception.InvalidProductException;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Category category;

    public Product() {
    }

    public Product(String name, String description, BigDecimal price, Integer stockQuantity, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        validProductValue();
        validNameValue();
        validStockValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
