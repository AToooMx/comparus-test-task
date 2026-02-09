CREATE TABLE tusers(
    id CHAR(36) PRIMARY KEY,
	ldap_login VARCHAR(36) NOT NULL UNIQUE,
	name VARCHAR(36) NOT NULL,
	surname VARCHAR(36) NOT NULL
);

INSERT INTO tusers(id, ldap_login, name, surname) VALUES (UUID(), 'Test-01-DB-3_LDAP', 'Testoviy', 'COMPARUS');
INSERT INTO tusers(id, ldap_login, name, surname) VALUES (UUID(), 'Test-02-DB-3_LDAP', 'Shashok', 'Ruslan');