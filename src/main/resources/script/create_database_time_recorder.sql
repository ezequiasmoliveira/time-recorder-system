-- DESCRIÇÃO...: criação do banco de registro de ponto.

CREATE DATABASE "time-recorder"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE "time-recorder"
    IS 'Database to control employee''s hours recording';