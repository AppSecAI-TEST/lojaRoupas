DROP DATABASE IF EXISTS CriancaBonitaDB;
CREATE DATABASE IF NOT EXISTS CriancaBonitaDB;
USE CriancaBonitaDB;
-- NA HORA DE ADICIONAR AO BANCO DE DADOS, RETIRAR CARACTERES ESPECIAIS
CREATE TABLE Transacao 
	-- Venda, devolucao ou caixa
( 
	ID_Transacao                         integer  NOT NULL AUTO_INCREMENT,
	TipoDeTransacao                  char(40) NOT NULL,
		-- venda , devolucao, caixa
	Valor_Total                      decimal(6,2)  NOT NULL ,
	Data_Transacao                       date  NULL ,
    Hora_Transacao                        time NULL ,
	Descricao_Transacao                         char(255) NULL   ,
		-- Descricao é criada automaticamente com os itens da venda e seus descontos
		-- Escolher com muito cuidado o formato da Descricao para poder recuperar as informacoes		
	ID_Caixa					     integer NULL, 
	Cliente                          char(40)   NULL       ,
		-- somente auxiliar, nao serve como chave de procura
	Observacao                      char(255)  NULL,
	PRIMARY KEY (ID_Transacao)
)
;

CREATE TABLE Cliente
( 
	ID_Cliente         integer   NOT NULL AUTO_INCREMENT,
	Nome_Cliente       char(50)  NOT NULL ,	
	Saldo_Cliente      decimal(6,2)   NULL ,	
	-- CUIDADO!!!! comercar com 0 ou com NULL
	Telefone_Celular1  char(18)  NULL ,
	Telefone_Celular2  char(18)  NULL ,
	Telefone_Fixo      char(18)  NULL ,
	Endereco_Cliente   char(70)  NULL ,
	Email_Cliente      char(50)  NULL,
	CPF_Cliente        integer NULL,
	Descricao_Cliente  char(70),
	PRIMARY KEY (ID_Cliente)
)
;

CREATE TABLE Fornecedor
( 
	ID_Fornecedor      integer  NOT NULL AUTO_INCREMENT,
	Nome_Fornecedor    char(50)  NOT NULL ,
	CPF_Fornecedor     integer NULL,
	Telefone_Fixo      char(18)  NULL ,
	Telefone_Celular1  char(18)  NULL ,
	Telefone_Celular2  char(18)  NULL ,
	Endereco_Fornecedor char(70)  NULL ,
	Email_Fornecedor   char(50)  NULL,
	Descricao_Fornecedor  char(70),
	PRIMARY KEY (ID_Fornecedor)
)
;

CREATE TABLE Usuario
( 
	ID_Usuario         integer  NOT NULL AUTO_INCREMENT,
	Nome_Usuario       char(50)  NOT NULL,        
	Descricao_Usuario       char(70)  NULL,
	PRIMARY KEY (ID_Usuario)
)
;


CREATE TABLE Caixa
( 
	ID_Caixa        integer  NOT NULL AUTO_INCREMENT,
	Status          char(8) NOT NULL,
		-- aberto ou fechado
		-- Encontramos o resultado em vendas/devolucoes pelas transacoes
	Data_Abertura    date    NOT NULL,	
	Hora_Abertura    time    NOT NULL,  
	Data_Fechamento  date     NULL, 
	Hora_Fechamento  time     NULL, 
	Adicionado      decimal(6,2) NOT NULL,
	Retirado        decimal(6,2)  NOT NULL,
		-- calcular o final real e comparar com o informado
	FinalInformado  decimal(6,2)  NULL,
	QuebraDeCaixa   decimal(6,2)  NULL,
	PRIMARY KEY (ID_Caixa)	
)
;

CREATE TABLE TipoMercadoria
(
	ID_TipoMercadoria        integer  NOT NULL AUTO_INCREMENT,
	Descricao_TipoMercadoria		 char(40)     NOT NULL,
		-- saia, conjunto, blusa ...., outros		
	PRIMARY KEY (ID_TipoMercadoria)
)
;

CREATE TABLE Mercadoria
( 
	ID_Mercadoria      char(30)   NOT NULL, 
		-- codigo de barras		
	TipoMercadoria          char(40)   NOT NULL ,
    Descricao_Mercadoria          char(255) NOT NULL,     
	Status             char(20)   NOT NULL,	
		-- (no estoque), (vendido), (encomendado) 		
	Tamanho_est        char(3)   NULL,      
	Tamanho            char(3)   NULL,	
    Observacao         char(255) NULL,  
	Preco_Merc         decimal(6,2)   NULL,
	PRIMARY KEY (ID_Mercadoria)
)
;
--    *********************************************************************************************
INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("camisa");
INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("vestido");
INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("saia");
INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("short");
INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("calça");
INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("conjunto");
INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("cinto");
INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("arco");
INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("biquini");
INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("calcinha e sutia");
INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("jaqueta");
INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("bebe");
INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("outros");
-- insert into caixa(Status, Data_Abertura, Hora_Abertura, Adicionado, Retirado) Values ('aberto', '2017-07-28', '17:00:00', 20.3, 0);













   