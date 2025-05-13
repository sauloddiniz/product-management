package br.com.productmanagement.adapter.input;

import br.com.productmanagement.adapter.input.dto.ProductRequestDto;
import br.com.productmanagement.adapter.input.dto.ProductResponseDto;
import br.com.productmanagement.application.ProductUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController implements ProductControllerApi {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @Override
    public ResponseEntity<Void> createProduct(ProductRequestDto productRequestDto) {
        log.info("Iniciando criação de novo produto: {}", productRequestDto.name());
        final Long idProductCreated = productUseCase.createProduct(productRequestDto);
        final URI idProductCreatedUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(idProductCreated)
                .toUri();
        log.info("Produto criado com sucesso. ID: {}", idProductCreated);
        return ResponseEntity.created(idProductCreatedUri).build();
    }

    @Override
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        log.info("Buscando lista de todos os produtos");
        List<ProductResponseDto> products = productUseCase.getProducts();
        log.info("Encontrados {} produtos", products.size());
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<ProductResponseDto> getProductById(Long id) {
        log.info("Buscando produto com ID: {}", id);
        ProductResponseDto product = productUseCase.getProductById(id);
        log.info("Produto encontrado: {}", product.name());
        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity<Void> updateProduct(final ProductRequestDto productRequestDto, final Long id) {
        log.info("Iniciando atualização do produto ID: {} - Nome: {}", id, productRequestDto.name());
        productUseCase.updateProduct(productRequestDto, id);
        log.info("Produto ID: {} atualizado com sucesso", id);
        return ResponseEntity.status(204).build();
    }

    @Override
    public ResponseEntity<Void> deleteProductById(final Long id) {
        log.info("Iniciando exclusão do produto ID: {}", id);
        productUseCase.deleteProductById(id);
        log.info("Produto ID: {} excluído com sucesso", id);
        return ResponseEntity.noContent().build();
    }
}