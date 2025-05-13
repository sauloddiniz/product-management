package br.com.productmanagement.application;

import br.com.productmanagement.adapter.input.dto.ProductRequestDto;
import br.com.productmanagement.adapter.input.dto.ProductResponseDto;
import br.com.productmanagement.adapter.output.ProductPersistencePort;
import br.com.productmanagement.application.mapper.ProductMapper;
import br.com.productmanagement.core.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.productmanagement.application.mapper.ProductMapper.toDomain;
import static br.com.productmanagement.application.mapper.ProductMapper.toResponse;

@Service
public class ProductUseCaseImpl implements ProductUseCase {

    private final ProductPersistencePort productPersistencePort;

    public ProductUseCaseImpl(ProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public Long createProduct(final ProductRequestDto productRequestDto) {
        Product product = toDomain(productRequestDto);
        product = productPersistencePort.save(product);
        return product.getId();
    }

    @Override
    public void updateProduct(final ProductRequestDto productRequestDto, final Long id) {
        Product product = toDomain(productRequestDto);
        productPersistencePort.update(product, id);
    }

    @Override
    public ProductResponseDto getProductById(final Long id) {
        Product product = productPersistencePort.findById(id);
        return toResponse(product);
    }

    @Override
    public List<ProductResponseDto> getProducts() {
        List<Product> products = productPersistencePort.findAll();
        return products
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteProductById(final Long id) {
        productPersistencePort.deleteById(id);
    }
}
