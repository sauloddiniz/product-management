package br.com.productmanagement.adapter.input;

import br.com.productmanagement.adapter.input.dto.ProductRequestDto;
import br.com.productmanagement.adapter.input.dto.ProductResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products", description = "Operações de CRUD para produtos")
@RequestMapping("/api/v1/products")
public interface ProductControllerApi {

    @Operation(
        summary = "Cria um novo produto",
        description = "Adiciona um novo produto ao banco de dados",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do produto para cadastro",
            required = true,
            content = @Content(
                schema = @Schema(implementation = ProductRequestDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
        }
    )
    @PostMapping
    ResponseEntity<Void> createProduct(
        @RequestBody final ProductRequestDto productRequestDto
    );

    @Operation(
        summary = "Lista todos os produtos",
        description = "Retorna todos os produtos cadastrados",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de produtos",
                content = @Content(
                    array = @ArraySchema(
                        schema = @Schema(implementation = ProductResponseDto.class)
                    )
                )
            )
        }
    )
    @GetMapping
    ResponseEntity<List<ProductResponseDto>> getAllProducts();

    @Operation(
        summary = "Obtém um produto por ID",
        description = "Retorna os detalhes do produto correspondente ao ID informado",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Produto encontrado",
                content = @Content(
                    schema = @Schema(implementation = ProductResponseDto.class)
                )
            ),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
        }
    )
    @GetMapping("/{id}")
    ResponseEntity<ProductResponseDto> getProductById(
        @Parameter(description = "ID do produto", required = true)
        @PathVariable final Long id
    );

    @Operation(
        summary = "Atualiza um produto existente",
        description = "Atualiza os dados de um produto cadastrado",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados atualizados do produto",
            required = true,
            content = @Content(
                schema = @Schema(implementation = ProductRequestDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "204", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
        }
    )
    @PutMapping("/{id}")
    ResponseEntity<Void> updateProduct(
        @RequestBody final ProductRequestDto productRequestDto,
        @Parameter(description = "ID do produto", required = true)
        @PathVariable final Long id
    );

    @Operation(
        summary = "Remove um produto",
        description = "Deleta um produto do banco de dados pelo ID",
        responses = {
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
        }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProductById(
        @Parameter(description = "ID do produto", required = true)
        @PathVariable final Long id
    );
}