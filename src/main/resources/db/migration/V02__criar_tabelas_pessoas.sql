CREATE TABLE pessoa (
     id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
     nome VARCHAR(50) NOT NULL,
     ativo BOOLEAN NOT NULL,
     logradouro VARCHAR(50),
     numero VARCHAR(6),
     complemento VARCHAR(20),
     bairro VARCHAR(20),
     cep VARCHAR(20),
     cidade VARCHAR(30),
     estado VARCHAR(2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) VALUES('Luciano Melo Farias',true,'Rua José Dias','634',null,'Bela Vista','58700-465','Belo Horizonte','MG');
INSERT INTO pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) VALUES('Marcio Sousa Vieira',true,'Rua 15 de Maio','350',null,'Malvinas','60300-000','Barbalha','CE');
INSERT INTO pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) VALUES('Talita Santos Rosa',true,'Rua da Alvorada','86','Apto 301','Centro','35750-010','Porto Velho','RO');
INSERT INTO pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) VALUES('Antônio Mariano Bezerra',true,'Rua Carolina Sucupira','116',null,'Aldeota','65850-030','Fortaleza','CE');
INSERT INTO pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) VALUES('Josué Mariano',true,'Av. Rio Branco','321', null, 'Jardins', '56.400-12', 'Natal', 'RN');
INSERT INTO pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) VALUES('Pedro Barbosa',true,'Av Brasil','100', null, 'Tubalina', '77.400-12', 'Porto Alegre', 'RS');
INSERT INTO pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) VALUES('Henrique Medeiros',true,'Rua do Sapo', '1120', 'Apto 201', 'Centro', '12.400-12', 'Rio de Janeiro', 'RJ');
