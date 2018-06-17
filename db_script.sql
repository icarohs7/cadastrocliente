CREATE DATABASE IF NOT EXISTS cadastrocliente;
USE cadastrocliente;

CREATE TABLE IF NOT EXISTS cliente (
	id             INT         NOT NULL AUTO_INCREMENT,
	nome           VARCHAR(70) NOT NULL,
	telresidencial VARCHAR(20) NOT NULL,
	telcomercial   VARCHAR(20) NOT NULL,
	telcelular     VARCHAR(20) NOT NULL,
	email          VARCHAR(70) NOT NULL,
	
	PRIMARY KEY (id)
);

INSERT INTO cliente (nome, telresidencial, telcomercial, telcelular, email)
VALUES ('Icaro Temponi', '(31) 1234-1234', '(31) 4321-4321', '(31) 1532-3141', 'icarohs7@gmail.com');

INSERT INTO cliente (nome, telresidencial, telcomercial, telcelular, email)
VALUES ('Márcio Ribeiro', '(31) 1111-2222', '(31) 3333-4444', '(31) 2351-1413', 'profmarcioazevedoribeiro@gmail.com');