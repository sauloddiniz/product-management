package br.com.productmanagement.adapter.input.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductRequestDto(
        @Schema(description = "Nome do produto", example = "Celular")
        @JsonAlias({"name", "nome"})
        String name,

        @Schema(description = "Descrição do produto", example = "Celular top de linha")
        @JsonAlias({"description", "descricao"})
        String description,

        @Schema(description = "Preço do produto", example = "3200.00")
        @JsonAlias({"price", "preco"})
        BigDecimal price,

        @Schema(description = "Quantidade em estoque", example = "10")
        @JsonAlias({"stockQuantity", "quantidade_estoque"})
        Integer stockQuantity,

        @Schema(description = "Categoria do produto. Opções: food, clothes, eletronics, other", example = "other")
        @JsonAlias({"category", "categoria"})
        String category
) {}