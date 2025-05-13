package br.com.productmanagement.application.mapper;

import br.com.productmanagement.adapter.input.dto.ProductRequestDto;
import br.com.productmanagement.adapter.input.dto.ProductResponseDto;
import br.com.productmanagement.core.domain.Product;

public class ProductMapper {

    private ProductMapper() {}

    public static Product toDomain(ProductRequestDto product) {
        return new Product(product.name(), product.description(), product.price(), product.stockQuantity(), product.category());
    }

    public static ProductResponseDto toResponse(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity(), product.getCategory());
    }
}
