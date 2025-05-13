package br.com.productmanagement.adapter.persistence.repository;

import br.com.productmanagement.adapter.persistence.entity.ProductEntity;
import br.com.productmanagement.core.domain.enums.Category;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    ProductEntity save(final ProductEntity product);
    Optional<ProductEntity> findById(final Long id);
    List<ProductEntity> findAll();
    List<ProductEntity> findAllByCategory(final Category category);
    void update(final ProductEntity product);
    void deleteById(final ProductEntity product);
}
