CREATE TABLE restaurante (
	id BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(80) NOT NULL,
	taxa_frete DECIMAL(10,2) NOT NULL,
	data_cadastro DATETIME NOT NULL,
	data_atualizacao DATETIME NOT NULL,
	cozinha_id BIGINT NOT NULL,

	endereco_cidade_id BIGINT,
    endereco_cep VARCHAR(9),
    endereco_logradouro VARCHAR(100),
    endereco_numero VARCHAR(20),
    endereco_complemento VARCHAR(60),
    endereco_bairro VARCHAR(60),

	PRIMARY KEY (id),
	CONSTRAINT fk_restaurante_cozinha_id FOREIGN KEY (cozinha_id) REFERENCES cozinha (id),
	CONSTRAINT fk_restaurante_cidade_id FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id)
) engine = InnoDB default charset = UTF8MB4;