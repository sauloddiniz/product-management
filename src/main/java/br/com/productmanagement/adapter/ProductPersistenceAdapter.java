package br.com.productmanagement.adapter;

import br.com.productmanagement.adapter.output.ProductPersistencePort;
import br.com.productmanagement.adapter.persistence.repository.ProductDAO;
import br.com.productmanagement.adapter.persistence.repository.ProductDAOImpl;
import br.com.productmanagement.core.domain.Product;
import br.com.productmanagement.core.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductPersistenceAdapter implements ProductPersistencePort {

    private static final Logger log = LoggerFactory.getLogger(ProductPersistenceAdapter.class);

    private final ProductDAO productDao;

    public ProductPersistenceAdapter(ProductDAO productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Override
    public Product findById(Long id) {
        return isContainsOrNotFound(id);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public void update(Product product) {
        isContainsOrNotFound(product.getId());
        productDao.update(product);
    }

    @Override
    public void deleteById(Long id) {
        Product product = isContainsOrNotFound(id);
        productDao.deleteById(product);
    }

    private Product isContainsOrNotFound(Long id) {
        return productDao.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tentativa de busca de produto inexistente. ID: {}", id);
                    return new ProductNotFoundException(id.toString());
                });
    }
}
