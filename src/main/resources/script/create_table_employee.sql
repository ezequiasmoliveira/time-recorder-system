-- DESCRI��O...: tabela que armazena as informa��es do funcion�rio.

CREATE TABLE employee(
    id bigint PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    pis VARCHAR(12) NOT NULL
);