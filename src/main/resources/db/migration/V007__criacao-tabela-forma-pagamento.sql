CREATE TABLE forma_pagamento (
	id BIGINT NOT NULL AUTO_INCREMENT,
	descricao VARCHAR(60) NOT NULL,
	PRIMARY KEY (id)
) engine = InnoDB default charset = UTF8MB4;