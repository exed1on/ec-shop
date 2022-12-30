INSERT INTO products(id, price, title)
VALUES (1, 200.0, 'Wine'),
       (2, 50.0, 'Juice'),
       (3, 120.0, 'Beer'),
       (4, 80.0, 'Soda'),
       (5, 10.0, 'Water');

ALTER SEQUENCE product_seq RESTART WITH 6;
