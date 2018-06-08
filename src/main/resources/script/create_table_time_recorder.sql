-- DESCRIÇÃO...: tabela que armazena as informações da batida do ponto.

CREATE TABLE time_recorder(
    id BIGINT PRIMARY KEY NOT NULL,
    id_worked BIGINT NOT NULL,
    momment TIMESTAMP NOT NULL,
	FOREIGN KEY (id_worked) REFERENCES worked (id)
);