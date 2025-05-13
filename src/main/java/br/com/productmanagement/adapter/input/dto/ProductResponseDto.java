package br.com.productmanagement.adapter.input.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductResponseDto(
        @Schema(description = "identificador unico do produto")
        Long id,

        @Schema(description = "Nome do produto")
        @JsonProperty("nome")
        String name,

        @Schema(description = "Descrição do produto")
        @JsonProperty("descricao")
        String description,

        @Schema(description = "Preço do produto")
        @JsonProperty("preco")
        BigDecimal price,

        @Schema(description = "Quantidade em estoque")
        @JsonProperty("quantidade_estoque")
        Integer stockQuantity,

        @Schema(description = "Categoria do produto")
        @JsonProperty("categoria")
        String category
) {
}
