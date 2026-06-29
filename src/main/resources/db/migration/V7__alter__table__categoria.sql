ALTER TABLE categoria
    ADD COLUMN usuario_id BIGINT NOT NULL,
    ADD FOREIGN KEY (usuario_id) REFERENCES  usuario(id);