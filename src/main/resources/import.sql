SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM cozinha;
DELETE FROM estado;
DELETE FROM cidade;
DELETE FROM forma_pagamento;
DELETE FROM grupo;
DELETE FROM grupo_permissao;
DELETE FROM permissao;
DELETE FROM restaurante;
DELETE FROM restaurante_forma_pagamento;
DELETE FROM usuario;
DELETE FROM usuario_grupo;
DELETE FROM produto;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO cozinha (id, nome) VALUES (1, 'Brasileira');
INSERT INTO cozinha (id, nome) VALUES (2, 'Australiana');
INSERT INTO cozinha (id, nome) VALUES (3, 'Argentina');
INSERT INTO cozinha (id, nome) VALUES (4, 'Italiana');

INSERT INTO estado (nome) VALUES (1, 'Distrito Federal');
INSERT INTO estado (nome) VALUES (2, 'Goiás');

INSERT INTO cidade (nome, estado_id) VALUES ('Águas Claras', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Goiânia', 2);

INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_criacao, data_atualizacao) VALUES (1, 'Cumarin', 5.0, 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_criacao, data_atualizacao) VALUES (2, 'Outback', 10.0, 2, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp);

INSERT INTO forma_pagamento (id, descricao) VALUES (1, "Dinheiro");
INSERT INTO forma_pagamento (id, descricao) VALUES (2, "Cartão de débito");
INSERT INTO forma_pagamento (id, descricao) VALUES (3, "Cartão de crédito");

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id)
VALUES (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);
