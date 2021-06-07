CREATE TABLE grupo_permissao (
	grupo_id BIGINT NOT NULL,
	permissao_id BIGINT NOT NULL,
	PRIMARY KEY (grupo_id, permissao_id),
	CONSTRAINT fk_grupo_permissao_grupo_id FOREIGN KEY (grupo_id) REFERENCES grupo (id),
	CONSTRAINT fk_grupo_permissao_permissao_id FOREIGN KEY (permissao_id) REFERENCES permissao (id)
) engine = InnoDB default charset = UTF8MB4;