-- DESCRIÇÃO...: tabela que armazena as informações do funcionário.

CREATE TABLE employee(
    id bigint PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    pis VARCHAR(12) NOT NULL
);