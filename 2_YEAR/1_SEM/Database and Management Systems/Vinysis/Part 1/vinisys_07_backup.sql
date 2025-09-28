-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 02-Nov-2024 às 23:56
-- Versão do servidor: 10.4.32-MariaDB
-- versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `vinisys`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `casta`
--

CREATE TABLE `casta` (
  `idCasta` int(11) NOT NULL,
  `nomeCasta` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `casta`
--

INSERT INTO `casta` (`idCasta`, `nomeCasta`) VALUES
(1, 'Uva Roxa'),
(2, 'Uva Branca');

-- --------------------------------------------------------

--
-- Estrutura da tabela `castaedicaovinho`
--

CREATE TABLE `castaedicaovinho` (
  `edicaoVinhoID` int(11) NOT NULL,
  `castaID` int(11) NOT NULL,
  `dataCriacao` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `castaedicaovinho`
--

INSERT INTO `castaedicaovinho` (`edicaoVinhoID`, `castaID`, `dataCriacao`) VALUES
(1, 2, '2024-11-02 22:42:21'),
(2, 1, '2024-11-02 22:42:21');

-- --------------------------------------------------------

--
-- Estrutura da tabela `castavinha`
--

CREATE TABLE `castavinha` (
  `castaID` int(11) NOT NULL,
  `vinhaID` int(11) NOT NULL,
  `areaCultivada` double DEFAULT NULL,
  `dataPlantacao` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `castavinha`
--

INSERT INTO `castavinha` (`castaID`, `vinhaID`, `areaCultivada`, `dataPlantacao`) VALUES
(1, 2, 200, '2024-11-02 22:41:54'),
(2, 1, 100, '2024-11-02 22:41:51');

-- --------------------------------------------------------

--
-- Estrutura da tabela `classificacaoatribuida`
--

CREATE TABLE `classificacaoatribuida` (
  `idClassificacao` int(11) NOT NULL,
  `nomeClassificacao` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `classificacaoatribuida`
--

INSERT INTO `classificacaoatribuida` (`idClassificacao`, `nomeClassificacao`) VALUES
(1, 'DOC'),
(2, 'IGP');

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE `cliente` (
  `tipoClienteID` int(11) NOT NULL,
  `numCliente` int(11) NOT NULL,
  `nomeCliente` text DEFAULT NULL,
  `nif` int(9) NOT NULL,
  `moradaCliente` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` (`tipoClienteID`, `numCliente`, `nomeCliente`, `nif`, `moradaCliente`) VALUES
(1, 1, 'Francisco Ribeiro', 123456789, 'São Marcos'),
(1, 2, 'ISCTE', 123456788, 'Lisboa');

-- --------------------------------------------------------

--
-- Estrutura da tabela `colaborador`
--

CREATE TABLE `colaborador` (
  `numColab` int(11) NOT NULL,
  `nomeColab` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `colaborador`
--

INSERT INTO `colaborador` (`numColab`, `nomeColab`) VALUES
(1, 'Ruben'),
(2, 'Rodrigo'),
(3, 'Pedro'),
(4, 'Gonçalo'),
(5, 'Fábio');

-- --------------------------------------------------------

--
-- Estrutura da tabela `colheita`
--

CREATE TABLE `colheita` (
  `vinhaID` int(11) NOT NULL,
  `produtorID` int(11) NOT NULL,
  `regiaoID` int(11) NOT NULL,
  `idColheita` int(11) NOT NULL,
  `anoColheita` int(11) DEFAULT NULL,
  `dataInicio` datetime DEFAULT NULL,
  `dataFim` datetime DEFAULT NULL,
  `kgUvasColhidas` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `colheita`
--

INSERT INTO `colheita` (`vinhaID`, `produtorID`, `regiaoID`, `idColheita`, `anoColheita`, `dataInicio`, `dataFim`, `kgUvasColhidas`) VALUES
(1, 1, 1, 1, 2024, '2024-11-01 22:47:38', '2024-11-02 22:47:38', 10),
(2, 2, 2, 2, 2024, '2024-11-01 22:47:38', '2024-11-02 22:47:38', 20);

-- --------------------------------------------------------

--
-- Estrutura da tabela `colheitacolaborador`
--

CREATE TABLE `colheitacolaborador` (
  `colheitaID` int(11) NOT NULL,
  `numColab` int(11) NOT NULL,
  `dataCriacao` datetime DEFAULT NULL,
  `horasTrabalhadas` int(11) DEFAULT NULL,
  `ordenado` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `colheitacolaborador`
--

INSERT INTO `colheitacolaborador` (`colheitaID`, `numColab`, `dataCriacao`, `horasTrabalhadas`, `ordenado`) VALUES
(1, 2, '2024-11-02 22:48:13', 20, NULL),
(2, 2, '2024-11-02 22:48:13', 30, NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `devolucao`
--

CREATE TABLE `devolucao` (
  `numDevolucao` int(11) NOT NULL,
  `dataDevolucao` datetime DEFAULT NULL,
  `totalDevolvido` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `devolucao`
--

INSERT INTO `devolucao` (`numDevolucao`, `dataDevolucao`, `totalDevolvido`) VALUES
(1, '2024-11-02 22:50:15', 5);

-- --------------------------------------------------------

--
-- Estrutura da tabela `devolucaolinhafatura`
--

CREATE TABLE `devolucaolinhafatura` (
  `linhaFaturaID` int(11) NOT NULL,
  `numDevolucao` int(11) NOT NULL,
  `quantidadeDevolvida` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `devolucaolinhafatura`
--

INSERT INTO `devolucaolinhafatura` (`linhaFaturaID`, `numDevolucao`, `quantidadeDevolvida`) VALUES
(1, 1, 5);

-- --------------------------------------------------------

--
-- Estrutura da tabela `edicaovinho`
--

CREATE TABLE `edicaovinho` (
  `vinhoID` int(11) NOT NULL,
  `tipoEdicaoVinhoID` int(11) NOT NULL,
  `idEdicao` int(11) NOT NULL,
  `ano` int(11) DEFAULT NULL,
  `volProd` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `edicaovinho`
--

INSERT INTO `edicaovinho` (`vinhoID`, `tipoEdicaoVinhoID`, `idEdicao`, `ano`, `volProd`) VALUES
(3, 1, 1, 2024, 20),
(3, 2, 2, 2023, 30);

-- --------------------------------------------------------

--
-- Estrutura da tabela `enologo`
--

CREATE TABLE `enologo` (
  `numColab` int(11) NOT NULL,
  `especializacaoID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `enologo`
--

INSERT INTO `enologo` (`numColab`, `especializacaoID`) VALUES
(2, 1),
(1, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `entidadepremiadora`
--

CREATE TABLE `entidadepremiadora` (
  `idEntidade` int(11) NOT NULL,
  `nomeEntidade` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `entidadepremiadora`
--

INSERT INTO `entidadepremiadora` (`idEntidade`, `nomeEntidade`) VALUES
(1, 'ISCTE'),
(2, 'MEO');

-- --------------------------------------------------------

--
-- Estrutura da tabela `especializacao`
--

CREATE TABLE `especializacao` (
  `idEspecializacao` int(11) NOT NULL,
  `nomeEspecializacao` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `especializacao`
--

INSERT INTO `especializacao` (`idEspecializacao`, `nomeEspecializacao`) VALUES
(1, 'Enologo-Chefe'),
(2, 'Assistente de Enologo');

-- --------------------------------------------------------

--
-- Estrutura da tabela `fatura`
--

CREATE TABLE `fatura` (
  `numFatura` int(11) NOT NULL,
  `dataVenda` datetime DEFAULT NULL,
  `valorTotal` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `fatura`
--

INSERT INTO `fatura` (`numFatura`, `dataVenda`, `valorTotal`) VALUES
(1, '2024-11-02 22:49:09', 100),
(2, '2024-11-01 22:49:09', 200);

-- --------------------------------------------------------

--
-- Estrutura da tabela `funcao`
--

CREATE TABLE `funcao` (
  `idFuncao` int(11) NOT NULL,
  `nomeFuncao` text DEFAULT NULL,
  `valorHora` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `funcao`
--

INSERT INTO `funcao` (`idFuncao`, `nomeFuncao`, `valorHora`) VALUES
(1, 'Apanhador', 5),
(2, 'Chefe', 10);

-- --------------------------------------------------------

--
-- Estrutura da tabela `linhafatura`
--

CREATE TABLE `linhafatura` (
  `vinhoID` int(11) NOT NULL,
  `numFatura` int(11) NOT NULL,
  `numCliente` int(11) NOT NULL,
  `idLinhaFatura` int(11) NOT NULL,
  `quantidadeGarrafas` int(11) DEFAULT NULL,
  `dimensaoGarrafa` double DEFAULT NULL,
  `precoUnitario` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `linhafatura`
--

INSERT INTO `linhafatura` (`vinhoID`, `numFatura`, `numCliente`, `idLinhaFatura`, `quantidadeGarrafas`, `dimensaoGarrafa`, `precoUnitario`) VALUES
(3, 1, 1, 1, 20, 0, 2),
(3, 2, 2, 2, 30, 0, 1),
(3, 1, 1, 3, 20, 0.75, 2),
(3, 2, 2, 4, 30, 0.5, 1.5);

-- --------------------------------------------------------

--
-- Estrutura da tabela `notadegustacao`
--

CREATE TABLE `notadegustacao` (
  `vinhoID` int(11) NOT NULL,
  `idNota` int(11) NOT NULL,
  `descricaoAroma` text DEFAULT NULL,
  `sabor` text DEFAULT NULL,
  `corVinho` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `notadegustacao`
--

INSERT INTO `notadegustacao` (`vinhoID`, `idNota`, `descricaoAroma`, `sabor`, `corVinho`) VALUES
(3, 1, 'Cheiroso', 'Bom', 'Vermelho Escuro');

-- --------------------------------------------------------

--
-- Estrutura da tabela `premio`
--

CREATE TABLE `premio` (
  `entidadePremiadoraID` int(11) NOT NULL,
  `idPremio` int(11) NOT NULL,
  `nomePremio` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `premio`
--

INSERT INTO `premio` (`entidadePremiadoraID`, `idPremio`, `nomePremio`) VALUES
(1, 1, 'Melhor Vinho ISCTE 2024'),
(2, 2, 'Melhor Vinho MEO 2024');

-- --------------------------------------------------------

--
-- Estrutura da tabela `premioedicaovinho`
--

CREATE TABLE `premioedicaovinho` (
  `edicaoVinhoID` int(11) NOT NULL,
  `premioID` int(11) NOT NULL,
  `dataAtribuicao` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `premioedicaovinho`
--

INSERT INTO `premioedicaovinho` (`edicaoVinhoID`, `premioID`, `dataAtribuicao`) VALUES
(1, 1, '2024-11-02 22:40:30'),
(2, 2, '2024-11-02 22:40:30');

-- --------------------------------------------------------

--
-- Estrutura da tabela `produtor`
--

CREATE TABLE `produtor` (
  `idProdutor` int(11) NOT NULL,
  `nomeProdutor` text DEFAULT NULL,
  `email` text NOT NULL,
  `morada` text DEFAULT NULL,
  `codigoPostal` text DEFAULT NULL,
  `telefone` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `produtor`
--

INSERT INTO `produtor` (`idProdutor`, `nomeProdutor`, `email`, `morada`, `codigoPostal`, `telefone`) VALUES
(1, 'Francisco', 'francisco@gmail.com', 'São Marcos', '2735-675', 986584376),
(2, 'Tiago', 'tiago@gmail.com', 'Massamá', '2605-652', 967548394);

-- --------------------------------------------------------

--
-- Estrutura da tabela `regiao`
--

CREATE TABLE `regiao` (
  `idRegiao` int(11) NOT NULL,
  `denominacao` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `regiao`
--

INSERT INTO `regiao` (`idRegiao`, `denominacao`) VALUES
(1, 'Lisboa'),
(2, 'Porto');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tipocliente`
--

CREATE TABLE `tipocliente` (
  `idTipoCliente` int(11) NOT NULL,
  `nomeTipoCliente` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tipocliente`
--

INSERT INTO `tipocliente` (`idTipoCliente`, `nomeTipoCliente`) VALUES
(1, 'Particular'),
(2, 'Empresa');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tipoedicaovinho`
--

CREATE TABLE `tipoedicaovinho` (
  `idTipoEdicao` int(11) NOT NULL,
  `nomeTipoEdicao` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tipoedicaovinho`
--

INSERT INTO `tipoedicaovinho` (`idTipoEdicao`, `nomeTipoEdicao`) VALUES
(1, 'Teste 1'),
(2, 'Teste 2');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tipovinho`
--

CREATE TABLE `tipovinho` (
  `idTipoVinho` int(11) NOT NULL,
  `nomeTipoVinho` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tipovinho`
--

INSERT INTO `tipovinho` (`idTipoVinho`, `nomeTipoVinho`) VALUES
(1, 'Tinto'),
(2, 'Branco');

-- --------------------------------------------------------

--
-- Estrutura da tabela `trabalhador`
--

CREATE TABLE `trabalhador` (
  `numColab` int(11) NOT NULL,
  `trabalhadorNumColab` int(11) DEFAULT NULL,
  `funcaoID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `trabalhador`
--

INSERT INTO `trabalhador` (`numColab`, `trabalhadorNumColab`, `funcaoID`) VALUES
(3, NULL, 1),
(4, 5, 1),
(5, NULL, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `vinha`
--

CREATE TABLE `vinha` (
  `idVinha` int(11) NOT NULL,
  `nomeVinha` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `vinha`
--

INSERT INTO `vinha` (`idVinha`, `nomeVinha`) VALUES
(1, 'Vinha Lisboa'),
(2, 'Vinho Porto');

-- --------------------------------------------------------

--
-- Estrutura da tabela `vinho`
--

CREATE TABLE `vinho` (
  `produtorID` int(11) NOT NULL,
  `tipoVinhoID` int(11) NOT NULL,
  `classificacaoAtribuidaID` int(11) NOT NULL,
  `regiaoID` int(11) NOT NULL,
  `idVinho` int(11) NOT NULL,
  `nomeVinho` text DEFAULT NULL,
  `dataEngarrafamento` datetime DEFAULT NULL,
  `teorAlc` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `vinho`
--

INSERT INTO `vinho` (`produtorID`, `tipoVinhoID`, `classificacaoAtribuidaID`, `regiaoID`, `idVinho`, `nomeVinho`, `dataEngarrafamento`, `teorAlc`) VALUES
(1, 2, 1, 1, 3, 'Teste 1', '2024-11-02 22:31:11', 15);

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `casta`
--
ALTER TABLE `casta`
  ADD PRIMARY KEY (`idCasta`);

--
-- Índices para tabela `castaedicaovinho`
--
ALTER TABLE `castaedicaovinho`
  ADD PRIMARY KEY (`edicaoVinhoID`,`castaID`),
  ADD KEY `FK_Casta_CastaEdicaoVinho_EdicaoVinho_` (`castaID`);

--
-- Índices para tabela `castavinha`
--
ALTER TABLE `castavinha`
  ADD PRIMARY KEY (`castaID`,`vinhaID`),
  ADD KEY `FK_Vinha_CastaVinha_Casta_` (`vinhaID`);

--
-- Índices para tabela `classificacaoatribuida`
--
ALTER TABLE `classificacaoatribuida`
  ADD PRIMARY KEY (`idClassificacao`);

--
-- Índices para tabela `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`numCliente`),
  ADD UNIQUE KEY `nif` (`nif`),
  ADD KEY `FK_Cliente_distingue_TipoCliente` (`tipoClienteID`);

--
-- Índices para tabela `colaborador`
--
ALTER TABLE `colaborador`
  ADD PRIMARY KEY (`numColab`);

--
-- Índices para tabela `colheita`
--
ALTER TABLE `colheita`
  ADD PRIMARY KEY (`idColheita`),
  ADD KEY `FK_Colheita_colhida_Vinha` (`vinhaID`),
  ADD KEY `FK_Colheita_colhe_Produtor` (`produtorID`),
  ADD KEY `FK_Colheita_efetuada_Regiao` (`regiaoID`);

--
-- Índices para tabela `colheitacolaborador`
--
ALTER TABLE `colheitacolaborador`
  ADD PRIMARY KEY (`colheitaID`,`numColab`),
  ADD KEY `FK_Colaborador_ColheitaColaborador_Colheita_` (`numColab`);

--
-- Índices para tabela `devolucao`
--
ALTER TABLE `devolucao`
  ADD PRIMARY KEY (`numDevolucao`);

--
-- Índices para tabela `devolucaolinhafatura`
--
ALTER TABLE `devolucaolinhafatura`
  ADD PRIMARY KEY (`linhaFaturaID`,`numDevolucao`),
  ADD KEY `FK_Devolucao_DevolucaoLinhaFatura_LinhaFatura_` (`numDevolucao`);

--
-- Índices para tabela `edicaovinho`
--
ALTER TABLE `edicaovinho`
  ADD PRIMARY KEY (`idEdicao`),
  ADD KEY `FK_EdicaoVinho_pode_ter_Vinho` (`vinhoID`),
  ADD KEY `FK_EdicaoVinho_caracteriza_TipoEdicaoVinho` (`tipoEdicaoVinhoID`);

--
-- Índices para tabela `enologo`
--
ALTER TABLE `enologo`
  ADD PRIMARY KEY (`numColab`),
  ADD KEY `FK_Enologo_especializa_Especializacao` (`especializacaoID`);

--
-- Índices para tabela `entidadepremiadora`
--
ALTER TABLE `entidadepremiadora`
  ADD PRIMARY KEY (`idEntidade`);

--
-- Índices para tabela `especializacao`
--
ALTER TABLE `especializacao`
  ADD PRIMARY KEY (`idEspecializacao`);

--
-- Índices para tabela `fatura`
--
ALTER TABLE `fatura`
  ADD PRIMARY KEY (`numFatura`);

--
-- Índices para tabela `funcao`
--
ALTER TABLE `funcao`
  ADD PRIMARY KEY (`idFuncao`);

--
-- Índices para tabela `linhafatura`
--
ALTER TABLE `linhafatura`
  ADD PRIMARY KEY (`idLinhaFatura`),
  ADD KEY `FK_LinhaFatura_faturado_Vinho` (`vinhoID`),
  ADD KEY `FK_LinhaFatura_compoe_Fatura` (`numFatura`),
  ADD KEY `FK_LinhaFatura_compra_Cliente` (`numCliente`);

--
-- Índices para tabela `notadegustacao`
--
ALTER TABLE `notadegustacao`
  ADD PRIMARY KEY (`idNota`),
  ADD KEY `FK_NotaDegustacao_caracteriza_Vinho` (`vinhoID`);

--
-- Índices para tabela `premio`
--
ALTER TABLE `premio`
  ADD PRIMARY KEY (`idPremio`),
  ADD KEY `FK_Premio_atribui_EntidadePremiadora` (`entidadePremiadoraID`);

--
-- Índices para tabela `premioedicaovinho`
--
ALTER TABLE `premioedicaovinho`
  ADD PRIMARY KEY (`edicaoVinhoID`,`premioID`),
  ADD KEY `FK_Premio_PremioEdicaoVinho_EdicaoVinho_` (`premioID`);

--
-- Índices para tabela `produtor`
--
ALTER TABLE `produtor`
  ADD PRIMARY KEY (`idProdutor`),
  ADD UNIQUE KEY `email` (`email`) USING HASH;

--
-- Índices para tabela `regiao`
--
ALTER TABLE `regiao`
  ADD PRIMARY KEY (`idRegiao`);

--
-- Índices para tabela `tipocliente`
--
ALTER TABLE `tipocliente`
  ADD PRIMARY KEY (`idTipoCliente`);

--
-- Índices para tabela `tipoedicaovinho`
--
ALTER TABLE `tipoedicaovinho`
  ADD PRIMARY KEY (`idTipoEdicao`);

--
-- Índices para tabela `tipovinho`
--
ALTER TABLE `tipovinho`
  ADD PRIMARY KEY (`idTipoVinho`);

--
-- Índices para tabela `trabalhador`
--
ALTER TABLE `trabalhador`
  ADD PRIMARY KEY (`numColab`),
  ADD KEY `FK_Trabalhador_chefe_Trabalhador` (`trabalhadorNumColab`),
  ADD KEY `FK_Trabalhador_executa_Funcao` (`funcaoID`);

--
-- Índices para tabela `vinha`
--
ALTER TABLE `vinha`
  ADD PRIMARY KEY (`idVinha`);

--
-- Índices para tabela `vinho`
--
ALTER TABLE `vinho`
  ADD PRIMARY KEY (`idVinho`),
  ADD KEY `FK_Vinho_produz_Produtor` (`produtorID`),
  ADD KEY `FK_Vinho_tipifica_TipoVinho` (`tipoVinhoID`),
  ADD KEY `FK_Vinho_classifica_ClassificacaoAtribuida` (`classificacaoAtribuidaID`),
  ADD KEY `FK_Vinho_localiza_Regiao` (`regiaoID`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `casta`
--
ALTER TABLE `casta`
  MODIFY `idCasta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `classificacaoatribuida`
--
ALTER TABLE `classificacaoatribuida`
  MODIFY `idClassificacao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `cliente`
--
ALTER TABLE `cliente`
  MODIFY `numCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `colaborador`
--
ALTER TABLE `colaborador`
  MODIFY `numColab` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `colheita`
--
ALTER TABLE `colheita`
  MODIFY `idColheita` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `devolucao`
--
ALTER TABLE `devolucao`
  MODIFY `numDevolucao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `edicaovinho`
--
ALTER TABLE `edicaovinho`
  MODIFY `idEdicao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `entidadepremiadora`
--
ALTER TABLE `entidadepremiadora`
  MODIFY `idEntidade` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `especializacao`
--
ALTER TABLE `especializacao`
  MODIFY `idEspecializacao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `fatura`
--
ALTER TABLE `fatura`
  MODIFY `numFatura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `funcao`
--
ALTER TABLE `funcao`
  MODIFY `idFuncao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `linhafatura`
--
ALTER TABLE `linhafatura`
  MODIFY `idLinhaFatura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `notadegustacao`
--
ALTER TABLE `notadegustacao`
  MODIFY `idNota` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `premio`
--
ALTER TABLE `premio`
  MODIFY `idPremio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `produtor`
--
ALTER TABLE `produtor`
  MODIFY `idProdutor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `regiao`
--
ALTER TABLE `regiao`
  MODIFY `idRegiao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `tipocliente`
--
ALTER TABLE `tipocliente`
  MODIFY `idTipoCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `tipoedicaovinho`
--
ALTER TABLE `tipoedicaovinho`
  MODIFY `idTipoEdicao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `tipovinho`
--
ALTER TABLE `tipovinho`
  MODIFY `idTipoVinho` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `vinha`
--
ALTER TABLE `vinha`
  MODIFY `idVinha` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `vinho`
--
ALTER TABLE `vinho`
  MODIFY `idVinho` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `castaedicaovinho`
--
ALTER TABLE `castaedicaovinho`
  ADD CONSTRAINT `FK_Casta_CastaEdicaoVinho_EdicaoVinho_` FOREIGN KEY (`castaID`) REFERENCES `casta` (`idCasta`),
  ADD CONSTRAINT `FK_EdicaoVinho_CastaEdicaoVinho_Casta_` FOREIGN KEY (`edicaoVinhoID`) REFERENCES `edicaovinho` (`idEdicao`);

--
-- Limitadores para a tabela `castavinha`
--
ALTER TABLE `castavinha`
  ADD CONSTRAINT `FK_Casta_CastaVinha_Vinha_` FOREIGN KEY (`castaID`) REFERENCES `casta` (`idCasta`),
  ADD CONSTRAINT `FK_Vinha_CastaVinha_Casta_` FOREIGN KEY (`vinhaID`) REFERENCES `vinha` (`idVinha`);

--
-- Limitadores para a tabela `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `FK_Cliente_distingue_TipoCliente` FOREIGN KEY (`tipoClienteID`) REFERENCES `tipocliente` (`idTipoCliente`);

--
-- Limitadores para a tabela `colheita`
--
ALTER TABLE `colheita`
  ADD CONSTRAINT `FK_Colheita_colhe_Produtor` FOREIGN KEY (`produtorID`) REFERENCES `produtor` (`idProdutor`),
  ADD CONSTRAINT `FK_Colheita_colhida_Vinha` FOREIGN KEY (`vinhaID`) REFERENCES `vinha` (`idVinha`),
  ADD CONSTRAINT `FK_Colheita_efetuada_Regiao` FOREIGN KEY (`regiaoID`) REFERENCES `regiao` (`idRegiao`);

--
-- Limitadores para a tabela `colheitacolaborador`
--
ALTER TABLE `colheitacolaborador`
  ADD CONSTRAINT `FK_Colaborador_ColheitaColaborador_Colheita_` FOREIGN KEY (`numColab`) REFERENCES `colaborador` (`numColab`),
  ADD CONSTRAINT `FK_Colheita_ColheitaColaborador_Colaborador_` FOREIGN KEY (`colheitaID`) REFERENCES `colheita` (`idColheita`);

--
-- Limitadores para a tabela `devolucaolinhafatura`
--
ALTER TABLE `devolucaolinhafatura`
  ADD CONSTRAINT `FK_Devolucao_DevolucaoLinhaFatura_LinhaFatura_` FOREIGN KEY (`numDevolucao`) REFERENCES `devolucao` (`numDevolucao`),
  ADD CONSTRAINT `FK_LinhaFatura_DevolucaoLinhaFatura_Devolucao_` FOREIGN KEY (`linhaFaturaID`) REFERENCES `linhafatura` (`idLinhaFatura`);

--
-- Limitadores para a tabela `edicaovinho`
--
ALTER TABLE `edicaovinho`
  ADD CONSTRAINT `FK_EdicaoVinho_caracteriza_TipoEdicaoVinho` FOREIGN KEY (`tipoEdicaoVinhoID`) REFERENCES `tipoedicaovinho` (`idTipoEdicao`),
  ADD CONSTRAINT `FK_EdicaoVinho_pode_ter_Vinho` FOREIGN KEY (`vinhoID`) REFERENCES `vinho` (`idVinho`);

--
-- Limitadores para a tabela `enologo`
--
ALTER TABLE `enologo`
  ADD CONSTRAINT `FK_Enologo_Colaborador` FOREIGN KEY (`numColab`) REFERENCES `colaborador` (`numColab`),
  ADD CONSTRAINT `FK_Enologo_especializa_Especializacao` FOREIGN KEY (`especializacaoID`) REFERENCES `especializacao` (`idEspecializacao`);

--
-- Limitadores para a tabela `linhafatura`
--
ALTER TABLE `linhafatura`
  ADD CONSTRAINT `FK_LinhaFatura_compoe_Fatura` FOREIGN KEY (`numFatura`) REFERENCES `fatura` (`numFatura`),
  ADD CONSTRAINT `FK_LinhaFatura_compra_Cliente` FOREIGN KEY (`numCliente`) REFERENCES `cliente` (`numCliente`),
  ADD CONSTRAINT `FK_LinhaFatura_faturado_Vinho` FOREIGN KEY (`vinhoID`) REFERENCES `vinho` (`idVinho`);

--
-- Limitadores para a tabela `notadegustacao`
--
ALTER TABLE `notadegustacao`
  ADD CONSTRAINT `FK_NotaDegustacao_caracteriza_Vinho` FOREIGN KEY (`vinhoID`) REFERENCES `vinho` (`idVinho`);

--
-- Limitadores para a tabela `premio`
--
ALTER TABLE `premio`
  ADD CONSTRAINT `FK_Premio_atribui_EntidadePremiadora` FOREIGN KEY (`entidadePremiadoraID`) REFERENCES `entidadepremiadora` (`idEntidade`);

--
-- Limitadores para a tabela `premioedicaovinho`
--
ALTER TABLE `premioedicaovinho`
  ADD CONSTRAINT `FK_EdicaoVinho_PremioEdicaoVinho_Premio_` FOREIGN KEY (`edicaoVinhoID`) REFERENCES `edicaovinho` (`idEdicao`),
  ADD CONSTRAINT `FK_Premio_PremioEdicaoVinho_EdicaoVinho_` FOREIGN KEY (`premioID`) REFERENCES `premio` (`idPremio`);

--
-- Limitadores para a tabela `trabalhador`
--
ALTER TABLE `trabalhador`
  ADD CONSTRAINT `FK_Trabalhador_Colaborador` FOREIGN KEY (`numColab`) REFERENCES `colaborador` (`numColab`),
  ADD CONSTRAINT `FK_Trabalhador_chefe_Trabalhador` FOREIGN KEY (`trabalhadorNumColab`) REFERENCES `trabalhador` (`numColab`) ON DELETE SET NULL,
  ADD CONSTRAINT `FK_Trabalhador_executa_Funcao` FOREIGN KEY (`funcaoID`) REFERENCES `funcao` (`idFuncao`);

--
-- Limitadores para a tabela `vinho`
--
ALTER TABLE `vinho`
  ADD CONSTRAINT `FK_Vinho_classifica_ClassificacaoAtribuida` FOREIGN KEY (`classificacaoAtribuidaID`) REFERENCES `classificacaoatribuida` (`idClassificacao`),
  ADD CONSTRAINT `FK_Vinho_localiza_Regiao` FOREIGN KEY (`regiaoID`) REFERENCES `regiao` (`idRegiao`),
  ADD CONSTRAINT `FK_Vinho_produz_Produtor` FOREIGN KEY (`produtorID`) REFERENCES `produtor` (`idProdutor`),
  ADD CONSTRAINT `FK_Vinho_tipifica_TipoVinho` FOREIGN KEY (`tipoVinhoID`) REFERENCES `tipovinho` (`idTipoVinho`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
