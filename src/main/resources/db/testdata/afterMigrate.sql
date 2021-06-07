
set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;

INSERT INTO cozinha (id, nome) VALUES (1, 'Brasileira');
INSERT INTO cozinha (id, nome) VALUES (2, 'Australiana');
INSERT INTO cozinha (id, nome) VALUES (3, 'Argentina');
INSERT INTO cozinha (id, nome) VALUES (4, 'Italiana');

INSERT INTO estado (id, nome) VALUES (1, 'Distrito Federal');
INSERT INTO estado (id, nome) VALUES (2, 'Goiás');

INSERT INTO cidade (nome, estado_id) VALUES ('Águas Claras', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Goiânia', 2);

INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) VALUES (1, 'Cumarin', 10, 1, utc_timestamp, utc_timestamp, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES (2, 'Outback', 9.50, 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp);

INSERT INTO forma_pagamento (id, descricao) VALUES (1, "Dinheiro");
INSERT INTO forma_pagamento (id, descricao) VALUES (2, "Cartão de débito");
INSERT INTO forma_pagamento (id, descricao) VALUES (3, "Cartão de crédito");

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id)
VALUES (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);