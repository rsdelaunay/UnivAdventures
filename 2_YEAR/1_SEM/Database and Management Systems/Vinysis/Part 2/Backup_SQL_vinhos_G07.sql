-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 22-Dez-2024 às 23:39
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
-- Banco de dados: `vinhos_v1.1`
--

DELIMITER $$
--
-- Procedimentos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `P1` (IN `colheita_id` INT, IN `trabalhador_id` INT, IN `horas` INT, IN `remuneracao_por_hora` DECIMAL(10,2), OUT `remuneracao_total` DECIMAL(10,2))   BEGIN
    INSERT INTO PARTICIPA (COLHEITA_ID, TRABALHADOR_COLABORADOR_numero, horas, remuneracao)
    VALUES (colheita_id, trabalhador_id, horas, horas * remuneracao_por_hora);

    SELECT SUM(remuneracao) INTO remuneracao_total
    FROM PARTICIPA
    WHERE COLHEITA_ID = colheita_id;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `P2` (IN `ano_colheita` INT, IN `regiao_id` INT, IN `produtor_id` INT, OUT `colheita_id` INT)   BEGIN
    INSERT INTO COLHEITA (ano, REGIAO_ID, PRODUTOR_ID)
    VALUES (ano_colheita, regiao_id, produtor_id);

    SET colheita_id = LAST_INSERT_ID();
END$$

--
-- Funções
--
CREATE DEFINER=`root`@`localhost` FUNCTION `F1` (`colheita_id` INT) RETURNS INT(11) DETERMINISTIC READS SQL DATA BEGIN
    DECLARE total_horas INT;
    SELECT SUM(horas) INTO total_horas
    FROM PARTICIPA
    WHERE participa.COLHEITA_ID = colheita_id;

    RETURN total_horas;
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `F2` (`casta_id` INT) RETURNS INT(11) DETERMINISTIC READS SQL DATA BEGIN
    DECLARE area_total INT;

    SELECT SUM(area_cultivada) INTO area_total
    FROM PRODUZ
    WHERE produz.CASTA_ID = casta_id;

    RETURN area_total;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `acompanha`
--

CREATE TABLE `acompanha` (
  `COLHEITA_ID` int(11) NOT NULL,
  `ENOLOGO_COLABORADOR_numero` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- --------------------------------------------------------

--
-- Estrutura da tabela `casta`
--

CREATE TABLE `casta` (
  `CASTA_ID` int(11) NOT NULL,
  `tipo_uva` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `casta`
--

INSERT INTO `casta` (`CASTA_ID`, `tipo_uva`) VALUES
(1, 'Cabernet Sauvignon'),
(2, 'Chardonnay'),
(3, 'Syrah'),
(4, 'Alvarinho'),
(5, 'Touriga Nacional'),
(6, 'Arinto'),
(7, 'Merlot'),
(8, 'Tinta Roriz'),
(9, 'Pinot Noir'),
(10, 'Moscatel'),
(11, 'Verdelho'),
(12, 'Fernão Pires'),
(13, 'Vinhão'),
(14, 'Alicante Bouschet'),
(15, 'Malvasia'),
(16, 'Trincadeira'),
(17, 'Baga'),
(18, 'Riesling'),
(19, 'Cercial'),
(20, 'Encruzado'),
(21, 'Teste');

-- --------------------------------------------------------

--
-- Estrutura stand-in para vista `castas_mais_produzidas_por_regiao`
-- (Veja abaixo para a view atual)
--
CREATE TABLE `castas_mais_produzidas_por_regiao` (
`regiao` varchar(50)
,`casta` varchar(30)
,`quantidade_produzida` decimal(32,0)
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE `cliente` (
  `numero_cliente` int(11) NOT NULL,
  `nome_cliente` varchar(100) DEFAULT NULL,
  `tipo` varchar(30) DEFAULT NULL,
  `nif` char(9) DEFAULT NULL,
  `morada` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` (`numero_cliente`, `nome_cliente`, `tipo`, `nif`, `morada`) VALUES
(1, 'João Silva', 'Particular', '123456789', 'Rua Principal, 123'),
(2, 'Maria Oliveira', 'Empresa', '987654321', 'Avenida Central, 456'),
(3, 'Carlos Souza', 'Particular', '456789123', 'Praça da Liberdade, 789'),
(4, 'Ana Costa', 'Empresa', '321654987', 'Rua do Comércio, 890');

-- --------------------------------------------------------

--
-- Estrutura da tabela `colaborador`
--

CREATE TABLE `colaborador` (
  `numero` int(11) NOT NULL,
  `nome` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `colaborador`
--

INSERT INTO `colaborador` (`numero`, `nome`) VALUES
(1, 'José'),
(2, 'Manel'),
(3, 'António'),
(4, 'Francisco'),
(5, 'Pedro'),
(6, 'Jorge'),
(7, 'João'),
(8, 'Daniel'),
(9, 'Joel'),
(10, 'Serafim'),
(11, 'Ana'),
(12, 'Maria'),
(13, 'Sofia'),
(14, 'Sara'),
(15, 'Lúcia'),
(16, 'Beatriz'),
(17, 'Rute'),
(18, 'Rita'),
(19, 'Antónia'),
(20, 'Lezíria');

-- --------------------------------------------------------

--
-- Estrutura da tabela `colheita`
--

CREATE TABLE `colheita` (
  `REGIAO_ID` int(11) NOT NULL,
  `PRODUTOR_ID` int(11) NOT NULL,
  `COLHEITA_ID` int(11) NOT NULL,
  `ano` int(11) DEFAULT NULL,
  `data_inicio` date DEFAULT NULL,
  `data_fim` date DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `colheita`
--

INSERT INTO `colheita` (`REGIAO_ID`, `PRODUTOR_ID`, `COLHEITA_ID`, `ano`, `data_inicio`, `data_fim`, `quantidade`) VALUES
(1, 1, 0, 2024, NULL, NULL, NULL),
(1, 1, 1, 2020, '2020-09-01', '2020-11-30', 500),
(1, 1, 2, 2021, '2021-09-01', '2021-11-30', 400),
(2, 2, 3, 2020, '2020-08-01', '2020-10-31', 300),
(2, 2, 4, 2021, '2021-08-01', '2021-10-31', 450);

-- --------------------------------------------------------

--
-- Estrutura da tabela `contem`
--

CREATE TABLE `contem` (
  `CASTA_ID` int(11) NOT NULL,
  `EDICAO_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- --------------------------------------------------------

--
-- Estrutura da tabela `devolucao`
--

CREATE TABLE `devolucao` (
  `CLIENTE_numero_cliente` int(11) NOT NULL,
  `FATURA_num_fatura` int(11) NOT NULL,
  `DEVOLUCAO_ID` int(11) NOT NULL,
  `data_devol` date DEFAULT NULL,
  `montante` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `devolucao`
--

INSERT INTO `devolucao` (`CLIENTE_numero_cliente`, `FATURA_num_fatura`, `DEVOLUCAO_ID`, `data_devol`, `montante`) VALUES
(1, 101, 1, '2023-09-20', 150),
(2, 102, 2, '2023-10-25', 100);

-- --------------------------------------------------------

--
-- Estrutura da tabela `devolvido`
--

CREATE TABLE `devolvido` (
  `VINHO_ID` int(11) NOT NULL,
  `DEVOLUCAO_ID` int(11) NOT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `preco` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `devolvido`
--

INSERT INTO `devolvido` (`VINHO_ID`, `DEVOLUCAO_ID`, `quantidade`, `preco`) VALUES
(1, 1, NULL, NULL),
(2, 2, NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `edicao`
--

CREATE TABLE `edicao` (
  `VINHO_ID` int(11) NOT NULL,
  `EDICAO_ID` int(11) NOT NULL,
  `ano` int(11) DEFAULT NULL,
  `tipo` varchar(30) DEFAULT NULL,
  `volume` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `edicao`
--

INSERT INTO `edicao` (`VINHO_ID`, `EDICAO_ID`, `ano`, `tipo`, `volume`) VALUES
(1, 1, 2020, NULL, NULL),
(2, 2, 2021, NULL, NULL),
(3, 3, 2020, NULL, NULL),
(4, 4, 2022, NULL, NULL),
(5, 5, 2021, NULL, NULL),
(6, 6, 2020, NULL, NULL),
(7, 7, 2023, NULL, NULL),
(8, 8, 2022, NULL, NULL),
(9, 9, 2023, NULL, NULL),
(10, 10, 2021, NULL, NULL),
(11, 11, 2020, NULL, NULL),
(12, 12, 2022, NULL, NULL),
(13, 13, 2023, NULL, NULL),
(14, 14, 2021, NULL, NULL),
(15, 15, 2022, NULL, NULL),
(16, 16, 2020, NULL, NULL),
(17, 17, 2023, NULL, NULL),
(18, 18, 2021, NULL, NULL),
(19, 19, 2022, NULL, NULL),
(20, 20, 2023, NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura stand-in para vista `empresas_maiores_compras`
-- (Veja abaixo para a view atual)
--
CREATE TABLE `empresas_maiores_compras` (
`numero` int(11)
,`nome` varchar(100)
,`nif` char(9)
,`morada` varchar(100)
,`total_faturas` bigint(21)
,`volume_compras` double
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `enologo`
--

CREATE TABLE `enologo` (
  `COLABORADOR_numero` int(11) NOT NULL,
  `especializacao` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- --------------------------------------------------------

--
-- Estrutura da tabela `fatura`
--

CREATE TABLE `fatura` (
  `CLIENTE_numero_cliente` int(11) NOT NULL,
  `num_fatura` int(11) NOT NULL,
  `data_fatura` date DEFAULT NULL,
  `valor_total` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `fatura`
--

INSERT INTO `fatura` (`CLIENTE_numero_cliente`, `num_fatura`, `data_fatura`, `valor_total`) VALUES
(1, 101, '2023-09-15', 3500),
(2, 102, '2023-10-20', 2800),
(1, 103, '2023-11-25', 4000),
(3, 104, '2023-12-05', 2700);

-- --------------------------------------------------------

--
-- Estrutura da tabela `ganha`
--

CREATE TABLE `ganha` (
  `EDICAO_ID` int(11) NOT NULL,
  `PREMIO_ID` int(11) NOT NULL,
  `data_distincao` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `ganha`
--

INSERT INTO `ganha` (`EDICAO_ID`, `PREMIO_ID`, `data_distincao`) VALUES
(1, 1, '2015-03-15'),
(1, 2, '2015-06-10'),
(1, 3, '2015-09-05'),
(1, 6, '2016-11-20'),
(1, 13, '2015-11-20'),
(2, 2, '2016-01-18'),
(2, 4, '2018-04-11'),
(2, 9, '2017-01-18'),
(2, 13, '2016-04-11'),
(3, 3, '2016-07-22'),
(3, 4, '2024-02-01'),
(4, 4, '2017-02-01'),
(4, 13, '2017-05-10'),
(5, 5, '2018-09-14'),
(6, 6, '2018-12-03'),
(7, 7, '2019-02-19'),
(8, 8, '2019-06-05'),
(9, 9, '2020-01-07'),
(10, 10, '2020-04-14'),
(11, 11, '2021-03-23'),
(12, 1, '2014-03-15'),
(12, 2, '2017-01-18'),
(12, 3, '2016-09-05'),
(12, 12, '2021-07-30'),
(12, 13, '2018-04-11'),
(13, 13, '2022-05-17'),
(14, 14, '2022-09-24'),
(15, 15, '2023-03-12'),
(16, 16, '2023-06-09'),
(17, 17, '2023-09-05'),
(18, 18, '2024-01-13'),
(19, 19, '2024-04-20');

-- --------------------------------------------------------

--
-- Estrutura da tabela `incide`
--

CREATE TABLE `incide` (
  `COLHEITA_ID` int(11) NOT NULL,
  `VINHA_codigo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- --------------------------------------------------------

--
-- Estrutura da tabela `item`
--

CREATE TABLE `item` (
  `VINHO_ID` int(11) NOT NULL,
  `FATURA_num_fatura` int(11) NOT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `preco` double DEFAULT NULL,
  `dimensao` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `item`
--

INSERT INTO `item` (`VINHO_ID`, `FATURA_num_fatura`, `quantidade`, `preco`, `dimensao`) VALUES
(1, 101, 200, 15, NULL),
(1, 104, 150, 16, NULL),
(2, 101, 100, 20, NULL),
(2, 104, 120, 21, NULL),
(3, 102, 50, 18, NULL),
(4, 103, 300, 17, NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `participa`
--

CREATE TABLE `participa` (
  `COLHEITA_ID` int(11) NOT NULL,
  `TRABALHADOR_COLABORADOR_numero` int(11) NOT NULL,
  `horas` int(11) DEFAULT NULL,
  `remuneracao` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `participa`
--

INSERT INTO `participa` (`COLHEITA_ID`, `TRABALHADOR_COLABORADOR_numero`, `horas`, `remuneracao`) VALUES
(1, 1, 40, 600),
(1, 10, 80, 800),
(1, 15, 25, 600),
(2, 20, 60, 750),
(3, 14, 80, 1200);

--
-- Acionadores `participa`
--
DELIMITER $$
CREATE TRIGGER `T2` AFTER INSERT ON `participa` FOR EACH ROW BEGIN
IF NOT EXISTS (
        SELECT 1 FROM COLHEITA WHERE COLHEITA_ID = NEW.COLHEITA_ID AND data_inicio IS NOT NULL
    ) THEN
    UPDATE COLHEITA
        SET data_inicio = CURRENT_DATE
        WHERE COLHEITA_ID = NEW.COLHEITA_ID;
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `premio`
--

CREATE TABLE `premio` (
  `PREMIO_ID` int(11) NOT NULL,
  `nome_distincao` varchar(30) DEFAULT NULL,
  `entidade` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `premio`
--

INSERT INTO `premio` (`PREMIO_ID`, `nome_distincao`, `entidade`) VALUES
(1, 'Melhor Vinho Tinto', NULL),
(2, 'Melhor Vinho Branco', NULL),
(3, 'Melhor Design de Garrafa', NULL),
(4, 'Melhor Qualidade/Preço', NULL),
(5, 'Melhor Vinho Regional', NULL),
(6, 'Melhor Vinho Português', NULL),
(7, 'Melhor Vinícola', NULL),
(8, 'Grande Medalha de Ouro', NULL),
(9, 'Medalha de Ouro', NULL),
(10, 'Medalha de Prata', NULL),
(11, 'Melhor Sabor Frutado', NULL),
(12, 'Melhor Aroma', NULL),
(13, 'Melhor Vinho Jovem', NULL),
(14, 'Melhor Envelhecimento', NULL),
(15, 'Vinho Revelação', NULL),
(16, 'Melhor Produção Artesanal', NULL),
(17, 'Melhor Reserva', NULL),
(18, 'Melhor Colheita', NULL),
(19, 'Melhor Vinho Seco', NULL),
(20, 'Melhor Vinho Doce', NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `produtor`
--

CREATE TABLE `produtor` (
  `REGIAO_ID` int(11) NOT NULL,
  `PRODUTOR_ID` int(11) NOT NULL,
  `nome_vinicola` varchar(100) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `morada` varchar(100) DEFAULT NULL,
  `codigo_postal` char(8) DEFAULT NULL,
  `telefone` char(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `produtor`
--

INSERT INTO `produtor` (`REGIAO_ID`, `PRODUTOR_ID`, `nome_vinicola`, `email`, `morada`, `codigo_postal`, `telefone`) VALUES
(1, 1, 'Adega Douro', '', '', '', ''),
(2, 2, 'Herdade do Alentejo', '', '', '', ''),
(3, 3, 'Quinta do Dão', NULL, NULL, NULL, NULL),
(4, 4, 'Casa do Minho', NULL, NULL, NULL, NULL),
(5, 5, 'Cooperativa Setúbal', NULL, NULL, NULL, NULL),
(6, 6, 'Caves do Porto', NULL, NULL, NULL, NULL),
(7, 7, 'Adega Regional Lisboa', NULL, NULL, NULL, NULL),
(1, 8, 'Vinhos Premium Douro', NULL, NULL, NULL, NULL),
(2, 9, 'Herdade do Sublime', NULL, NULL, NULL, NULL),
(3, 10, 'Quinta Encantada', NULL, NULL, NULL, NULL),
(4, 11, 'Casa Verdejo Minho', NULL, NULL, NULL, NULL),
(5, 12, 'Cooperativa Antiga Setúbal', NULL, NULL, NULL, NULL),
(6, 13, 'Caves Ruby Porto', NULL, NULL, NULL, NULL),
(2, 14, 'Adega Reserva Alentejo', NULL, NULL, NULL, NULL),
(1, 15, 'Quinta do Ouro Douro', NULL, NULL, NULL, NULL),
(2, 16, 'Herdade Sereno Alentejo', NULL, NULL, NULL, NULL),
(3, 17, 'Quinta Velha Dão', NULL, NULL, NULL, NULL),
(4, 18, 'Cristalino Minho', NULL, NULL, NULL, NULL),
(5, 19, 'Doce Setúbal', NULL, NULL, NULL, NULL),
(6, 20, 'Vintage Porto', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `produz`
--

CREATE TABLE `produz` (
  `VINHA_codigo` int(11) NOT NULL,
  `CASTA_ID` int(11) NOT NULL,
  `area_cultivada` int(11) DEFAULT NULL,
  `data_plantacao` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `produz`
--

INSERT INTO `produz` (`VINHA_codigo`, `CASTA_ID`, `area_cultivada`, `data_plantacao`) VALUES
(1, 1, 1000, '2015-03-10'),
(1, 2, 2000, '2016-04-20'),
(2, 2, 1500, '2016-04-15'),
(2, 3, 1800, '2014-05-10'),
(3, 1, 1200, '2017-05-20'),
(3, 2, 1700, '2018-06-25'),
(4, 3, 1800, '2018-06-25'),
(4, 4, 1900, '2016-09-05'),
(4, 5, 2100, '2017-11-11'),
(5, 6, 2500, '2015-08-20'),
(5, 7, 2300, '2016-12-02'),
(6, 8, 3000, '2018-01-17'),
(6, 9, 2800, '2017-07-30'),
(7, 10, 2200, '2015-10-15'),
(7, 11, 2700, '2016-03-03'),
(8, 12, 3100, '2014-04-10'),
(8, 13, 3200, '2015-05-25'),
(9, 14, 3300, '2018-08-18'),
(9, 15, 3400, '2017-09-22'),
(10, 16, 2900, '2016-11-08'),
(10, 17, 2500, '2014-12-14'),
(11, 18, 2400, '2017-06-11'),
(11, 19, 2600, '2018-03-21'),
(12, 4, 1800, '2015-02-28'),
(12, 20, 2000, '2016-04-19'),
(13, 5, 2200, '2014-09-09'),
(13, 6, 2400, '2015-12-25'),
(14, 7, 2600, '2018-07-14'),
(14, 8, 2800, '2017-11-03'),
(15, 9, 3000, '2016-10-23'),
(15, 10, 3200, '2014-03-05'),
(16, 11, 3400, '2015-06-15'),
(16, 12, 2900, '2018-08-11'),
(17, 13, 2500, '2017-04-20'),
(17, 14, 2700, '2016-05-01'),
(18, 15, 3100, '2014-02-22'),
(18, 16, 3300, '2015-10-05'),
(19, 17, 3500, '2018-12-30'),
(19, 18, 4000, '2017-03-28'),
(20, 19, 4200, '2016-06-12'),
(20, 20, 4500, '2014-07-07'),
(21, 21, 50, '2024-12-22');

--
-- Acionadores `produz`
--
DELIMITER $$
CREATE TRIGGER `T1` BEFORE INSERT ON `produz` FOR EACH ROW BEGIN
    DECLARE existing_area INT;
    
    SELECT area_cultivada INTO existing_area
    FROM PRODUZ
    WHERE VINHA_codigo = NEW.VINHA_codigo AND CASTA_ID = NEW.CASTA_ID
    LIMIT 1;
    
    IF existing_area IS NOT NULL THEN
        UPDATE PRODUZ
        SET area_cultivada = existing_area + NEW.area_cultivada
        WHERE VINHA_codigo = NEW.VINHA_codigo AND CASTA_ID = NEW.CASTA_ID;
        
     SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Já existe registo na Base de Dados. Área cultivada atualizada.';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `regiao`
--

CREATE TABLE `regiao` (
  `REGIAO_ID` int(11) NOT NULL,
  `denominacao` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `regiao`
--

INSERT INTO `regiao` (`REGIAO_ID`, `denominacao`) VALUES
(1, 'Alentejo'),
(2, 'Douro'),
(3, 'Dão'),
(4, 'Minho'),
(5, 'Setúbal'),
(6, 'Porto'),
(7, 'Lisboa');

-- --------------------------------------------------------

--
-- Estrutura da tabela `trabalha`
--

CREATE TABLE `trabalha` (
  `PRODUTOR_ID` int(11) NOT NULL,
  `COLABORADOR_numero` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- --------------------------------------------------------

--
-- Estrutura da tabela `trabalhador`
--

CREATE TABLE `trabalhador` (
  `TRABALHADOR_COLABORADOR_numero` int(11) DEFAULT NULL,
  `COLABORADOR_numero` int(11) NOT NULL,
  `funcao` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `trabalhador`
--

INSERT INTO `trabalhador` (`TRABALHADOR_COLABORADOR_numero`, `COLABORADOR_numero`, `funcao`) VALUES
(NULL, 1, NULL),
(NULL, 2, NULL),
(NULL, 3, NULL),
(NULL, 7, NULL),
(NULL, 10, NULL),
(NULL, 11, NULL),
(NULL, 14, NULL),
(NULL, 15, NULL),
(NULL, 18, NULL),
(NULL, 20, NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `vinha`
--

CREATE TABLE `vinha` (
  `codigo` int(11) NOT NULL,
  `nome_vinha` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `vinha`
--

INSERT INTO `vinha` (`codigo`, `nome_vinha`) VALUES
(1, 'Vinha do Sol'),
(2, 'Vinha da Lua'),
(3, 'Vinha das Estrelas'),
(4, 'Vinha do Vale'),
(5, 'Vinha Encosta'),
(6, 'Vinha do Pinhal'),
(7, 'Vinha dos Ventos'),
(8, 'Vinha do Prado'),
(9, 'Vinha do Luar'),
(10, 'Vinha das Pedras'),
(11, 'Vinha da Montanha'),
(12, 'Vinha do Penedo'),
(13, 'Vinha do Horizonte'),
(14, 'Vinha do Mirante'),
(15, 'Vinha do Pinheiro'),
(16, 'Vinha do Castelo'),
(17, 'Vinha do Mar'),
(18, 'Vinha da Serra'),
(19, 'Vinha do Sobreiro'),
(20, 'Vinha do Vale Verde'),
(21, 'Vinha Teste');

-- --------------------------------------------------------

--
-- Estrutura da tabela `vinho`
--

CREATE TABLE `vinho` (
  `REGIAO_ID` int(11) NOT NULL,
  `PRODUTOR_ID` int(11) NOT NULL,
  `VINHO_ID` int(11) NOT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `data_engarraf` date DEFAULT NULL,
  `teor_alcoolico` float DEFAULT NULL,
  `tipo` varchar(30) DEFAULT NULL,
  `classificacao` varchar(30) DEFAULT NULL,
  `aroma` varchar(30) DEFAULT NULL,
  `sabor` varchar(30) DEFAULT NULL,
  `cor` varchar(30) DEFAULT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Extraindo dados da tabela `vinho`
--

INSERT INTO `vinho` (`REGIAO_ID`, `PRODUTOR_ID`, `VINHO_ID`, `nome`, `data_engarraf`, `teor_alcoolico`, `tipo`, `classificacao`, `aroma`, `sabor`, `cor`, `stock`) VALUES
(1, 1, 1, 'Sol do Douro', '2023-05-01', 13, 'Tinto', '5.0', 'Frutado', 'Suave', 'Rubi', 0),
(2, 2, 2, 'Lua do Alentejo', '2022-08-15', 12.5, 'Branco', '4.5', 'Cítrico', 'Fresco', 'Amarelo claro', 0),
(3, 3, 3, 'Estrela do Dão', '2021-10-10', 14, 'Tinto', '4.8', 'Amadeirado', 'Intenso', 'Granada', 0),
(4, 4, 4, 'Néctar Minho', '2023-02-20', 11.5, 'Rosé', '4.2', 'Floral', 'Leve', 'Rosado', 0),
(5, 5, 5, 'Tradição Setúbal', '2020-09-05', 13.5, 'Tinto', '4.9', 'Frutado', 'Encorpado', 'Rubi escuro', 0),
(6, 6, 6, 'Porto Clássico', '2022-03-18', 12, 'Branco', '4.0', 'Fresco', 'Leve', 'Amarelo pálido', 0),
(7, 7, 7, 'Alentejo Reserva', '2023-01-25', 14.5, 'Tinto', '5.0', 'Complexo', 'Aveludado', 'Vermelho escuro', 0),
(1, 8, 8, 'Douro Premium', '2021-06-30', 13, 'Rosé', '4.3', 'Suave', 'Fresco', 'Rosa claro', 0),
(2, 9, 9, 'Alentejo Sublime', '2020-12-12', 11, 'Branco', '3.8', 'Cítrico', 'Leve', 'Amarelo brilhante', 0),
(3, 10, 10, 'Dão Encantado', '2022-11-22', 12.8, 'Tinto', '4.6', 'Frutado', 'Equilibrado', 'Vermelho rubi', 0),
(4, 11, 11, 'Minho Verdejo', '2023-03-15', 13.2, 'Branco', '4.7', 'Frutado', 'Fresco', 'Amarelo dourado', 0),
(5, 12, 12, 'Setúbal Antigo', '2021-07-10', 14, 'Tinto', '4.9', 'Complexo', 'Encorpado', 'Granada', 0),
(6, 13, 13, 'Porto Ruby', '2020-05-20', 12.7, 'Rosé', '4.5', 'Floral', 'Leve', 'Rosa claro', 0),
(7, 14, 14, 'Reserva do Alentejo', '2022-04-18', 13.8, 'Tinto', '5.0', 'Amadeirado', 'Intenso', 'Rubi escuro', 0),
(1, 15, 15, 'Douro Ouro', '2023-06-05', 11.5, 'Branco', '4.0', 'Cítrico', 'Fresco', 'Amarelo pálido', 0),
(2, 16, 16, 'Alentejo Sereno', '2021-01-12', 12, 'Tinto', '4.3', 'Frutado', 'Suave', 'Rubi', 0),
(3, 17, 17, 'Dão Velho', '2020-09-25', 14.2, 'Rosé', '4.6', 'Frutado', 'Leve', 'Rosa intenso', 0),
(4, 18, 18, 'Minho Cristalino', '2022-08-30', 13.5, 'Branco', '4.4', 'Fresco', 'Equilibrado', 'Amarelo esverdeado', 0),
(5, 19, 19, 'Setúbal Doce', '2023-10-20', 14.8, 'Tinto', '5.0', 'Complexo', 'Aveludado', 'Vermelho escuro', 0),
(6, 20, 20, 'Porto Vintage', '2021-12-05', 12.9, 'Rosé', '4.2', 'Floral', 'Fresco', 'Rosa claro', 0);

-- --------------------------------------------------------

--
-- Estrutura stand-in para vista `vinhos_mais_vendidos_por_produtor`
-- (Veja abaixo para a view atual)
--
CREATE TABLE `vinhos_mais_vendidos_por_produtor` (
`produtor` varchar(100)
,`vinho` varchar(50)
,`quantidade_vendida` int(11)
);

-- --------------------------------------------------------

--
-- Estrutura stand-in para vista `vinhos_produtores`
-- (Veja abaixo para a view atual)
--
CREATE TABLE `vinhos_produtores` (
`Produtor` varchar(100)
,`Vinho` varchar(50)
,`Tipo` varchar(30)
,`Teor_Alcoolico` float
,`Região` varchar(50)
,`Ano` int(11)
);

-- --------------------------------------------------------

--
-- Estrutura para vista `castas_mais_produzidas_por_regiao`
--
DROP TABLE IF EXISTS `castas_mais_produzidas_por_regiao`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `castas_mais_produzidas_por_regiao`  AS SELECT `r`.`denominacao` AS `regiao`, `c`.`tipo_uva` AS `casta`, sum(`co`.`quantidade`) AS `quantidade_produzida` FROM (((`regiao` `r` join `colheita` `co` on(`r`.`REGIAO_ID` = `co`.`REGIAO_ID`)) join `produz` `p` on(`p`.`VINHA_codigo` = `co`.`PRODUTOR_ID`)) join `casta` `c` on(`p`.`CASTA_ID` = `c`.`CASTA_ID`)) GROUP BY `r`.`denominacao`, `c`.`tipo_uva` ORDER BY sum(`co`.`quantidade`) DESC LIMIT 0, 25 ;

-- --------------------------------------------------------

--
-- Estrutura para vista `empresas_maiores_compras`
--
DROP TABLE IF EXISTS `empresas_maiores_compras`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `empresas_maiores_compras`  AS SELECT `c`.`numero_cliente` AS `numero`, `c`.`nome_cliente` AS `nome`, `c`.`nif` AS `nif`, `c`.`morada` AS `morada`, count(`f`.`num_fatura`) AS `total_faturas`, sum(`f`.`valor_total`) AS `volume_compras` FROM (`cliente` `c` join `fatura` `f` on(`c`.`numero_cliente` = `f`.`CLIENTE_numero_cliente`)) WHERE `c`.`tipo` = 'Empresa' GROUP BY `c`.`numero_cliente`, `c`.`nome_cliente`, `c`.`nif`, `c`.`morada` ORDER BY sum(`f`.`valor_total`) DESC ;

-- --------------------------------------------------------

--
-- Estrutura para vista `vinhos_mais_vendidos_por_produtor`
--
DROP TABLE IF EXISTS `vinhos_mais_vendidos_por_produtor`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vinhos_mais_vendidos_por_produtor`  AS SELECT `p`.`nome_vinicola` AS `produtor`, `v`.`nome` AS `vinho`, `i`.`quantidade` AS `quantidade_vendida` FROM ((`produtor` `p` join `vinho` `v` on(`p`.`PRODUTOR_ID` = `v`.`PRODUTOR_ID`)) join `item` `i` on(`v`.`VINHO_ID` = `i`.`VINHO_ID`)) WHERE `i`.`quantidade` = (select max(`i2`.`quantidade`) from `item` `i2` where `i2`.`VINHO_ID` = `i`.`VINHO_ID`) GROUP BY `p`.`nome_vinicola`, `v`.`nome`, `i`.`quantidade` ;

-- --------------------------------------------------------

--
-- Estrutura para vista `vinhos_produtores`
--
DROP TABLE IF EXISTS `vinhos_produtores`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vinhos_produtores`  AS SELECT `p`.`nome_vinicola` AS `Produtor`, `v`.`nome` AS `Vinho`, `v`.`tipo` AS `Tipo`, `v`.`teor_alcoolico` AS `Teor_Alcoolico`, `r`.`denominacao` AS `Região`, `e`.`ano` AS `Ano` FROM (((`produtor` `p` join `vinho` `v` on(`v`.`PRODUTOR_ID` = `p`.`PRODUTOR_ID`)) join `regiao` `r` on(`r`.`REGIAO_ID` = `v`.`REGIAO_ID`)) join `edicao` `e` on(`e`.`VINHO_ID` = `v`.`VINHO_ID`)) ORDER BY `p`.`nome_vinicola` ASC ;

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `acompanha`
--
ALTER TABLE `acompanha`
  ADD PRIMARY KEY (`COLHEITA_ID`,`ENOLOGO_COLABORADOR_numero`),
  ADD KEY `FK_ENOLOGO_ACOMPANHA_COLHEITA` (`ENOLOGO_COLABORADOR_numero`);

--
-- Índices para tabela `casta`
--
ALTER TABLE `casta`
  ADD PRIMARY KEY (`CASTA_ID`);

--
-- Índices para tabela `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`numero_cliente`);

--
-- Índices para tabela `colaborador`
--
ALTER TABLE `colaborador`
  ADD PRIMARY KEY (`numero`);

--
-- Índices para tabela `colheita`
--
ALTER TABLE `colheita`
  ADD PRIMARY KEY (`COLHEITA_ID`),
  ADD KEY `FK_COLHEITA_pertence_REGIAO` (`REGIAO_ID`),
  ADD KEY `FK_COLHEITA_destinada_PRODUTOR` (`PRODUTOR_ID`);

--
-- Índices para tabela `contem`
--
ALTER TABLE `contem`
  ADD PRIMARY KEY (`CASTA_ID`,`EDICAO_ID`),
  ADD KEY `FK_EDICAO_CONTEM_CASTA` (`EDICAO_ID`);

--
-- Índices para tabela `devolucao`
--
ALTER TABLE `devolucao`
  ADD PRIMARY KEY (`DEVOLUCAO_ID`),
  ADD KEY `FK_DEVOLUCAO_realiza_CLIENTE` (`CLIENTE_numero_cliente`),
  ADD KEY `FK_DEVOLUCAO_origina_FATURA` (`FATURA_num_fatura`);

--
-- Índices para tabela `devolvido`
--
ALTER TABLE `devolvido`
  ADD PRIMARY KEY (`VINHO_ID`,`DEVOLUCAO_ID`),
  ADD KEY `FK_DEVOLUCAO_DEVOLVIDO_VINHO` (`DEVOLUCAO_ID`);

--
-- Índices para tabela `edicao`
--
ALTER TABLE `edicao`
  ADD PRIMARY KEY (`EDICAO_ID`),
  ADD KEY `FK_EDICAO_tem_VINHO` (`VINHO_ID`);

--
-- Índices para tabela `enologo`
--
ALTER TABLE `enologo`
  ADD PRIMARY KEY (`COLABORADOR_numero`);

--
-- Índices para tabela `fatura`
--
ALTER TABLE `fatura`
  ADD PRIMARY KEY (`num_fatura`),
  ADD KEY `FK_FATURA_emitida_CLIENTE` (`CLIENTE_numero_cliente`);

--
-- Índices para tabela `ganha`
--
ALTER TABLE `ganha`
  ADD PRIMARY KEY (`EDICAO_ID`,`PREMIO_ID`),
  ADD KEY `FK_PREMIO_GANHA_EDICAO` (`PREMIO_ID`);

--
-- Índices para tabela `incide`
--
ALTER TABLE `incide`
  ADD PRIMARY KEY (`COLHEITA_ID`,`VINHA_codigo`),
  ADD KEY `FK_VINHA_INCIDE_COLHEITA` (`VINHA_codigo`);

--
-- Índices para tabela `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`VINHO_ID`,`FATURA_num_fatura`),
  ADD KEY `FK_ITEM_noname_FATURA` (`FATURA_num_fatura`);

--
-- Índices para tabela `participa`
--
ALTER TABLE `participa`
  ADD PRIMARY KEY (`COLHEITA_ID`,`TRABALHADOR_COLABORADOR_numero`),
  ADD KEY `FK_TRABALHADOR_PARTICIPA_COLHEITA` (`TRABALHADOR_COLABORADOR_numero`);

--
-- Índices para tabela `premio`
--
ALTER TABLE `premio`
  ADD PRIMARY KEY (`PREMIO_ID`);

--
-- Índices para tabela `produtor`
--
ALTER TABLE `produtor`
  ADD PRIMARY KEY (`PRODUTOR_ID`),
  ADD KEY `FK_PRODUTOR_tem_REGIAO` (`REGIAO_ID`);

--
-- Índices para tabela `produz`
--
ALTER TABLE `produz`
  ADD PRIMARY KEY (`VINHA_codigo`,`CASTA_ID`),
  ADD KEY `FK_CASTA_PRODUZ_VINHA` (`CASTA_ID`);

--
-- Índices para tabela `regiao`
--
ALTER TABLE `regiao`
  ADD PRIMARY KEY (`REGIAO_ID`);

--
-- Índices para tabela `trabalha`
--
ALTER TABLE `trabalha`
  ADD PRIMARY KEY (`PRODUTOR_ID`,`COLABORADOR_numero`),
  ADD KEY `FK_COLABORADOR_TRABALHA_PRODUTOR` (`COLABORADOR_numero`);

--
-- Índices para tabela `trabalhador`
--
ALTER TABLE `trabalhador`
  ADD PRIMARY KEY (`COLABORADOR_numero`),
  ADD KEY `FK_TRABALHADOR_chefia_TRABALHADOR` (`TRABALHADOR_COLABORADOR_numero`);

--
-- Índices para tabela `vinha`
--
ALTER TABLE `vinha`
  ADD PRIMARY KEY (`codigo`);

--
-- Índices para tabela `vinho`
--
ALTER TABLE `vinho`
  ADD PRIMARY KEY (`VINHO_ID`),
  ADD KEY `FK_VINHO_associado_REGIAO` (`REGIAO_ID`),
  ADD KEY `FK_VINHO_produzido_PRODUTOR` (`PRODUTOR_ID`);

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `acompanha`
--
ALTER TABLE `acompanha`
  ADD CONSTRAINT `FK_COLHEITA_ACOMPANHA_ENOLOGO` FOREIGN KEY (`COLHEITA_ID`) REFERENCES `colheita` (`COLHEITA_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_ENOLOGO_ACOMPANHA_COLHEITA` FOREIGN KEY (`ENOLOGO_COLABORADOR_numero`) REFERENCES `enologo` (`COLABORADOR_numero`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `colheita`
--
ALTER TABLE `colheita`
  ADD CONSTRAINT `FK_COLHEITA_destinada_PRODUTOR` FOREIGN KEY (`PRODUTOR_ID`) REFERENCES `produtor` (`PRODUTOR_ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_COLHEITA_pertence_REGIAO` FOREIGN KEY (`REGIAO_ID`) REFERENCES `regiao` (`REGIAO_ID`) ON UPDATE CASCADE;

--
-- Limitadores para a tabela `contem`
--
ALTER TABLE `contem`
  ADD CONSTRAINT `FK_CASTA_CONTEM_EDICAO` FOREIGN KEY (`CASTA_ID`) REFERENCES `casta` (`CASTA_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_EDICAO_CONTEM_CASTA` FOREIGN KEY (`EDICAO_ID`) REFERENCES `edicao` (`EDICAO_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `devolucao`
--
ALTER TABLE `devolucao`
  ADD CONSTRAINT `FK_DEVOLUCAO_origina_FATURA` FOREIGN KEY (`FATURA_num_fatura`) REFERENCES `fatura` (`num_fatura`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_DEVOLUCAO_realiza_CLIENTE` FOREIGN KEY (`CLIENTE_numero_cliente`) REFERENCES `cliente` (`numero_cliente`) ON UPDATE CASCADE;

--
-- Limitadores para a tabela `devolvido`
--
ALTER TABLE `devolvido`
  ADD CONSTRAINT `FK_DEVOLUCAO_DEVOLVIDO_VINHO` FOREIGN KEY (`DEVOLUCAO_ID`) REFERENCES `devolucao` (`DEVOLUCAO_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_VINHO_DEVOLVIDO_DEVOLUCAO_` FOREIGN KEY (`VINHO_ID`) REFERENCES `vinho` (`VINHO_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `edicao`
--
ALTER TABLE `edicao`
  ADD CONSTRAINT `FK_EDICAO_tem_VINHO` FOREIGN KEY (`VINHO_ID`) REFERENCES `vinho` (`VINHO_ID`) ON UPDATE CASCADE;

--
-- Limitadores para a tabela `enologo`
--
ALTER TABLE `enologo`
  ADD CONSTRAINT `FK_ENOLOGO_COLABORADOR` FOREIGN KEY (`COLABORADOR_numero`) REFERENCES `colaborador` (`numero`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `fatura`
--
ALTER TABLE `fatura`
  ADD CONSTRAINT `FK_FATURA_emitida_CLIENTE` FOREIGN KEY (`CLIENTE_numero_cliente`) REFERENCES `cliente` (`numero_cliente`) ON UPDATE CASCADE;

--
-- Limitadores para a tabela `ganha`
--
ALTER TABLE `ganha`
  ADD CONSTRAINT `FK_EDICAO_GANHA_PREMIO` FOREIGN KEY (`EDICAO_ID`) REFERENCES `edicao` (`EDICAO_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_PREMIO_GANHA_EDICAO` FOREIGN KEY (`PREMIO_ID`) REFERENCES `premio` (`PREMIO_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `incide`
--
ALTER TABLE `incide`
  ADD CONSTRAINT `FK_COLHEITA_INCIDE_VINHA` FOREIGN KEY (`COLHEITA_ID`) REFERENCES `colheita` (`COLHEITA_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_VINHA_INCIDE_COLHEITA` FOREIGN KEY (`VINHA_codigo`) REFERENCES `vinha` (`codigo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `FK_FATURA_ITEM_VINHO` FOREIGN KEY (`FATURA_num_fatura`) REFERENCES `fatura` (`num_fatura`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_ITEM_noname_FATURA` FOREIGN KEY (`FATURA_num_fatura`) REFERENCES `fatura` (`num_fatura`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_ITEM_representa_VINHO` FOREIGN KEY (`VINHO_ID`) REFERENCES `vinho` (`VINHO_ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_VINHO_ITEM_FATURA` FOREIGN KEY (`VINHO_ID`) REFERENCES `vinho` (`VINHO_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `participa`
--
ALTER TABLE `participa`
  ADD CONSTRAINT `FK_COLHEITA_PARTICIPA_TRABALHADOR` FOREIGN KEY (`COLHEITA_ID`) REFERENCES `colheita` (`COLHEITA_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_TRABALHADOR_PARTICIPA_COLHEITA` FOREIGN KEY (`TRABALHADOR_COLABORADOR_numero`) REFERENCES `trabalhador` (`COLABORADOR_numero`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `produtor`
--
ALTER TABLE `produtor`
  ADD CONSTRAINT `FK_PRODUTOR_tem_REGIAO` FOREIGN KEY (`REGIAO_ID`) REFERENCES `regiao` (`REGIAO_ID`) ON UPDATE CASCADE;

--
-- Limitadores para a tabela `produz`
--
ALTER TABLE `produz`
  ADD CONSTRAINT `FK_CASTA_PRODUZ_VINHA` FOREIGN KEY (`CASTA_ID`) REFERENCES `casta` (`CASTA_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_VINHA_PRODUZ_CASTA` FOREIGN KEY (`VINHA_codigo`) REFERENCES `vinha` (`codigo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `trabalha`
--
ALTER TABLE `trabalha`
  ADD CONSTRAINT `FK_COLABORADOR_TRABALHA_PRODUTOR` FOREIGN KEY (`COLABORADOR_numero`) REFERENCES `colaborador` (`numero`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_PRODUTOR_TRABALHA_COLABORADOR` FOREIGN KEY (`PRODUTOR_ID`) REFERENCES `produtor` (`PRODUTOR_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `trabalhador`
--
ALTER TABLE `trabalhador`
  ADD CONSTRAINT `FK_TRABALHADOR_COLABORADOR` FOREIGN KEY (`COLABORADOR_numero`) REFERENCES `colaborador` (`numero`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_TRABALHADOR_chefia_TRABALHADOR` FOREIGN KEY (`TRABALHADOR_COLABORADOR_numero`) REFERENCES `trabalhador` (`COLABORADOR_numero`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Limitadores para a tabela `vinho`
--
ALTER TABLE `vinho`
  ADD CONSTRAINT `FK_VINHO_associado_REGIAO` FOREIGN KEY (`REGIAO_ID`) REFERENCES `regiao` (`REGIAO_ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_VINHO_produzido_PRODUTOR` FOREIGN KEY (`PRODUTOR_ID`) REFERENCES `produtor` (`PRODUTOR_ID`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
