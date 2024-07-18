INSERT INTO catalog (product_id, product_name, stock, unit_price)
VALUES ('CATALOG_001', '감자', 100, 1500)
    ON DUPLICATE KEY UPDATE product_name = VALUES(product_name), stock = VALUES(stock), unit_price = VALUES(unit_price);

INSERT INTO catalog (product_id, product_name, stock, unit_price)
VALUES ('CATALOG_002', '고구마', 50, 1000)
    ON DUPLICATE KEY UPDATE product_name = VALUES(product_name), stock = VALUES(stock), unit_price = VALUES(unit_price);

INSERT INTO catalog (product_id, product_name, stock, unit_price)
VALUES ('CATALOG_003', '당근', 150, 2000)
    ON DUPLICATE KEY UPDATE product_name = VALUES(product_name), stock = VALUES(stock), unit_price = VALUES(unit_price);
