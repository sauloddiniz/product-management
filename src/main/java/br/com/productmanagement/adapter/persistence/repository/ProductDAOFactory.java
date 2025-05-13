package br.com.productmanagement.adapter.persistence.repository;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ProductDAOFactory {

    private final EntityManager entityManager;

    public ProductDAOFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public ProductDAO productDAO() {
        return new ProductDaoImpl(entityManager);
    }

}
