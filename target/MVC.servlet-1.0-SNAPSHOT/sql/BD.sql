create database supermercado;

use supermercado;

create table filial (
idfilial int primary key,
nome varchar(50),
CNPJ varchar(50),
logradouro 	varchar(50),
numero	int,
complemento	varchar(50),
cep	varchar(50),
cidade varchar(50),
UF varchar(10));

create table usuario (
id int primary key auto_increment,
nome varchar(50),
cpf varchar(50),
perfil varchar(50),
login varchar(50),
senha varchar(50),
senha_adm varchar(50),
filial varchar(50),
idfilial int,
foreign key (idfilial) references filial (idfilial));

create table venda(
idvenda int primary key auto_increment,
nomeproduto varchar(50),
codigoproduto varchar(50),
categoria varchar(50),
qtd int,
preco_unitario double,
preco_total double,
usuario varchar(50),
nomeCLiente varchar(50),
cpfCliente varchar(50),
nomeFilial varchar(50),
tipoPagto varchar(50)
);
    
create table cliente (
idCliente int primary key auto_increment,
nomecli varchar(50),
sexo varchar(50),
CPF varchar(50),
rg varchar(50),
estadoCivil varchar(50),
telefone varchar(50),
dataNascimento varchar(50),
email varchar(50),
logradouro varchar(50),
numero varchar(50),
complemento varchar(50),
cep varchar(50),
cidade varchar(50),
UF varchar(50),
idfilial int,
foreign key (idfilial) references filial (idfilial));

create table produto (
idproduto int primary key auto_increment,
nome varchar(50),
codigoproduto varchar(50),
categoria varchar(50),
qtd int,
preco double,
filial varchar(50),
idfilial int,
foreign key (idfilial) references filial (idfilial));

create table formapagamento(
idpagamento int primary key auto_increment,
credito varchar(50),
debito varchar(50),
dinheiro varchar(50),
alimentacao varchar(50));

create table relatorio(
idrelatorio int primary key auto_increment,
nomeproduto varchar(50),
codigoproduto varchar(50),
categoria varchar(50),
qtd_vendido int,
preco_unitario double,
preco_total double,
data_venda date,
usuario varchar(50),
nomeCLiente varchar(50),
cpfCliente varchar(50),
nomeFilial varchar(50),
forma_pagamento varchar(50),
idfilial int, 
foreign key (idfilial) references filial (idfilial));

insert into filial (idfilial, nome, CNPJ, logradouro, numero, complemento, cep, cidade, UF)value(
1, "CDSP", "012335463400001-4", "rua jardins", "43", "ap17 B", "01516-044", "São Paulo", "SP");

insert into filial (idfilial, nome, CNPJ, logradouro, numero, complemento, cep, cidade, UF)value(
2, "CDRJ", "012335463400002-6", "rua cidades", "54", "Conj 28", "01500-144", "Rio de Janeiro", "RJ");

insert into formapagamento (credito, debito, dinheiro, alimentacao)value("Cartão de Crédito", "Cartão de Débito", "Dinheiro", "Voucher Alimentação");

insert into usuario (nome, cpf, filial, perfil, login, senha, senha_adm, idfilial) value(
"Rafael Rocha Alves Louzano", "123", "CDSP", "ADMIN", "rlouzano", "123", "123", 1);



insert into cliente (nomecli, sexo, CPF, rg, estadoCivil, telefone, dataNascimento, email, logradouro, numero, complemento, cep, cidade, UF, idfilial) value(
"Rafael Louzano", "Masculino", "123", "443634634", "Casado", "11986232910", "08/04/1988", "teste", "rua joao ferreira de abreu", "533","AP 33", "04445140", "São Paulo", "SP", 1);

insert into produto (nome, codigoproduto, categoria, qtd, preco, filial, idfilial)value( 
"Arroz", "32564123574", "Alimentos", 100, 5.0, "CDSP", 1);
