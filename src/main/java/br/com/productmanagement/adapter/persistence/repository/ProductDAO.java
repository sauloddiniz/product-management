package br.com.productmanagement.adapter.persistence.repository;

import br.com.productmanagement.adapter.persistence.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    ProductEntity save(final ProductEntity product);
    Optional<ProductEntity> findById(final Long id);
    List<ProductEntity> findAll();
    void update(final ProductEntity product);
    void deleteById(final ProductEntity product);
}
