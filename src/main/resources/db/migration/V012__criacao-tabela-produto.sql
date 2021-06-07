CREATE TABLE produto (
	id BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(80) NOT NULL,
	descricao VARCHAR(100) NOT NULL,
	preco DECIMAL(10,2) NOT NULL,
	ativo tinyint(1) NOT NULL,
	restaurante_id BIGINT NOT NULL,

	PRIMARY KEY (id),
	CONSTRAINT fk_produto_restaurante_id FOREIGN KEY (restaurante_id) REFERENCES restaurante (id)
) engine = InnoDB default charset = UTF8MB4;