CREATE TABLE transacao (
    id BIGSERIAL PRIMARY KEY,
    conta_id BIGINT NOT NULL REFERENCES contas(id) ON DELETE CASCADE,
    descricao VARCHAR(255) NOT NULL,
    valor NUMERIC(15, 2) NOT NULL,
    categoria VARCHAR(30) NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    data_transacao TIMESTAMP NOT NULL
);