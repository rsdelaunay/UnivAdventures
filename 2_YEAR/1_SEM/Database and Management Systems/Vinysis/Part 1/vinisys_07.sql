create table CastaEdicaoVinho
(
   edicaoVinhoID   Integer   not null,
   castaID   Integer   not null,
   dataCriacao   time   null,
 
   constraint PK_CastaEdicaoVinho primary key (edicaoVinhoID, castaID)
);
 
create table CastaVinha
(
   castaID   Integer   not null,
   vinhaID   Integer   not null,
   areaCultivada   double   null,
   dataPlantacao   time   null,
 
   constraint PK_CastaVinha primary key (castaID, vinhaID)
);
 
create table ColheitaColaborador
(
   colheitaID   Integer   not null,
   numColab   Integer   not null,
   dataCriacao   time   null,
   horasTrabalhadas   Integer   null,
   ordenado   double   null,
 
   constraint PK_ColheitaColaborador primary key (colheitaID, numColab)
);
 
create table PremioEdicaoVinho
(
   edicaoVinhoID   Integer   not null,
   premioID   Integer   not null,
   dataAtribuicao   time   null,
 
   constraint PK_PremioEdicaoVinho primary key (edicaoVinhoID, premioID)
);
 
create table DevolucaoLinhaFatura
(
   linhaFaturaID   integer   not null,
   numDevolucao   Integer   not null,
   quantidadeDevolvida   Integer   null,
 
   constraint PK_DevolucaoLinhaFatura primary key (linhaFaturaID, numDevolucao)
);
 
create table Vinho
(
   produtorID   Integer   not null,
   tipoVinhoID   Integer   not null,
   classificacaoAtribuidaID   Integer   not null,
   regiaoID   Integer   not null,
   idVinho   Integer   not null AUTO_INCREMENT,
   nomeVinho   text   null,
   dataEngarrafamento   time   null,
   teorAlc   double   null,
 
   constraint PK_Vinho primary key (idVinho)
);
 
create table EdicaoVinho
(
   vinhoID   Integer   not null,
   tipoEdicaoVinhoID   Integer   not null,
   idEdicao   Integer   not null,
   ano   Integer   null,
   volProd   double   null,
 
   constraint PK_EdicaoVinho primary key (idEdicao)
);
 
create table Casta
(
   idCasta   Integer   not null AUTO_INCREMENT,
   nomeCasta   text   null,
 
   constraint PK_Casta primary key (idCasta)
);
 
create table Vinha
(
   idVinha   Integer   not null AUTO_INCREMENT,
   nomeVinha   text   null,
 
   constraint PK_Vinha primary key (idVinha)
);
 
create table TipoEdicaoVinho
(
   idTipoEdicao   Integer   not null AUTO_INCREMENT,
   nomeTipoEdicao   text   null,
 
   constraint PK_TipoEdicaoVinho primary key (idTipoEdicao)
);
 
create table Produtor
(
   idProdutor   Integer   not null AUTO_INCREMENT,
   nomeProdutor   text   null,
   email   text   null,
   morada   text   null,
   codigoPostal   text   null,
   telefone   Integer   null,
 
   constraint PK_Produtor primary key (idProdutor)
);
 
create table Colheita
(
   vinhaID   Integer   not null,
   produtorID   Integer   not null,
   regiaoID   Integer   not null,
   idColheita   Integer   not null AUTO_INCREMENT,
   anoColheita   Integer   null,
   dataInicio   time   null,
   dataFim   time   null,
   kgUvasColhidas   double   null,
 
   constraint PK_Colheita primary key (idColheita)
);
 
create table Colaborador
(
   numColab   Integer   not null AUTO_INCREMENT,
   nomeColab   text   null,
 
   constraint PK_Colaborador primary key (numColab)
);
 
create table Enologo
(
   numColab   Integer   not null,
   especializacaoID   Integer   not null,
 
   constraint PK_Enologo primary key (numColab)
);
 
create table Especializacao
(
   idEspecializacao   Integer   not null AUTO_INCREMENT,
   nomeEspecializacao   text   null,
 
   constraint PK_Especializacao primary key (idEspecializacao)
);
 
create table Trabalhador
(
   numColab   Integer   not null,
   trabalhadorNumColab   Integer   null,
   funcaoID   Integer   not null,
 
   constraint PK_Trabalhador primary key (numColab)
);
 
create table Funcao
(
   idFuncao   Integer   not null AUTO_INCREMENT,
   nomeFuncao   text   null,
   valorHora   double   null,
 
   constraint PK_Funcao primary key (idFuncao)
);
 
create table Premio
(
   entidadePremiadoraID   Integer   not null,
   idPremio   Integer   not null AUTO_INCREMENT,
   nomePremio   text   null,
 
   constraint PK_Premio primary key (idPremio)
);
 
create table EntidadePremiadora
(
   idEntidade   Integer   not null AUTO_INCREMENT,
   nomeEntidade   text   null,
 
   constraint PK_EntidadePremiadora primary key (idEntidade)
);
 
create table TipoVinho
(
   idTipoVinho   Integer   not null AUTO_INCREMENT,
   nomeTipoVinho   text   null,
 
   constraint PK_TipoVinho primary key (idTipoVinho)
);
 
create table NotaDegustacao
(
   vinhoID   Integer   not null,
   idNota   Integer   not null AUTO_INCREMENT,
   descricaoAroma   text   null,
   sabor   text   null,
   corVinho   text   null,
 
   constraint PK_NotaDegustacao primary key (idNota)
);
 
create table ClassificacaoAtribuida
(
   idClassificacao   Integer   not null AUTO_INCREMENT,
   nomeClassificacao   text   null,
 
   constraint PK_ClassificacaoAtribuida primary key (idClassificacao)
);
 
create table Regiao
(
   idRegiao   Integer   not null AUTO_INCREMENT,
   denominacao   text   null,
 
   constraint PK_Regiao primary key (idRegiao)
);
 
create table LinhaFatura
(
   vinhoID   Integer   not null,
   numFatura   Integer   not null,
   numCliente   Integer   not null,
   idLinhaFatura   integer   not null AUTO_INCREMENT,
   quantidadeGarrafas   Integer   null,
   dimensaoGarrafa   double   null,
   precoUnitario   double   null,
 
   constraint PK_LinhaFatura primary key (idLinhaFatura)
);
 
create table Fatura
(
   numFatura   Integer   not null AUTO_INCREMENT,
   dataVenda   time   null,
   valorTotal   double   null,
 
   constraint PK_Fatura primary key (numFatura)
);
 
create table Devolucao
(
   numDevolucao   Integer   not null AUTO_INCREMENT,
   dataDevolucao   time   null,
   totalDevolvido   double   null,
 
   constraint PK_Devolucao primary key (numDevolucao)
);
 
create table Cliente
(
   tipoClienteID   Integer   not null,
   numCliente   Integer   not null AUTO_INCREMENT,
   nomeCliente   text   null,
   nif   Integer   null,
   moradaCliente   text   null,
 
   constraint PK_Cliente primary key (numCliente)
);
 
create table TipoCliente
(
   idTipoCliente   Integer   not null AUTO_INCREMENT,
   nomeTipoCliente   text   null,
 
   constraint PK_TipoCliente primary key (idTipoCliente)
);
 
alter table CastaEdicaoVinho
   add constraint FK_EdicaoVinho_CastaEdicaoVinho_Casta_ foreign key (edicaoVinhoID)
   references EdicaoVinho(idEdicao)
   on delete restrict
   on update restrict
; 
alter table CastaEdicaoVinho
   add constraint FK_Casta_CastaEdicaoVinho_EdicaoVinho_ foreign key (castaID)
   references Casta(idCasta)
   on delete restrict
   on update restrict
;
alter table CastaVinha
   add constraint FK_Casta_CastaVinha_Vinha_ foreign key (castaID)
   references Casta(idCasta)
   on delete restrict
   on update restrict
; 
alter table CastaVinha
   add constraint FK_Vinha_CastaVinha_Casta_ foreign key (vinhaID)
   references Vinha(idVinha)
   on delete restrict
   on update restrict
;
alter table ColheitaColaborador
   add constraint FK_Colheita_ColheitaColaborador_Colaborador_ foreign key (colheitaID)
   references Colheita(idColheita)
   on delete restrict
   on update restrict
; 
alter table ColheitaColaborador
   add constraint FK_Colaborador_ColheitaColaborador_Colheita_ foreign key (numColab)
   references Colaborador(numColab)
   on delete restrict
   on update restrict
;
alter table PremioEdicaoVinho
   add constraint FK_EdicaoVinho_PremioEdicaoVinho_Premio_ foreign key (edicaoVinhoID)
   references EdicaoVinho(idEdicao)
   on delete restrict
   on update restrict
; 
alter table PremioEdicaoVinho
   add constraint FK_Premio_PremioEdicaoVinho_EdicaoVinho_ foreign key (premioID)
   references Premio(idPremio)
   on delete restrict
   on update restrict
;
alter table DevolucaoLinhaFatura
   add constraint FK_LinhaFatura_DevolucaoLinhaFatura_Devolucao_ foreign key (linhaFaturaID)
   references LinhaFatura(idLinhaFatura)
   on delete restrict
   on update restrict
; 
alter table DevolucaoLinhaFatura
   add constraint FK_Devolucao_DevolucaoLinhaFatura_LinhaFatura_ foreign key (numDevolucao)
   references Devolucao(numDevolucao)
   on delete restrict
   on update restrict
;
alter table Vinho
   add constraint FK_Vinho_produz_Produtor foreign key (produtorID)
   references Produtor(idProdutor)
   on delete restrict
   on update restrict
; 
alter table Vinho
   add constraint FK_Vinho_tipifica_TipoVinho foreign key (tipoVinhoID)
   references TipoVinho(idTipoVinho)
   on delete restrict
   on update restrict
; 
alter table Vinho
   add constraint FK_Vinho_classifica_ClassificacaoAtribuida foreign key (classificacaoAtribuidaID)
   references ClassificacaoAtribuida(idClassificacao)
   on delete restrict
   on update restrict
; 
alter table Vinho
   add constraint FK_Vinho_localiza_Regiao foreign key (regiaoID)
   references Regiao(idRegiao)
   on delete restrict
   on update restrict
;
alter table EdicaoVinho
   add constraint FK_EdicaoVinho_pode_ter_Vinho foreign key (vinhoID)
   references Vinho(idVinho)
   on delete restrict
   on update restrict
; 
alter table EdicaoVinho
   add constraint FK_EdicaoVinho_caracteriza_TipoEdicaoVinho foreign key (tipoEdicaoVinhoID)
   references TipoEdicaoVinho(idTipoEdicao)
   on delete restrict
   on update restrict
;
alter table Colheita
   add constraint FK_Colheita_colhida_Vinha foreign key (vinhaID)
   references Vinha(idVinha)
   on delete restrict
   on update restrict
; 
alter table Colheita
   add constraint FK_Colheita_colhe_Produtor foreign key (produtorID)
   references Produtor(idProdutor)
   on delete restrict
   on update restrict
; 
alter table Colheita
   add constraint FK_Colheita_efetuada_Regiao foreign key (regiaoID)
   references Regiao(idRegiao)
   on delete restrict
   on update restrict
;
alter table Enologo
   add constraint FK_Enologo_Colaborador foreign key (numColab)
   references Colaborador(numColab)
   on delete restrict
   on update restrict
; 
alter table Enologo
   add constraint FK_Enologo_especializa_Especializacao foreign key (especializacaoID)
   references Especializacao(idEspecializacao)
   on delete restrict
   on update restrict
;
alter table Trabalhador
   add constraint FK_Trabalhador_Colaborador foreign key (numColab)
   references Colaborador(numColab)
   on delete restrict
   on update restrict
; 
alter table Trabalhador
   add constraint FK_Trabalhador_chefe_Trabalhador foreign key (trabalhadorNumColab)
   references Trabalhador(numColab)
   on delete set null
   on update restrict
; 
alter table Trabalhador
   add constraint FK_Trabalhador_executa_Funcao foreign key (funcaoID)
   references Funcao(idFuncao)
   on delete restrict
   on update restrict
;
alter table Premio
   add constraint FK_Premio_atribui_EntidadePremiadora foreign key (entidadePremiadoraID)
   references EntidadePremiadora(idEntidade)
   on delete restrict
   on update restrict
;
alter table NotaDegustacao
   add constraint FK_NotaDegustacao_caracteriza_Vinho foreign key (vinhoID)
   references Vinho(idVinho)
   on delete cascade
   on update restrict
;
alter table LinhaFatura
   add constraint FK_LinhaFatura_faturado_Vinho foreign key (vinhoID)
   references Vinho(idVinho)
   on delete restrict
   on update restrict
; 
alter table LinhaFatura
   add constraint FK_LinhaFatura_compoe_Fatura foreign key (numFatura)
   references Fatura(numFatura)
   on delete restrict
   on update restrict
; 
alter table LinhaFatura
   add constraint FK_LinhaFatura_compra_Cliente foreign key (numCliente)
   references Cliente(numCliente)
   on delete restrict
   on update restrict
;
alter table Cliente
   add constraint FK_Cliente_distingue_TipoCliente foreign key (tipoClienteID)
   references TipoCliente(idTipoCliente)
   on delete restrict
   on update restrict
;