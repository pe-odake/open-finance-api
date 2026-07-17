CREATE TABLE contas (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    nome_banco VARCHAR(25) NOT NULL,
    tipo_conta VARCHAR(20) NOT NULL,
    saldo NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    ultima_sincronizacao TIMESTAMP,
    criada_em TIMESTAMP NOT NULL DEFAULT NOW()
)