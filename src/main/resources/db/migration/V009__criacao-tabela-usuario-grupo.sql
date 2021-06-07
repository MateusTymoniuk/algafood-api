CREATE TABLE usuario_grupo (
	usuario_id BIGINT NOT NULL,
	grupo_id BIGINT NOT NULL,

	PRIMARY KEY (usuario_id, grupo_id),
	CONSTRAINT fk_usuario_grupo_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuario (id),
	CONSTRAINT fk_usuario_grupo_grupo_id FOREIGN KEY (grupo_id) REFERENCES grupo (id)
) engine = InnoDB default charset = UTF8MB4;