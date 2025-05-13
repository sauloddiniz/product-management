package br.com.productmanagement.adapter.input.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductResponseDto(
        @Schema(description = "identificador unico do produto")
        Long id,

        @Schema(description = "Nome do produto")
        String name,

        @Schema(description = "Descrição do produto")
        String description,

        @Schema(description = "Preço do produto")
        BigDecimal price,

        @Schema(description = "Quantidade em estoque")
        Integer stockQuantity,

        @Schema(
                description = "Categoria do produto"
        )
        String category
) {
}
