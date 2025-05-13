package br.com.productmanagement.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    public static final String PRODUCTS_CACHE = "products-cache";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(PRODUCTS_CACHE);
    }
}
