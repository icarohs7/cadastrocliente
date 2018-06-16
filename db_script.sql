CREATE DATABASE IF NOT EXISTS cadastrocliente;

CREATE TABLE IF NOT EXISTS cliente (
	id             INT         NOT NULL AUTO_INCREMENT,
	nome           VARCHAR(70) NOT NULL,
	telresidencial VARCHAR(20) NOT NULL,
	telcomercial   VARCHAR(20) NOT NULL,
	telcelular     VARCHAR(20) NOT NULL,
	email          VARCHAR(70) NOT NULL,
	
	PRIMARY KEY (id)
);