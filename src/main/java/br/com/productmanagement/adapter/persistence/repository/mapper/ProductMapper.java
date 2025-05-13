package br.com.productmanagement.adapter.persistence.repository.mapper;

import br.com.productmanagement.adapter.persistence.entity.ProductEntity;
import br.com.productmanagement.core.domain.Product;

public class ProductMapper {

    private ProductMapper() {
    }

    public static ProductEntity toEntity(final Product product) {
        if (product == null) {
            return null;
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        productEntity.setStockQuantity(product.getStockQuantity());
        productEntity.setCategory(product.getCategory());
        return productEntity;
    }

    public static Product toDomain(final ProductEntity productEntity) {
        if (productEntity == null) {
            return null;
        }
        return new Product(productEntity.getId(), productEntity.getName(),
                productEntity.getDescription(), productEntity.getPrice(),
                productEntity.getStockQuantity(), productEntity.getCategory());
    }
}
