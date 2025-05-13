package br.com.productmanagement.adapter.output;

import br.com.productmanagement.core.domain.Product;

import java.util.List;

public interface ProductPersistencePort {
    Product save(final Product product);
    Product findById(final Long id);
    List<Product> findAll();
    void update(final Product product);
    void deleteById(final Long id);
}
