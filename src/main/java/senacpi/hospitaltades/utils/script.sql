CREATE DATABASE IF NOT EXISTS `hospital_tades`;
USE `hospital_tades`;

CREATE TABLE `paciente` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `nome` varchar(45) DEFAULT NULL,
    `sobrenome` varchar(45) DEFAULT NULL,
    `dataNasc` varchar(11) DEFAULT NULL,
    `cpf` varchar(15) DEFAULT NULL,
    `sexo` varchar(15) DEFAULT NULL,
    `contato` varchar(15) DEFAULT NULL,
    `email` varchar(45) DEFAULT NULL,
    `ativo` boolean,
    PRIMARY KEY (`id`)
);

CREATE TABLE `usuario` (
	`idUsuario` int(11) NOT NULL AUTO_INCREMENT,
    `login` varchar(45) DEFAULT NULL,
    `senha` varchar(45) DEFAULT NULL,
    `nome` varchar(45) DEFAULT NULL,
    `cargo` varchar(30) DEFAULT NULL,
    `loginAtivo` boolean,
    `ativo` boolean,
	PRIMARY KEY (`idUsuario`)
);

CREATE TABLE `medico` (
	`idMedico` int(11) NOT NULL AUTO_INCREMENT,
    `nome` varchar(45) DEFAULT NULL,
    `sobrenome` varchar(45) DEFAULT NULL,
    `cpf` varchar(15) DEFAULT NULL,
    `sexo` varchar(15) DEFAULT NULL,
    `crm` varchar(15) DEFAULT NULL,
    `temLogin` boolean,
    `ativo` boolean,
    PRIMARY KEY (`idMedico`)
);


CREATE TABLE `atendente` (
	`idAtendente` int(11) NOT NULL AUTO_INCREMENT,
    `nome` varchar(45) DEFAULT NULL,
    `sobrenome` varchar(45) DEFAULT NULL,
    `cpf` varchar(15) DEFAULT NULL,
    `sexo` varchar(15) DEFAULT NULL, 
    `temLogin` boolean,
    `ativo` boolean,
    PRIMARY KEY (`idAtendente`)
);

CREATE TABLE `remedio` (
	`idRemedio` int(11) NOT NULL AUTO_INCREMENT,
    `nome` varchar(45) DEFAULT NULL,
    `quantidade` varchar(4) DEFAULT NULL,
    `ativo` boolean,
    PRIMARY KEY (`idRemedio`)
);


