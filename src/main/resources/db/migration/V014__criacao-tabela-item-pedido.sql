CREATE TABLE item_pedido (
	id BIGINT NOT NULL AUTO_INCREMENT,
	quantidade SMALLINT(6) NOT NULL,
	preco_unitario DECIMAL(10,2) NOT NULL,
	preco_total DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100) NOT NULL,
	pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,

	PRIMARY KEY (id),
	unique key uk_item_pedido_produto (pedido_id, produto_id),

	CONSTRAINT fk_item_pedido_pedido_id FOREIGN KEY (pedido_id) REFERENCES pedido (id),
	CONSTRAINT fk_item_pedido_produto_id FOREIGN KEY (produto_id) REFERENCES produto (id)
) engine = InnoDB default charset = UTF8MB4;