package br.com.productmanagement.adapter.input.dto;

import br.com.productmanagement.core.domain.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductRequestDto(
        @Schema(description = "Nome do produto", example = "Celular")
        String name,

        @Schema(description = "Descrição do produto", example = "Celular top de linha")
        String description,

        @Schema(description = "Preço do produto", example = "3200.00")
        BigDecimal price,

        @Schema(description = "Quantidade em estoque", example = "10")
        Integer stockQuantity,

        @Schema(
            description = "Categoria do produto. Opções: FOOD, CLOTHES, ELECTRONICS, OTHER",
            example = "OTHER"
        )
        Category category
) {}