CREATE SCHEMA IF NOT EXISTS product_schema;

CREATE TABLE product_schema.product (
                                        id BIGSERIAL PRIMARY KEY,
                                        name VARCHAR(100) NOT NULL,
                                        description TEXT,
                                        price DECIMAL(10,2) NOT NULL,
                                        stock_quantity INTEGER NOT NULL,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_product_name ON product_schema.product(name);

ALTER TABLE product_schema.product ADD CONSTRAINT check_price_positive CHECK (price >= 0);
ALTER TABLE product_schema.product ADD CONSTRAINT check_stock_quantity_positive CHECK (stock_quantity >= 0);

COMMENT ON TABLE product_schema.product                 IS 'Tabela para armazenar informações dos produtos';
COMMENT ON COLUMN product_schema.product.id             IS 'Identificador único do produto';
COMMENT ON COLUMN product_schema.product.name           IS 'Nome do produto';
COMMENT ON COLUMN product_schema.product.description    IS 'Descrição detalhada do produto';
COMMENT ON COLUMN product_schema.product.price          IS 'Preço do produto';
COMMENT ON COLUMN product_schema.product.stock_quantity IS 'Quantidade em estoque do produto';
COMMENT ON COLUMN product_schema.product.created_at     IS 'Data e hora de criação do registro';
COMMENT ON COLUMN product_schema.product.updated_at     IS 'Data e hora da última atualização do registro';