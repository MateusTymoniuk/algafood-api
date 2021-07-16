
SET foreign_key_checks = 0;

DELETE FROM cidade;
DELETE FROM cozinha;
DELETE FROM estado;
DELETE FROM forma_pagamento;
DELETE FROM grupo_permissao;
DELETE FROM grupo;
DELETE FROM permissao;
DELETE FROM produto;
DELETE FROM restaurante;
DELETE FROM restaurante_forma_pagamento;
DELETE FROM usuario;
DELETE FROM usuario_grupo;
DELETE FROM restaurante_usuario_responsavel;
DELETE FROM pedido;
DELETE FROM item_pedido;

SET foreign_key_checks = 1;

ALTER TABLE cidade auto_increment = 1;
ALTER TABLE cozinha auto_increment = 1;
ALTER TABLE estado auto_increment = 1;
ALTER TABLE forma_pagamento auto_increment = 1;
ALTER TABLE grupo auto_increment = 1;
ALTER TABLE permissao auto_increment = 1;
ALTER TABLE produto auto_increment = 1;
ALTER TABLE restaurante auto_increment = 1;
ALTER TABLE usuario auto_increment = 1;
ALTER TABLE pedido auto_increment = 1;
ALTER TABLE item_pedido auto_increment = 1;

INSERT INTO cozinha (id, nome) VALUES (1, 'Brasileira');
INSERT INTO cozinha (id, nome) VALUES (2, 'Australiana');
INSERT INTO cozinha (id, nome) VALUES (3, 'Argentina');
INSERT INTO cozinha (id, nome) VALUES (4, 'Italiana');

INSERT INTO estado (id, nome) VALUES (1, 'Distrito Federal');
INSERT INTO estado (id, nome) VALUES (2, 'Goiás');

INSERT INTO cidade (nome, estado_id) VALUES ('Águas Claras', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Goiânia', 2);

INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) VALUES (1, 'Cumarin', 10, 1, utc_timestamp, utc_timestamp, true, false, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (2, 'Outback', 9.50, 1, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, false);

INSERT INTO forma_pagamento (id, descricao) VALUES (1, "Dinheiro");
INSERT INTO forma_pagamento (id, descricao) VALUES (2, "Cartão de débito");
INSERT INTO forma_pagamento (id, descricao) VALUES (3, "Cartão de crédito");

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id)
VALUES (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

INSERT INTO grupo (id, nome)
VALUES (1, "Gerente"), (2, "Vendedor");

INSERT INTO permissao (id, nome, descricao) VALUES (1, "VISUALIZAR_PRODUTOS", "Permissão de visualizar produtos");
INSERT INTO permissao (id, nome, descricao) VALUES (2, "INCLUIR_PRODUTOS", "Permissão de incluir produtos");
INSERT INTO permissao (id, nome, descricao) VALUES (3, "DELETAR_PRODUTOS", "Permissão de deletar produtos");

INSERT INTO grupo_permissao (grupo_id, permissao_id)
VALUES (1, 1), (1, 2), (1, 3);

INSERT INTO usuario
(id, nome, email, senha, data_cadastro)
VALUES
(1, "João", "joao@email.com", "123", utc_timestamp),
(2, "Manoel", "manoel@email.com", "123", utc_timestamp);

INSERT INTO usuario_grupo
(usuario_id, grupo_id)
VALUES (1, 1);

INSERT INTO produto
(nome, descricao, preco, ativo, restaurante_id)
VALUES('Cheeseburguer', 'Hambúrguer com queijo', 30.0, 1, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

INSERT INTO restaurante_usuario_responsavel
(restaurante_id, usuario_id)
VALUES (1, 1), (3, 1);

INSERT INTO pedido (id, codigo, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES (1, 'd043d4b5-0983-4699-9e5c-f33ca05a7a7d', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'CRIADO', utc_timestamp, 298.90, 10, 308.90);

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
VALUES (1, 1, 1, 1, 78.9, 78.9, null);

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
VALUES (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');


INSERT INTO pedido (id, codigo, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
        endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
        status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES (2, 'e94e60f2-f92f-4ad7-bb2f-dc4942fb7acf', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CRIADO', utc_timestamp, 79, 0, 79);

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
VALUES (3, 2, 6, 1, 79, 79, 'Ao ponto');