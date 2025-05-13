package br.com.productmanagement.adapter.input;

import br.com.productmanagement.adapter.input.dto.ProductRequestDto;
import br.com.productmanagement.adapter.persistence.entity.ProductEntity;
import br.com.productmanagement.application.ProductUseCase;
import br.com.productmanagement.core.domain.enums.Category;
import br.com.productmanagement.core.exception.InvalidCategoryExecption;
import br.com.productmanagement.core.exception.InvalidProductException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EntityManager entityManager;

    @Test
    @DisplayName("Deve criar produto com sucesso e retornar status 201")
    void shouldCreateProductSuccessfully() throws Exception {
        String requestBody = """
                {
                    "name": "Smartphone",
                    "description": "iPhone 13",
                    "price": 5000.00,
                    "stockQuantity": 15,
                    "category": "electronics"
                }
                """;


        when(entityManager.persist(Mockito.any(ProductEntity.class)))
                .thenReturn(new ProductEntity(1L, "Smartphone", "iPhone 13", BigDecimal.valueOf(5000.00), 15, Category.ELECTRONICS))

        mockMvc.perform(post("/api/v1/products")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/products/1"))
                .andReturn();

    }

    @Test
    @DisplayName("Deve retornar erro 400 quando tentar criar produto com preço negativo")
    void shouldReturnBadRequestWhenPriceIsNegative() throws Exception {

        String requestBody = """
            {
                "name": "Smartphone",
                "description": "iPhone 13",
                "price": -5000.00,
                "stockQuantity": 15,
                "category": "electronics"
            }
            """;

        mockMvc.perform(post("/api/v1/products")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid product: price is invalid, must be greater than zero"));
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando tentar criar produto sem nome")
    void shouldReturnBadRequestWhenNameIsEmpty() throws Exception {
        String requestBody = """
            {
                "name": "",
                "description": "iPhone 13",
                "price": 5000.00,
                "stockQuantity": 15,
                "category": "electronics"
            }
            """;

        mockMvc.perform(post("/api/v1/products")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid product: name is invalid, must be filled"));
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando tentar criar produto com quantidade de estoque negativa")
    void shouldReturnBadRequestWhenStockQuantityIsNegative() throws Exception {
        String requestBody = """
            {
                "name": "Smartphone",
                "description": "iPhone 13",
                "price": 5000.00,
                "stockQuantity": -1,
                "category": "electronics"
            }
            """;

        mockMvc.perform(post("/api/v1/products")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid product: stockQuantity is invalid, must be greater than zero"));
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando tentar criar produto com categoria inválida")
    void shouldReturnBadRequestWhenCategoryIsInvalid() throws Exception {
        String requestBody = """
            {
                "name": "Smartphone",
                "description": "iPhone 13",
                "price": 5000.00,
                "stockQuantity": 15,
                "category": "Moveis"
            }
            """;

        mockMvc.perform(post("/api/v1/products")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid category: Moveis"));
    }

    @Test
    @DisplayName("Deve retornar lista com todos os produtos quando não houver filtro de categoria")
    void shouldReturnAllProductsWhenNoCategoryFilter() throws Exception {
        List<ProductEntity> products = List.of(
                new ProductEntity(1L, "Telefone", "Samsung A15", BigDecimal.valueOf(1500.00), 10, Category.ELECTRONICS),
                new ProductEntity(2L, "Notebook", "Dell G15", BigDecimal.valueOf(5000.00), 5, Category.ELECTRONICS),
                new ProductEntity(3L, "Calça", "Calça jeans preta", BigDecimal.valueOf(150.00), 3, Category.CLOTHES)
        );

        when(entityManager.createQuery("SELECT p FROM ProductEntity p", ProductEntity.class))
                .thenReturn(mock(jakarta.persistence.TypedQuery.class));

        when(entityManager.createQuery("SELECT p FROM ProductEntity p", ProductEntity.class)
                .getResultList())
                .thenReturn(products);

        mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Telefone"))
                .andExpect(jsonPath("$[1].nome").value("Notebook"))
                .andExpect(jsonPath("$[2].nome").value("Calça"));
    }

    @Test
    @DisplayName("Deve retornar lista filtrada quando informada a categoria")
    void shouldReturnFilteredProductsWhenCategoryProvided() throws Exception {
        List<ProductEntity> products = List.of(
                new ProductEntity(1L, "Telefone", "Samsung A15", BigDecimal.valueOf(1500.00), 10, Category.ELECTRONICS),
                new ProductEntity(2L, "Notebook", "Dell G15", BigDecimal.valueOf(5000.00), 5, Category.ELECTRONICS)
        );

        TypedQuery<ProductEntity> typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery("SELECT p FROM ProductEntity p WHERE p.category = :categoria", ProductEntity.class))
                .thenReturn(typedQuery);

        when(typedQuery.setParameter("categoria", Category.ELECTRONICS))
                .thenReturn(typedQuery);

        when(typedQuery.getResultList())
                .thenReturn(products);

        mockMvc.perform(get("/api/v1/products")
                        .param("categoria", "electronics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Telefone"))
                .andExpect(jsonPath("$[0].categoria").value("eletronicos"))
                .andExpect(jsonPath("$[1].nome").value("Notebook"))
                .andExpect(jsonPath("$[1].categoria").value("eletronicos"));
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando informada uma categoria inválida")
    void shouldReturnBadRequestWhenInvalidCategoryProvided() throws Exception {
        String invalidCategory = "papelaria";

        mockMvc.perform(get("/api/v1/products")
                .param("categoria", invalidCategory)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid category: " + invalidCategory));
    }

    @Test
    @DisplayName("Deve retornar produto quando buscar por ID existente")
    void shouldReturnProductWhenFindByExistingId() throws Exception {
        Long id = 1L;

        when(entityManager.find(ProductEntity.class, id))
                .thenReturn(new ProductEntity(1L, "Telefone", "Samsung A15", BigDecimal.valueOf(1500.00), 10, Category.ELECTRONICS));

        mockMvc.perform(get("/api/v1/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Telefone"))
                .andExpect(jsonPath("$.descricao").value("Samsung A15"))
                .andExpect(jsonPath("$.preco").value(1500.00))
                .andExpect(jsonPath("$.quantidade_estoque").value(10))
                .andExpect(jsonPath("$.categoria").value("eletronicos"));
    }

    @Test
    @DisplayName("Deve retornar erro 404 quando buscar produto com ID inexistente")
    void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        Long id = 100L;

        when(entityManager.find(ProductEntity.class, id))
                .thenReturn(null);

        mockMvc.perform(get("/api/v1/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Could not find product " + id));
    }

    @Test
    @DisplayName("Deve retornar status 204 quando atualizar produto com sucesso")
    void shouldReturnNoContentWhenUpdateProductSuccessfully() throws Exception {
        Long id = 1L;

        ProductEntity product = new ProductEntity(
                id, "Telefone", "Samsung A15", BigDecimal.valueOf(1500.00), 10, Category.ELECTRONICS);

        String requestBody = """
                {
                    "name": "Telefone Atualizado",
                    "description": "Samsung A15 Plus",
                    "price": 1800.00,
                    "stockQuantity": 15,
                    "category": "electronics"
                }
                """;

        when(entityManager.find(ProductEntity.class, id))
                .thenReturn(product);

        when(entityManager.merge(any(ProductEntity.class)))
                .thenReturn(product);

        mockMvc.perform(put("/api/v1/products/{id}", id)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(entityManager, times(1)).merge(any(ProductEntity.class));
    }

    @Test
    @DisplayName("Deve retornar erro 404 quando tentar atualizar produto inexistente")
    void shouldReturnNotFoundWhenUpdateNonExistingProduct() throws Exception {
        Long id = 100L;
        String requestBody = """
                {
                    "name": "Telefone Atualizado",
                    "description": "Samsung A15 Plus",
                    "price": 1800.00,
                    "stockQuantity": 15,
                    "category": "electronics"
                }
                """;

        when(entityManager.find(ProductEntity.class, id))
                .thenReturn(null);

        mockMvc.perform(put("/api/v1/products/{id}", id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(entityManager, never()).merge(any(ProductEntity.class));
    }

    @Test
    @DisplayName("Deve retornar status 204 quando deletar produto existente")
    void shouldReturnNoContentWhenDeleteExistingProduct() throws Exception {
        Long id = 1L;

        ProductEntity productToDelete = new ProductEntity(
                id, "Telefone", "Samsung A15", BigDecimal.valueOf(1500.00), 10 ,Category.ELECTRONICS);

        when(entityManager.find(ProductEntity.class, id))
                .thenReturn(productToDelete);

        doNothing().when(entityManager).remove(any(ProductEntity.class));

        mockMvc.perform(delete("/api/v1/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(entityManager, times(1)).remove(any(ProductEntity.class));
    }

    @Test
    @DisplayName("Deve retornar status 404 quando tentar deletar produto inexistente")
    void shouldReturnNotFoundWhenDeleteNonExistingProduct() throws Exception {
        Long id = 100L;

        when(entityManager.find(ProductEntity.class, id))
                .thenReturn(null);

        mockMvc.perform(delete("/api/v1/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Could not find product " + id));

        verify(entityManager, never()).remove(any(ProductEntity.class));
    }
}