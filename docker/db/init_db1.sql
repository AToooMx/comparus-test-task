CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE users(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	username VARCHAR(36) NOT NULL UNIQUE,
	name VARCHAR(36) NOT NULL,
	surname VARCHAR(36) NOT NULL
);

INSERT INTO users(username, name, surname) VALUES ('TestUser-01-DB-1_USERNAME', 'TestUser', 'TestUser_Surname');
INSERT INTO users(username, name, surname) VALUES ('TestUser-02-DB-1_USERNAME', 'TestUser01', 'TestUser02_SURNAME');