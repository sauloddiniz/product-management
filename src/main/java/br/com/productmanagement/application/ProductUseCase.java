package br.com.productmanagement.application;

import br.com.productmanagement.adapter.input.dto.ProductRequestDto;
import br.com.productmanagement.adapter.input.dto.ProductResponseDto;

import java.util.List;

public interface ProductUseCase {
    Long createProduct(final ProductRequestDto productRequestDto);
    void updateProduct(final ProductRequestDto productRequestDto, final Long id);
    ProductResponseDto getProductById(final Long id);
    List<ProductResponseDto> getProducts(final String categoria);
    void deleteProductById(final Long id);
}
