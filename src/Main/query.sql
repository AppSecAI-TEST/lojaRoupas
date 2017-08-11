DROP DATABASE IF EXISTS CriancaBonitaDB;
CREATE DATABASE IF NOT EXISTS CriancaBonitaDB;
USE CriancaBonitaDB;
-- NA HORA DE ADICIONAR AO BANCO DE DADOS, RETIRAR CARACTERES ESPECIAIS
CREATE TABLE Transacao 
	-- Venda, devolucao ou caixa
( 	
	ID_Transacao                     integer  NOT NULL AUTO_INCREMENT,
	ID_Caixa					     integer NULL, 
	Tipo_Transacao                  char(40) NOT NULL,
		-- venda , devolucao, caixa
	Dinheiro                      decimal(6,2)  NOT NULL ,
	Cartao                        decimal(6,2)  NULL,
	Fiado                            decimal(6,2)  NULL,
	Com_SaldoCliente                 decimal(6,2)  NULL,
	Data_Transacao                       date  NULL ,
    Hora_Transacao                        time NULL ,
	Descricao_Transacao                         char(255) NULL   ,
		-- Na venda: A descricao criada com os itens da venda e seus descontos
		-- Na devolucao: A descricao é o ID_Mercadoria ou vazia/null se for produto não cadastrado
		-- Na caixa: "retirada do caixa"  "adição ao caixa"
	Cliente                          char(40)   NULL       ,
		-- somente auxiliar, nao serve como chave de procura
	Vendedor						   char(40) NULL,
	Observacao                      char(255)  NULL,
		-- na devolução, corresponde ao motivo
		-- na venda, corresponde ao desconto e outras coisas a serem adicionadas
	PRIMARY KEY (ID_Transacao)		
)
;

CREATE TABLE Cliente
( 
	ID_Cliente         integer   NOT NULL AUTO_INCREMENT,
	Nome_Cliente       char(50)  NOT NULL ,	
	Saldo_Cliente      decimal(6,2)   NULL ,	
	-- CUIDADO!!!! comercar com 0 ou com NULL
	Descricao_Cliente  char(70) NULL,	
	Telefone_Celular1  char(18)  NULL ,
	Telefone_Celular2  char(18)  NULL ,
	Telefone_Fixo      char(18)  NULL ,
	Endereco_Cliente   char(70)  NULL ,
	Email_Cliente      char(50)  NULL,
	CPF_Cliente        char(18)  NULL ,
	PRIMARY KEY (ID_Cliente)
)
;

CREATE TABLE Fornecedor
( 
	ID_Fornecedor      integer  NOT NULL AUTO_INCREMENT,
	Nome_Fornecedor    char(50)  NOT NULL ,
	Descricao_Fornecedor  char(70) NULL,	
	Telefone_Fixo      char(18)  NULL ,
	Telefone_Celular1  char(18)  NULL ,
	Telefone_Celular2  char(18)  NULL ,
	Endereco_Fornecedor char(70)  NULL ,
	Email_Fornecedor   char(50)  NULL,
	CPF_Fornecedor     char(18)  NULL ,	
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
	VendasDevolucoes decimal(6,2)  NULL,	
	FinalInformado  decimal(6,2)  NULL,
	QuebraDeCaixa   decimal(6,2)  NULL,
		-- aberto ou fechado
		-- Encontramos o resultado em vendas/devolucoes pelas transacoes
	Data_Abertura    date    NOT NULL,	
	Hora_Abertura    time    NOT NULL, 	
	Adicionado      decimal(6,2) NOT NULL,
	Retirado        decimal(6,2)  NOT NULL,
		-- calcular o final real e comparar com o informado
	Status          char(8) NOT NULL,
	Data_Fechamento  date     NULL, 
	Hora_Fechamento  time     NULL, 
	Observacao                      char(255)  NULL,
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
	Descricao_Mercadoria          char(255) NOT NULL,   	
	TipoMercadoria          char(40)   NOT NULL ,
    Preco_Merc         decimal(6,2)   NULL,
	Status             char(20)   NOT NULL,	
		-- (no estoque), (vendido), (encomendado) 		
	Tamanho_est        char(3)   NULL,      
	Tamanho            char(3)   NULL,	
    Observacao         char(255) NULL,  
	PRIMARY KEY (ID_Mercadoria)
)
;
CREATE TABLE Senha
(
	ID_Senha     integer   NOT NULL AUTO_INCREMENT, 	
	String_Senha         char(40) NOT NULL,
	Tipo_Senha           char(1) NOT NULL,
	-- 1: ADM  2: USER
	PRIMARY KEY (ID_Senha)
);
--    *********************************************************************************************
INSERT into Senha(String_Senha, Tipo_Senha) VALUES ("sgscb", "1");
INSERT into Senha(String_Senha, Tipo_Senha) VALUES ("02042010", "1");
INSERT into Senha(String_Senha, Tipo_Senha) VALUES ("261287", "2");
-- INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("camisa");
-- INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("vestido");
-- INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("saia");
-- INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("short");
-- INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("calça");
-- INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("conjunto");
-- INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("cinto");
-- INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("arco");
-- INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("biquini");
-- INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("calcinha e sutiã");
-- INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("jaqueta");
-- INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("bebê");
-- INSERT into TipoMercadoria(Descricao_TipoMercadoria) VALUES ("outros");
-- INSERT into Senha(String_Senha, Tipo_Senha) VALUES ("sgscb", "1");
-- INSERT into Senha(String_Senha, Tipo_Senha) VALUES ("02042010", "1");
-- INSERT into Senha(String_Senha, Tipo_Senha) VALUES ("criancabonita17", "2");

-- insert into caixa(Status, Data_Abertura, Hora_Abertura, Adicionado, Retirado) Values ('aberto', '2017-07-28', '17:00:00', 20.3, 0);













   