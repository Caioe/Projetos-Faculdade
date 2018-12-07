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
    `codFilial` varchar(45) DEFAULT NULL,
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
    `codFilial` varchar(45) DEFAULT NULL,
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
    `idUsuario` integer,
    `codFilial` varchar(45) DEFAULT NULL,
    `ativo` boolean,
    PRIMARY KEY (`idMedico`),
    CONSTRAINT fk_MeUsuarioId FOREIGN KEY (idUsuario) REFERENCES usuario (idUsuario)
);


CREATE TABLE `atendente` (
	`idAtendente` int(11) NOT NULL AUTO_INCREMENT,
    `nome` varchar(45) DEFAULT NULL,
    `sobrenome` varchar(45) DEFAULT NULL,
    `cpf` varchar(15) DEFAULT NULL,
    `sexo` varchar(15) DEFAULT NULL, 
    `idUsuario` integer,
    `codFilial` varchar(45) DEFAULT NULL,
    `ativo` boolean,
    PRIMARY KEY (`idAtendente`),
    CONSTRAINT fk_AtUsuarioId FOREIGN KEY (idUsuario) REFERENCES usuario (idUsuario)
);

CREATE TABLE `remedio` (
	`idRemedio` int(11) NOT NULL AUTO_INCREMENT,
    `nome` varchar(45) DEFAULT NULL,
    `quantidade` varchar(4) DEFAULT NULL,
    `codFilial` varchar(45) DEFAULT NULL,
    `ativo` boolean,
    PRIMARY KEY (`idRemedio`)
);

CREATE TABLE `consultas` (
	`idConsulta` int(11) NOT NULL AUTO_INCREMENT,
    `data` date,
    `motivo` varchar(45) DEFAULT NULL,
    `idPaciente` integer,
    `nomePaciente` varchar(11) DEFAULT NULL,
    `idMedico` integer,
    `nomeMedico` varchar(45) DEFAULT NULL,
    `idRemedio` integer,
    `nomeRemedio` varchar(45) DEFAULT NULL,
    `usuarioNome` varChar(45) DEFAULT NULL,
    `codFilial` varchar(45) DEFAULT NULL,
    `ativo` boolean,
    `obsMedica` varchar(255) DEFAULT NULL,
    PRIMARY KEY(`idConsulta`),
    CONSTRAINT fk_ConsPaciente FOREIGN KEY (idPaciente) REFERENCES paciente (id),
    CONSTRAINT fk_ConsMedico FOREIGN KEY (idMedico) REFERENCES medico (idMedico),
    CONSTRAINT fk_ConsRemedio FOREIGN KEY (idRemedio) REFERENCES remedio (idRemedio)
);

INSERT INTO `usuario` (idUsuario, login, senha, nome, cargo, loginAtivo, codFilial, ativo)
VALUES (1, 'yurypcf', 123456, 'Yury', 'Admin', 1, '23A', 1);
INSERT INTO `usuario` (idUsuario, login, senha, nome, cargo, loginAtivo, codFilial, ativo)
VALUES (2, 's.santos', 123456, 'Sabrina', 'Medico', 1, '23A', 1);
INSERT INTO `usuario` (idUsuario, login, senha, nome, cargo, loginAtivo, codFilial, ativo)
VALUES (3, 'p.sato', 123456, 'Pedro', 'Medico', 1, '23B', 1);
INSERT INTO `usuario` (idUsuario, login, senha, nome, cargo, loginAtivo, codFilial, ativo)
VALUES (4, 'f.pacheco', 123456, 'Fernanda', 'Atendente', 1, '23B', 1);
INSERT INTO `usuario` (idUsuario, login, senha, nome, cargo, loginAtivo, codFilial, ativo)
VALUES (5, 'k.neres', 123456, 'Kelvin', 'Atendente', 1, '23A', 1);

INSERT INTO `paciente` (id, nome, sobrenome, dataNasc, cpf, sexo, contato, email, codFilial, ativo)
VALUES (1, 'Claudio', 'Filho', '1992-09-22', '22222878521', 'Masculino', '11925368741', 'c.farias@gmail.com', '23A', 1);
INSERT INTO `paciente` (id, nome, sobrenome, dataNasc, cpf, sexo, contato, email, codFilial, ativo)
VALUES (2, 'Luana', 'Alves', '1991-10-22', '22222878521', 'Masculino', '11925368741', 'l.alves@gmail.com', '23B', 1);

INSERT INTO `medico` (idMedico, nome, sobrenome, cpf, sexo, crm, idUsuario, codFilial, ativo)
VALUES (1, 'Sabrina', 'Santos', '45117536244', 'Feminino', '238415788BR', 1, '23A', 1);
INSERT INTO `medico` (idMedico, nome, sobrenome, cpf, sexo, crm, idUsuario, codFilial, ativo)
VALUES (3, 'Pedro', 'Sato', '45117536244', 'Masculino', '238413348BR', 3, '23B', 1);

INSERT INTO `atendente` (idAtendente, nome, sobrenome, cpf, sexo, idUsuario, codFilial, ativo)
VALUES (1, 'Kelvin', 'Neres', '22784661425', 'Masculino', 5, '23A', 1);
INSERT INTO `atendente` (idAtendente, nome, sobrenome, cpf, sexo, idUsuario, codFilial, ativo)
VALUES (4, 'Fernanda', 'Pacheco', '22784661425', 'Masculino', 4, '23B', 1);

INSERT INTO `remedio` (idRemedio, nome, quantidade, codFilial, ativo)
VALUES (1, 'Paracetamol', '200', '23A', 1);
INSERT INTO `remedio` (idRemedio, nome, quantidade, codFilial, ativo)
VALUES (2, 'Dipirona Infantil', '400', '23B', 1);
