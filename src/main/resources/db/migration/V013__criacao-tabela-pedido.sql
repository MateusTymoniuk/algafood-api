CREATE TABLE pedido (
	id BIGINT NOT NULL AUTO_INCREMENT,
	subtotal DECIMAL(10,2) NOT NULL,
	taxa_frete DECIMAL(10,2) NOT NULL,
	valor_total DECIMAL(10,2) NOT NULL,
	status VARCHAR(10) NOT NULL,
	data_criacao DATETIME NOT NULL,
    data_confirmacao DATETIME,
    data_cancelamento DATETIME,
    data_entrega DATETIME,
    endereco_cidade_id BIGINT NOT NULL,
    endereco_cep VARCHAR(9) NOT NULL,
    endereco_logradouro VARCHAR(100) NOT NULL,
    endereco_numero VARCHAR(20) NOT NULL,
    endereco_complemento VARCHAR(60),
    endereco_bairro VARCHAR(60) NOT NULL,

    cliente_id BIGINT NOT NULL,
    restaurante_id BIGINT NOT NULL,
    forma_pagamento_id BIGINT NOT NULL,

	PRIMARY KEY (id),
	CONSTRAINT fk_pedido_cliente_id FOREIGN KEY (cliente_id) REFERENCES usuario (id),
	CONSTRAINT fk_pedido_restaurante_id FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),
	CONSTRAINT fk_pedido_forma_pagamento_id FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id),
    CONSTRAINT fk_pedido_endereco_cidade FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id)
) engine = InnoDB default charset = UTF8MB4;