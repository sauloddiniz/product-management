package br.com.productmanagement.adapter.persistence.repository;

import br.com.productmanagement.adapter.persistence.entity.ProductEntity;
import br.com.productmanagement.adapter.persistence.repository.mapper.ProductMapper;
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

import static br.com.productmanagement.adapter.persistence.repository.mapper.ProductMapper.toDomain;
import static br.com.productmanagement.adapter.persistence.repository.mapper.ProductMapper.toEntity;

@Component
@Transactional
public class ProductDAOImpl implements ProductDAO {

    private static final Logger log = LoggerFactory.getLogger(ProductDAOImpl.class);
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product save(final Product product) {
        log.debug("Iniciando persistência do produto: {}", product.getName());
        ProductEntity productEntity = toEntity(product);
        entityManager.persist(productEntity);
        log.info("Produto persistido com sucesso. ID: {}, Nome: {}", productEntity.getId(), productEntity.getName());
        return toDomain(productEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        log.debug("Buscando produto no banco de dados. ID: {}", id);
        ProductEntity productEntity = entityManager.find(ProductEntity.class, id);
        if (productEntity == null) {
            log.info("Produto não encontrado. ID: {}", id);
            return Optional.empty();
        }
        log.debug("Produto encontrado. ID: {}, Nome: {}", productEntity.getId(), productEntity.getName());
        return Optional.ofNullable(toDomain(productEntity));
    }

    @Override
    public List<Product> findAll() {
        log.debug("Iniciando busca de todos os produtos");
        List<ProductEntity> productEntities = entityManager
                .createQuery("SELECT p FROM ProductEntity p", ProductEntity.class)
                .getResultList();
        log.info("Total de produtos encontrados: {}", productEntities.size());
        return productEntities
                .stream()
                .map(ProductMapper::toDomain)
                .toList();
    }

    @Override
    public void update(Product product) {
        log.debug("Iniciando atualização do produto. ID: {}, Nome: {}", product.getId(), product.getName());
        ProductEntity productEntity = toEntity(product);
        productEntity.setUpdatedAt(LocalDateTime.now());
        entityManager.merge(productEntity);
        log.info("Produto atualizado com sucesso. ID: {}, Nome: {}", product.getId(), product.getName());
    }

    @Override
    public void deleteById(Product product) {
        log.debug("Iniciando exclusão do produto. ID: {}", product.getId());
        ProductEntity productEntity = toEntity(product);
        if (productEntity != null) {
            entityManager.remove(productEntity);
            log.info("Produto excluído com sucesso. ID: {}", product.getId());
        } else {
            log.warn("Tentativa de exclusão de produto inexistente. ID: {}", product.getId());
        }
    }
}