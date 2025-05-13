package br.com.productmanagement.adapter;

import br.com.productmanagement.adapter.output.ProductPersistencePort;
import br.com.productmanagement.adapter.persistence.entity.ProductEntity;
import br.com.productmanagement.adapter.persistence.repository.ProductDAO;
import br.com.productmanagement.adapter.persistence.repository.mapper.ProductMapper;
import br.com.productmanagement.core.domain.Product;
import br.com.productmanagement.core.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static br.com.productmanagement.adapter.persistence.repository.mapper.ProductMapper.toDomain;
import static br.com.productmanagement.adapter.persistence.repository.mapper.ProductMapper.toEntity;

@Component
public class ProductPersistenceAdapter implements ProductPersistencePort {

    private static final Logger log = LoggerFactory.getLogger(ProductPersistenceAdapter.class);

    private final ProductDAO productDao;

    public ProductPersistenceAdapter(ProductDAO productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product save(final Product product) {
        final ProductEntity productEntity = productDao.save(toEntity(product));
        return toDomain(productEntity);
    }

    @Override
    public Product findById(final Long id) {
        return toDomain(isContainsOrNotFound(id));
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll().stream().map(ProductMapper::toDomain).toList();
    }

    @Override
    public void update(final Product product, final Long id) {
        final ProductEntity productSaved = isContainsOrNotFound(id);
        productSaved.setName(product.getName());
        productSaved.setDescription(product.getDescription());
        productSaved.setPrice(product.getPrice());
        productSaved.setStockQuantity(product.getStockQuantity());
        productSaved.setCategory(product.getCategory());
        productSaved.setUpdatedAt(LocalDateTime.now());
        productDao.update(productSaved);
    }

    @Override
    public void deleteById(Long id) {
        final ProductEntity product = isContainsOrNotFound(id);
        productDao.deleteById(product);
    }

    private ProductEntity isContainsOrNotFound(final Long id) {
        return productDao.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tentativa de busca de produto inexistente. ID: {}", id);
                    return new ProductNotFoundException(id.toString());
                });
    }

}
