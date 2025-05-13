ALTER TABLE product_schema.product
    ADD COLUMN category VARCHAR(50);

COMMENT ON COLUMN product_schema.product.category IS 'Categoria do produto';
