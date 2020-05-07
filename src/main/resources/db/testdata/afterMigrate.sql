set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from item_pedido;
delete from pedido;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from restaurante_usuario_responsavel;
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
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;

-- Estado

insert into estado (nome, data_criacao, data_atualizacao) values ('Acre', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Alagoas', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Amazonas', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Amapá', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Bahia', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Ceará', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Distrito Federal', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Espírito Santo', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Goiás', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Maranhão', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Minas Gerais', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Mato Grosso', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Mato Grosso do Sul', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Pará', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Paraíba', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Paraná', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Pernambuco', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Piauí', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Rio de Janeiro', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Rio Grande do Norte', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Rio Grande do Sul', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Rondônia', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Roraima', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Santa Catarina', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Sergipe', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('São Paulo', utc_timestamp, utc_timestamp);
insert into estado (nome, data_criacao, data_atualizacao) values ('Tocantins', utc_timestamp, utc_timestamp);

-- Cidade

insert into cidade (data_atualizacao, data_criacao, nome, estado_id)
values (utc_timestamp, utc_timestamp, 'Curitiba', 16);
insert into cidade (data_atualizacao, data_criacao, nome, estado_id)
values (utc_timestamp, utc_timestamp, 'Maceió', 2);
insert into cidade (data_atualizacao, data_criacao, nome, estado_id)
values (utc_timestamp, utc_timestamp, 'Palmas', 27);
insert into cidade (data_atualizacao, data_criacao, nome, estado_id)
values (utc_timestamp, utc_timestamp, 'São Paulo', 26);
insert into cidade (data_atualizacao, data_criacao, nome, estado_id)
values (utc_timestamp, utc_timestamp, 'São Luís', 10);

-- Cozinha

insert into cozinha (data_atualizacao, data_criacao, nome) values (utc_timestamp, utc_timestamp, 'Francesa');
insert into cozinha (data_atualizacao, data_criacao, nome) values (utc_timestamp, utc_timestamp, 'Italiana');
insert into cozinha (data_atualizacao, data_criacao, nome) values (utc_timestamp, utc_timestamp, 'Brasileira');
insert into cozinha (data_atualizacao, data_criacao, nome) values (utc_timestamp, utc_timestamp, 'Japonesa');
insert into cozinha (data_atualizacao, data_criacao, nome) values (utc_timestamp, utc_timestamp, 'Alemã');
insert into cozinha (data_atualizacao, data_criacao, nome) values (utc_timestamp, utc_timestamp, 'Tailandesa');

-- Grupo

insert into grupo (id, nome) values (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretária'), (4, 'Cadastrador');

-- Forma de Pagamento

insert into forma_pagamento (id, data_atualizacao, data_criacao, descricao)
values (1, utc_timestamp, utc_timestamp, 'Dinheiro');
insert into forma_pagamento (id, data_atualizacao, data_criacao, descricao)
values (2, utc_timestamp, utc_timestamp, 'Cartão de Crédito');
insert into forma_pagamento (id, data_atualizacao, data_criacao, descricao)
values (3, utc_timestamp, utc_timestamp, 'Cartão de Débito');

-- Restaurante

insert into restaurante
(id, data_atualizacao, data_criacao, aberto, ativo, endereco_bairro, endereco_cep, endereco_complemento,
 endereco_logradouro, endereco_numero, nome, taxa_frete, id_cozinha, endereco_cidade_id)
values (1, utc_timestamp, utc_timestamp, 0, 1, 'Calhau', '65077-357', 'L-02', 'Av. dos Holandeses', '200', 'Thai',
7.50, 6, 5);

-- Produto

INSERT INTO produto
(id, data_atualizacao, data_criacao, ativo, descricao, nome, preco, restaurante_id)
VALUES(1, utc_timestamp, utc_timestamp, 0, 'Sauté de Frango com Castanhas de Caju', 'Khai Phad Met Ma Muang', 58.00, 1);
INSERT INTO produto
(id, data_atualizacao, data_criacao, ativo, descricao, nome, preco, restaurante_id)
VALUES(2, utc_timestamp, utc_timestamp, 0, 'Filet de Peixe com Sauce de Leite de Coco e Cúrcuma com Pimentas Frescas e Castanha d´Água. / "Chef''s Favorite"', 'Pla Tom Kati Haew', 85.00, 1);

-- Usuário

INSERT INTO usuario
(id, data_cadastro, email, nome, senha)
VALUES(1, utc_timestamp, 'augusto.jota@algafood.com', 'Augusto Jota', '$2y$12$uHL.DZL6M3WMwULAofH5j.VuBQVZGcklxJ6CsaeVrooOYHrMTmnca');
INSERT INTO usuario
(id, data_cadastro, email, nome, senha)
VALUES(2, utc_timestamp, 'arreis@pedidos.com.br', 'Alessandra Reis', '$2y$12$uHL.DZL6M3WMwULAofH5j.VuBQVZGcklxJ6CsaeVrooOYHrMTmnca');
INSERT INTO usuario
(id, data_cadastro, email, nome, senha)
VALUES(3, utc_timestamp, 'test.algafood@gmail.com', 'Donnie Darko', '$2y$12$uHL.DZL6M3WMwULAofH5j.VuBQVZGcklxJ6CsaeVrooOYHrMTmnca');
INSERT INTO usuario
(id, data_cadastro, email, nome, senha)
VALUES(4, utc_timestamp, 'mtu@domain.com', 'Maria Tereza Ubiraci', '$2y$12$uHL.DZL6M3WMwULAofH5j.VuBQVZGcklxJ6CsaeVrooOYHrMTmnca');

-- Pedido

INSERT INTO pedido
(id, data_atualizacao, data_criacao, codigo, data_cancelamento, data_confirmacao, data_entrega, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, status, subtotal, taxa_frete, valor_total, cliente_id, endereco_cidade_id, forma_pagamento_id, restaurante_id)
VALUES(1, '2020-04-11 00:03:25', '2020-04-11 00:03:25', '05d2b496-9751-4d4d-9875-71444271fc25', NULL, NULL, NULL, 'Cohafuma', '65477-468', NULL, 'Avenida A', '5B', 'CRIADO', 116.00, 7.50, 123.50, 3, 5, 1, 1);

INSERT INTO item_pedido
(id, data_atualizacao, data_criacao, observacao, preco_total, preco_unitario, quantidade, pedido_id, produto_id)
VALUES(1, '2020-04-11 00:03:25', '2020-04-11 00:03:25', NULL, 116.00, 58.00, 2, 1, 1);

-- Permissão

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
insert into permissao (id, nome, descricao) values (3, 'CONSULTAR_FORMAS_PAGAMENTO', 'Permite consultar formas de pagamento');
insert into permissao (id, nome, descricao) values (4, 'EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento');
insert into permissao (id, nome, descricao) values (5, 'CONSULTAR_CIDADES', 'Permite consultar cidades');
insert into permissao (id, nome, descricao) values (6, 'EDITAR_CIDADES', 'Permite criar ou editar cidades');
insert into permissao (id, nome, descricao) values (7, 'CONSULTAR_ESTADOS', 'Permite consultar estados');
insert into permissao (id, nome, descricao) values (8, 'EDITAR_ESTADOS', 'Permite criar ou editar estados');
insert into permissao (id, nome, descricao) values (9, 'CONSULTAR_USUARIOS', 'Permite consultar usuários');
insert into permissao (id, nome, descricao) values (10, 'EDITAR_USUARIOS', 'Permite criar ou editar usuários');
insert into permissao (id, nome, descricao) values (11, 'CONSULTAR_RESTAURANTES', 'Permite consultar restaurantes');
insert into permissao (id, nome, descricao) values (12, 'EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes');
insert into permissao (id, nome, descricao) values (13, 'CONSULTAR_PRODUTOS', 'Permite consultar produtos');
insert into permissao (id, nome, descricao) values (14, 'EDITAR_PRODUTOS', 'Permite criar ou editar produtos');
insert into permissao (id, nome, descricao) values (15, 'CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
insert into permissao (id, nome, descricao) values (16, 'GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
insert into permissao (id, nome, descricao) values (17, 'GERAR_RELATORIOS', 'Permite gerar relatórios');

-- Usuário e Grupo

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2), (3, 3), (4, 4);

-- Grupo e Permissão

-- Adiciona todas as permissoes no grupo do gerente
insert into grupo_permissao (grupo_id, permissao_id)
select 1, id from permissao;

-- Adiciona permissoes no grupo do vendedor
insert into grupo_permissao (grupo_id, permissao_id)
select 2, id from permissao where nome like 'CONSULTAR_%';

insert into grupo_permissao (grupo_id, permissao_id) values (2, 14);

-- Adiciona permissoes no grupo do auxiliar
insert into grupo_permissao (grupo_id, permissao_id)
select 3, id from permissao where nome like 'CONSULTAR_%';

-- Adiciona permissoes no grupo cadastrador
insert into grupo_permissao (grupo_id, permissao_id)
select 4, id from permissao where nome like '%_RESTAURANTES' or nome like '%_PRODUTOS';