-- DESCRIÇÃO...: tabela que armazena o dia trabalhado.

CREATE TABLE worked(
    id BIGINT PRIMARY KEY NOT NULL,
    id_employee BIGINT NOT NULL,
    momment DATE NOT NULL,
	FOREIGN KEY (id_employee) REFERENCES employee (id)
);