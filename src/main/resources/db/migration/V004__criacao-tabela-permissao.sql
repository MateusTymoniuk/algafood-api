CREATE TABLE permissao (
	id BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(60) NOT NULL,
	descricao VARCHAR(100) NOT NULL,
	PRIMARY KEY (id)
) engine = InnoDB default charset = UTF8MB4;