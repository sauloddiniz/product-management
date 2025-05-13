package br.com.productmanagement.application.mapper;

import br.com.productmanagement.adapter.input.dto.ProductRequestDto;
import br.com.productmanagement.adapter.input.dto.ProductResponseDto;
import br.com.productmanagement.core.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductMapper {

    private static final Logger log = LoggerFactory.getLogger(ProductMapper.class);

    private ProductMapper() {}

    public static Product toDomain(ProductRequestDto product) {
        try {
            return new Product(product.name(), product.description(), product.price(), product.stockQuantity(), product.category());
        } catch (Exception exception) {
            log.error("Erro to create product: {}", exception.getMessage());
            throw exception;
        }
    }

    public static ProductResponseDto toResponse(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity(), product.getCategory().getDescriptionPt());
    }
}
