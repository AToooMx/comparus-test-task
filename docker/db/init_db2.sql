CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE customers(
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	login VARCHAR(36) NOT NULL UNIQUE,
	first_name VARCHAR(36) NOT NULL,
	last_name VARCHAR(36) NOT NULL
);

INSERT INTO customers(login, first_name, last_name) VALUES ('TestCustomer-01-DB-2_login', 'TestUser', 'TestUser_lastName');
INSERT INTO customers(login, first_name, last_name) VALUES ('TestCustomer-02-DB-2_login', 'TestUser03', 'Testovich');