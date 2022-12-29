INSERT INTO users (id, email, name, password, role)
VALUES (1, 'mail@mail.ru', 'admin', '$2a$10$0Xw/aSw6AXLWf6pgQzbdW.3zY1/S1vNrFMDgl0oJR.EqT7Fq6QEEe', 'ADMIN');

ALTER SEQUENCE user_seq RESTART WITH 2;