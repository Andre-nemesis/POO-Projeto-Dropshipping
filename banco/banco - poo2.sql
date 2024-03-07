/* modeloLogico - ProjetoPOO: */

drop database if exists dbshiphub2;

create database dbshiphub2;
use dbshiphub2;

CREATE TABLE cliente (
    id int PRIMARY KEY auto_increment,
    cpf varchar(14) unique not null,
    nome varchar(60) not null,
    numeroCartao bigint not null unique,
    telefone varchar(13),
    cep varchar(9) not null,
    endereco varchar(50) not null,
    uf varchar(2) default 'nD' not null,
    email varchar(50) not null,
    senha varchar(10) not null
);

CREATE TABLE Fornecedor (
    id int PRIMARY KEY auto_increment,
    cnpj  varchar(19) unique not null,
    nome varchar(40) not null,
    telefone varchar(13),
    cep varchar(9) not null,
    uf varchar(2) default 'nD' not null,
    endereco varchar(50) not null,
    email varchar(50) not null,
    senha varchar(10) not null
);

CREATE TABLE TipoProduto (
    id int PRIMARY KEY auto_increment,
    nome varchar(20) UNIQUE
);

CREATE TABLE Venda_Pagamento (
    ID int PRIMARY KEY auto_increment,
    DataPagamento date,
    prazo date,
    ValorFinal float
);

CREATE TABLE Pedido (
    id int PRIMARY KEY auto_increment,
    dataSaida date not null,
    dataChegada date not null,
    quantidade int not null,
    fk_Cliente_ID int,
    fk_Venda_Pagamento_ID int
);

CREATE TABLE Produto (
    id int PRIMARY KEY auto_increment,
    nome varchar(40) not null,
    descricao text,
    valor float,
    quantidade int,
    fk_TipoProduto_ID int,
    fk_Pedido_ID int,
    fk_Fornecedor_ID int
);

CREATE TABLE relizada (
    fk_Fornecedor_ID int,
    fk_Venda_Pagamento_ID int
);
 
ALTER TABLE Pedido ADD CONSTRAINT FK_Pedido_2
    FOREIGN KEY (fk_Cliente_ID)
    REFERENCES Cliente (id)
    ON DELETE CASCADE;
 
ALTER TABLE Pedido ADD CONSTRAINT FK_Pedido_3
    FOREIGN KEY (fk_Venda_Pagamento_ID)
    REFERENCES Venda_Pagamento (ID)
    ON DELETE CASCADE;
 
ALTER TABLE Produto ADD CONSTRAINT FK_Produto_2
    FOREIGN KEY (fk_TipoProduto_ID)
    REFERENCES TipoProduto (id)
    ON DELETE CASCADE;
 
ALTER TABLE Produto ADD CONSTRAINT FK_Produto_3
    FOREIGN KEY (fk_Pedido_ID)
    REFERENCES Pedido (id)
    ON DELETE CASCADE;
 
ALTER TABLE Produto ADD CONSTRAINT FK_Produto_4
    FOREIGN KEY (fk_Fornecedor_ID)
    REFERENCES Fornecedor (id)
    ON DELETE CASCADE;
 
ALTER TABLE relizada ADD CONSTRAINT FK_relizada_1
    FOREIGN KEY (fk_Fornecedor_ID)
    REFERENCES Fornecedor (id)
    ON DELETE CASCADE;
 
ALTER TABLE relizada ADD CONSTRAINT FK_relizada_2
    FOREIGN KEY (fk_Venda_Pagamento_ID)
    REFERENCES Venda_Pagamento (id)
    ON DELETE CASCADE;