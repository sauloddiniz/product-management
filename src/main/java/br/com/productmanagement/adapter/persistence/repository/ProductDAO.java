package br.com.productmanagement.adapter.persistence.repository;

import br.com.productmanagement.core.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    Product save(final Product product);
    Optional<Product> findById(final Long id);
    List<Product> findAll();
    void update(final Product product);
    void deleteById(final Product product);
}
