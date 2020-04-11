-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: algafood
-- ------------------------------------------------------
-- Server version	5.7.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

ALTER DATABASE algafood DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

--
-- Table structure for table cidade
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cidade (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  data_atualizacao datetime DEFAULT NULL,
  data_criacao datetime DEFAULT NULL,
  nome varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  estado_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  KEY FKkworrwk40xj58kevvh3evi500 (estado_id),
  CONSTRAINT FKkworrwk40xj58kevvh3evi500 FOREIGN KEY (estado_id) REFERENCES estado (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table cozinha
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cozinha (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  data_atualizacao datetime DEFAULT NULL,
  data_criacao datetime DEFAULT NULL,
  nome varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table estado
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE estado (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  data_atualizacao datetime DEFAULT NULL,
  data_criacao datetime DEFAULT NULL,
  nome varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table forma_pagamento
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE forma_pagamento (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  data_atualizacao datetime DEFAULT NULL,
  data_criacao datetime DEFAULT NULL,
  descricao varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table grupo
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE grupo (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  nome varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table grupo_permissao
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE grupo_permissao (
  grupo_id bigint(20) NOT NULL,
  permissao_id bigint(20) NOT NULL,
  PRIMARY KEY (grupo_id,permissao_id),
  KEY FKh21kiw0y0hxg6birmdf2ef6vy (permissao_id),
  CONSTRAINT FKh21kiw0y0hxg6birmdf2ef6vy FOREIGN KEY (permissao_id) REFERENCES permissao (id),
  CONSTRAINT FKta4si8vh3f4jo3bsslvkscc2m FOREIGN KEY (grupo_id) REFERENCES grupo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table item_pedido
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE item_pedido (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  data_atualizacao datetime DEFAULT NULL,
  data_criacao datetime DEFAULT NULL,
  observacao varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  preco_total decimal(19,2) NOT NULL,
  preco_unitario decimal(19,2) NOT NULL,
  quantidade int(11) NOT NULL,
  pedido_id bigint(20) DEFAULT NULL,
  produto_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK60ym08cfoysa17wrn1swyiuda (pedido_id),
  KEY FKtk55mn6d6bvl5h0no5uagi3sf (produto_id),
  CONSTRAINT FK60ym08cfoysa17wrn1swyiuda FOREIGN KEY (pedido_id) REFERENCES pedido (id),
  CONSTRAINT FKtk55mn6d6bvl5h0no5uagi3sf FOREIGN KEY (produto_id) REFERENCES produto (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table pedido
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE pedido (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  data_atualizacao datetime DEFAULT NULL,
  data_criacao datetime DEFAULT NULL,
  codigo varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  data_cancelamento datetime DEFAULT NULL,
  data_confirmacao datetime DEFAULT NULL,
  data_entrega datetime DEFAULT NULL,
  endereco_bairro varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  endereco_cep varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  endereco_complemento varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  endereco_logradouro varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  endereco_numero varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  status varchar(10) DEFAULT NULL,
  subtotal decimal(19,2) DEFAULT NULL,
  taxa_frete decimal(19,2) DEFAULT NULL,
  valor_total decimal(19,2) DEFAULT NULL,
  cliente_id bigint(20) DEFAULT NULL,
  endereco_cidade_id bigint(20) DEFAULT NULL,
  forma_pagamento_id bigint(20) DEFAULT NULL,
  restaurante_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK37ms39e5dvx6m05hftvx9uavk (cliente_id),
  KEY FKk987vfg9cpgx7qxj3166fdqig (endereco_cidade_id),
  KEY FKqaa411xsl0xu4tkvt1wpccd3b (forma_pagamento_id),
  KEY FK3eud5cqmgsnltyk704hu3qj71 (restaurante_id),
  CONSTRAINT FK37ms39e5dvx6m05hftvx9uavk FOREIGN KEY (cliente_id) REFERENCES usuario (id),
  CONSTRAINT FK3eud5cqmgsnltyk704hu3qj71 FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),
  CONSTRAINT FKk987vfg9cpgx7qxj3166fdqig FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id),
  CONSTRAINT FKqaa411xsl0xu4tkvt1wpccd3b FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table permissao
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE permissao (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  descricao varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  nome varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table produto
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE produto (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  data_atualizacao datetime DEFAULT NULL,
  data_criacao datetime DEFAULT NULL,
  ativo bit(1) NOT NULL,
  descricao varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  nome varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  preco decimal(19,2) DEFAULT NULL,
  restaurante_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKb9jhjyghjcn25guim7q4pt8qx (restaurante_id),
  CONSTRAINT FKb9jhjyghjcn25guim7q4pt8qx FOREIGN KEY (restaurante_id) REFERENCES restaurante (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table restaurante
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE restaurante (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  data_atualizacao datetime DEFAULT NULL,
  data_criacao datetime DEFAULT NULL,
  aberto bit(1) NOT NULL,
  ativo bit(1) NOT NULL,
  endereco_bairro varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  endereco_cep varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  endereco_complemento varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  endereco_logradouro varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  endereco_numero varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  nome varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  taxa_frete decimal(19,2) DEFAULT NULL,
  id_cozinha bigint(20) DEFAULT NULL,
  endereco_cidade_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKs0dxabd90oowkwaqh42dkbfxi (id_cozinha),
  KEY FKbc0tm7hnvc96d8e7e2ulb05yw (endereco_cidade_id),
  CONSTRAINT FKbc0tm7hnvc96d8e7e2ulb05yw FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id),
  CONSTRAINT FKs0dxabd90oowkwaqh42dkbfxi FOREIGN KEY (id_cozinha) REFERENCES cozinha (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table restaurante_forma_pagamento
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE restaurante_forma_pagamento (
  restaurante_id bigint(20) NOT NULL,
  forma_pagamento_id bigint(20) NOT NULL,
  KEY FK7aln770m80358y4olr03hyhh2 (forma_pagamento_id),
  KEY FKa30vowfejemkw7whjvr8pryvj (restaurante_id),
  CONSTRAINT FK7aln770m80358y4olr03hyhh2 FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id),
  CONSTRAINT FKa30vowfejemkw7whjvr8pryvj FOREIGN KEY (restaurante_id) REFERENCES restaurante (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table restaurante_usuario_responsavel
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE restaurante_usuario_responsavel (
  restaurante_id bigint(20) NOT NULL,
  usuario_id bigint(20) NOT NULL,
  KEY FKp5xoqly0jvjow2qtvsc6m2a8x (usuario_id),
  KEY FKb6qllil4954tf7wg9x5xj3weo (restaurante_id),
  CONSTRAINT FKb6qllil4954tf7wg9x5xj3weo FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),
  CONSTRAINT FKp5xoqly0jvjow2qtvsc6m2a8x FOREIGN KEY (usuario_id) REFERENCES usuario (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table usuario
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE usuario (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  data_cadastro datetime DEFAULT NULL,
  email varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  nome varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  senha varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table usuario_grupo
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE usuario_grupo (
  usuario_id bigint(20) NOT NULL,
  grupo_id bigint(20) NOT NULL,
  PRIMARY KEY (usuario_id,grupo_id),
  KEY FKk30suuy31cq5u36m9am4om9ju (grupo_id),
  CONSTRAINT FKdofo9es0esuiahyw2q467crxw FOREIGN KEY (usuario_id) REFERENCES usuario (id),
  CONSTRAINT FKk30suuy31cq5u36m9am4om9ju FOREIGN KEY (grupo_id) REFERENCES grupo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'algafood'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-06 11:19:03
