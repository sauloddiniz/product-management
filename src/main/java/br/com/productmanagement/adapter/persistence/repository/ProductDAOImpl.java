package br.com.productmanagement.adapter.persistence.repository;

import br.com.productmanagement.adapter.persistence.entity.ProductEntity;
import br.com.productmanagement.core.domain.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class ProductDAOImpl implements ProductDAO {

    private static final Logger log = LoggerFactory.getLogger(ProductDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductEntity save(final ProductEntity product) {
        log.debug("Iniciando persistência do produto: {}", product.getName());
        entityManager.persist(product);
        log.info("Produto persistido com sucesso. ID: {}, Nome: {}", product.getId(), product.getName());
        return product;
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        log.debug("Buscando produto no banco de dados. ID: {}", id);
        ProductEntity productEntity = entityManager.find(ProductEntity.class, id);
        if (productEntity == null) {
            log.info("Produto não encontrado. ID: {}", id);
            return Optional.empty();
        }
        log.debug("Produto encontrado. ID: {}, Nome: {}", productEntity.getId(), productEntity.getName());
        return Optional.of(productEntity);
    }

    @Override
    public List<ProductEntity> findAll() {
        log.debug("Iniciando busca de todos os produtos");
        List<ProductEntity> productEntities = entityManager
                .createQuery("SELECT p FROM ProductEntity p", ProductEntity.class)
                .getResultList();
        log.info("Total de produtos encontrados: {}", productEntities.size());
        return productEntities;
    }

    @Override
    public void update(ProductEntity product) {
        log.debug("Iniciando atualização do produto. ID: {}, Nome: {}", product.getId(), product.getName());
        entityManager.merge(product);
        log.info("Produto atualizado com sucesso. ID: {}, Nome: {}", product.getId(), product.getName());
    }

    private ProductEntity updateProduct(ProductEntity productEntity, Product product) {
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        productEntity.setStockQuantity(product.getStockQuantity());
        productEntity.setCategory(product.getCategory());
        productEntity.setUpdatedAt(LocalDateTime.now());
        return productEntity;
    }

    @Override
    public void deleteById(ProductEntity product) {
        entityManager.remove(product);
    }
}