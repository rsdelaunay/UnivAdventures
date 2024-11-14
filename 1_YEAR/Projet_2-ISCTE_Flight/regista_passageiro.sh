#!/bin/bash
# SO_HIDE_DEBUG=1                   ## Uncomment this line to hide all @DEBUG statements
# SO_HIDE_COLOURS=1                 ## Uncomment this line to disable all escape colouring
. ./so_utils.sh                     ## This is required to activate the macros so_success, so_error, and so_debug

###############################################################################
## ISCTE-IUL: Trabalho prático de Sistemas Operativos 2023/2024, Enunciado Versão 3+
##
## Aluno: Nº: 122123       Nome: Rodrigo Miguel da Silva Delaunay
## Nome do Módulo: S1. Script: regista_passageiro.sh
## Descrição/Explicação do Módulo:
## O presente script visa proceder-se ao registo ou atualização do saldo de um utilizador da plataforma IscteFlight.
## Para o efeito, recebe como argumentos o Nome do usuário (cruzado com a base de dados presente no servidor tigre), 
## senha, saldo e NIF. Estes dados são guardados no ficheiro passageiros.txt, porém, antes de o serem, são, naturalmente,
## devidamente validados.
###############################################################################

## Este script é invocado quando um novo passageiro se regista na plataforma IscteFlight. Este script recebe todos os dados por argumento, na chamada da linha de comandos. Os passageiros são registados no ficheiro passageiros.txt. Deve receber as informações do passageiro como argumentos pela seguinte ordem: <Nome:string> <Senha:string>  <Saldo a adicionar:number> [<NIF:number>]

## S1.1. Valida os argumentos passados e os seus formatos:
## S1.1.1. Valida os argumentos passados, avaliando se são em número suficiente (mínimo 3, máximo 4). Em caso de erro, dá so_error S1.1.1 e termina. Caso contrário, dá so_success S1.1.1.

if [ "$#" -ge 3 ] && [ "$#" -le 4 ]; then ## se número argumentos greater or equal than 3 (maior ou igual a 3); less/equal (menor ou igual a) 4;
	so_success S1.1.1 #sucesso, pois ou são 3 ou 4 argumentos
else 
	so_error S1.1.1 #caso contrário erro.
	exit
fi

## S1.1.2. Valida se o argumento <Nome> corresponde ao nome de um utilizador do servidor Tigre. Se não corresponder ao nome de nenhum utilizador do Tigre, dá so_error S1.1.2 e termina. Senão, dá so_success S1.1.2.

if grep -Fq -w "$1" /etc/passwd; then #grep para procurar, -q para não dar output, $1 primeiro argumento (<Nome>), /etc/passwd listagem users tigre 
	so_success S1.1.2
else
	so_error S1.1.2
	exit
fi

## S1.1.3. Valida se o argumento <Saldo a adicionar> tem formato “number” (inteiro positivo ou 0). Se não tiver, dá so_error S1.1.3 e termina. Caso contrário, dá so_success S1.1.3.

if [[ $3 =~ ^[0-9]+$ ]]; then # [[]] para bloco estrutura condicional complexa; =~ para verificar igualdade; 
#^ para ler string do início; [0-9] dígitos entre 0 e 9; + indica que podem ser vários dígitos; $ termina string
#Isto irá limitar existência de caracteres no meio do argumento dado.
	so_success S1.1.3
else
	so_error S1.1.3
	exit
fi

## S1.1.4. Valida se o argumento opcional <NIF> (só no caso de ser passado, i.e., se tiver valor) tem formato “number” com 9 (nove) dígitos. Se não for, dá so_error S1.1.4 e termina. Caso contrário, dá so_success S1.1.4.

if [ $# -ge 4 ]; then #caso o numero de argumentos seja maior ou igual a quatro - ou seja, caso tenha o NIF incluido, faz a seguinte validação:
	if [[ "$4" =~ ^[0-9]{9}$ ]]; then #$4 --> NIF. ^[0-9] para ler dígitos entre 0 e 9 na string,{9} indica que o argumento terá que ter exatamente 9 números; $ fim.
	so_success S1.1.4 #caso cumpra o estipulado anteriormente, sucesso.
	else
	so_error S1.1.4
	exit
	fi
fi

## S1.2. Associa os dados passados com a base de dados dos passageiros registados:
## S1.2.1. Verifica se o ficheiro passageiros.txt existe. Se o ficheiro existir, dá so_success S1.2.1 e continua no passo S1.2.3. Se não existir, dá so_error S1.2.1, e continua.

if [ -e "passageiros.txt" ]; then #procura na diretoria atual se o ficheiro passageiros.txt existe. -e retorna verdadeiro se o ficheiro existir.
	so_success S1.2.1
	ficheiro_existe=0 #Definimos esta variável com valor 0 para indicar que o ficheiro existe e foi encontrado com sucesso. Posteriormente servirá para correr S1.2.2 ou não.
else
	so_error S1.2.1
	ficheiro_existe=1 #Ficheiro não existe, logo não há exit porque pretende-se dar continuidade em S1.2.2.
fi

## S1.2.2. Cria o ficheiro passageiros.txt. Se der erro, dá so_error S1.2.2 e termina. Senão, dá so_success S1.2.2.

if [ $ficheiro_existe = 1 ]; then #Se ficheiro não existe
	touch passageiros.txt #Cria ficheiro passageiros.txt 
	if [ $? -eq 0 ]; then #Verifica se criacao foi bem sucedida ($?) - nomeadamente se deu igual a 0, que indica sucesso.
    	so_success S1.2.2
	else
    so_error S1.2.2 #diferente de 0, logo deu erro.
	exit
	fi
fi

## S1.2.3. Caso o passageiro <Nome> passado já exista no ficheiro passageiros.txt, dá so_success S1.2.3, e continua no passo S1.3.  Senão, dá so_error S1.2.3, e continua.

if grep -F "$1" passageiros.txt; then #Procura nome no ficheiro passageiros.txt. -F para procurar string exatamente como é
   	so_success S1.2.3
	existe_passageiro=0 #caso passageiro exista, criamos esta variável para guardar o sucesso de o termos encontrado.
	#Como se pretende que se siga para S1.3., utilizaremos esta variável nessa alínea.
else
    so_error S1.2.3
	existe_passageiro=1 #passageiro nao existe - deu erro na procura do mesmo.
	#Continua, logo não há exit.
fi

## S1.2.4. Como o passageiro <Nome> não existe no ficheiro, terá de o registar. Para isso, valida se <NIF> (campo opcional) foi mesmo passado. Se não foi, dá so_error S1.2.4 e termina. Senão, dá so_success S1.2.4.

if [ $existe_passageiro = 1 ]; then #Caso passageiro NÃO exista
	if [[ "$4" =~ ^[0-9]{9}$ ]]; then #$4 --> NIF. Verificamos se foi efetivamente passado e se tem os 9 dígitos.
		so_success S1.2.4 #caso não cumpra a formatação anterior, sucesso.
	else
		so_error S1.2.4 
		exit
	fi
fi

## S1.2.5. Define o campo <ID_passageiro>, como sendo o UserId Linux associado ao utilizador de nome <Nome> no servidor Tigre. Em caso de haver algum erro na operação, dá so_error S1.2.5 e termina. Caso contrário, dá so_success S1.2.5 <ID_passageiro> (substituindo pelo campo definido).
if [ $existe_passageiro = 1 ]; then #Caso passageiro NÃO exista
	ID_passageiro=$(grep -F "$1" /etc/passwd | head -n 1 |cut -d: -f1) 
	#grep para procurar nome na lista tigre; -F para procurar por string exata em /etc/passwd; | pipe para passar para outro
	#comando; head -n 1 para utilizar somente a primeira linha que se obteve do grep anterior (nao selecionar varias pessoas); cut -d: para cortar
	#delimitando à primeira coluna (f1) que é o número de usuario do tigre.

	#Verificar sucesso da operacao anterior
	if [ $? -eq 0 ]; then #caso tenha sido efetuada com sucesso, sucesso.
		so_success S1.2.5 $ID_passageiro
	else
    	so_error S1.2.5
		exit
	fi
fi	

## S1.2.6. Define o campo <Email>, gerado a partir do <Nome> introduzido pelo utilizador, usando apenas o primeiro e o último nome (dica: https://moodle23.iscte-iul.pt/mod/forum/discuss.php?d=5344), convertendo-os para minúsculas apenas, colocando um ponto entre os dois nomes, e domínio iscteflight.pt. Assim sendo, um exemplo seria “david.gabriel@iscteflight.pt”. Se houver algum erro na operação (e.g., o utilizador “root” tem menos de 2 nomes), dá so_error S1.2.6 e termina. Caso contrário, dá so_success S1.2.6 <Email> (substituindo pelo campo gerado). Ao registar um novo passageiro no sistema, o número inicial de <Saldo> tem o valor 0 (zero).

if [ $existe_passageiro = 1 ]; then #Caso passageiro NÃO exista
	if [ $(echo "$1" | grep -o ' ' | wc -l) -lt 1 ]; then #caso o nome fornecido não contenha dois nomes, da logo erro.
    #grep -o ' ' conta os espaços em branco no nome, (arg $1). Caso seja lower than 1 quer dizer que não há espaços, portanto só há um nome.
    	so_error S1.2.6
		exit
	fi

	# Extrair o primeiro e o último nome do nome fornecido (primeiro argumento)
	primeiro_nome=$(echo "$1" | awk '{print tolower($1)}') #awk para processar, print to lower para meter tudo em lower case
	ultimo_nome=$(echo "$1" | awk '{print tolower($NF)}') #igualmente para o último nome. NF --> elemento final

	# Criar o e-mail ao adicionar primeiro_nome.ultimo_nome@iscteflight.pt (domínio)
	Email="$primeiro_nome.$ultimo_nome@iscteflight.pt"
	Saldo="0.0" #definir saldo como 0.0. Importa notar que posteriormente caso queira juntar saldo, terei que tornar esta variável numérica

	# Por fim, verificar se a operação foi bem sucedida.
	if [ $? -eq 0 ]; then # 0 --> sucesso
    	so_success S1.2.6 $Email
	else
    	so_error S1.2.6
		exit
	fi
fi

## S1.2.7. Regista o utilizador numa nova linha no final do ficheiro passageiros.txt, seguindo a sintaxe:   <ID_passageiro>:<NIF>:<Nome>:<Email>:<Senha>:<Saldo>. Em caso de haver algum erro na operação (e.g., erro na escrita do ficheiro), dá so_error S1.2.7 e termina. Caso contrário, dá so_success S1.2.7 <linha> (substituindo pela linha completa escrita no ficheiro).

if [ $existe_passageiro = 1 ]; then #Caso passageiro NÃO exista
	#Nota: NIF - $4; Nome - $1; Senha - $2; Id_passageiro, E-mail e Saldos feitos anteriormente.
	NIF="$4"
	Nome="$1"
	Senha="$2"

	# Linha a ser adicionada ao ficheiro passageiros.txt por forma a registar o passageiro
	linha_nova="$ID_passageiro:$NIF:$Nome:$Email:$Senha:$3"

	echo "$linha_nova" >> passageiros.txt # >> para escrever numa linha nova

	if [ $? -eq 0 ]; then #novamente verificar se a operação anterior foi efetuada com sucesso.
    	so_success S1.2.7
	else
		so_error S1.2.7
		exit
	fi
fi

## S1.3. Adiciona créditos na conta de um passageiro que existe no ficheiro passageiros.txt:
## S1.3.1. Tendo já encontrado um “match” passageiro com o Nome <Nome> no ficheiro, valida se o campo <Senha> passado corresponde à senha registada no ficheiro. Se não corresponder, dá so_error S1.3.1 e termina. Caso contrário, dá so_success S1.3.1.

if [ $existe_passageiro = 0 ]; then #Caso passageiro EXISTA (foi sucesso tê-lo encontrado anteriormente)
	senha_registada=$(grep -F "$1" passageiros.txt | cut -d':' -f5) #retira a coluna 5 da linha correspondente ao user identificado através de grep -F (string exata)
	if [ "$2" != "$senha_registada" ]; then #se o segundo argumento inicial, que era a password, for diferente da senha registada:
    	so_error S1.3.1 #erro
		exit
	else
    	so_success S1.3.1
	fi
fi

## S1.3.2. Mesmo que tenha sido passado um campo <NIF> (opcional), ignora-o. Adiciona o valor passado do campo <Saldo a adicionar> ao valor do <Saldo> registado no ficheiro passageiros.txt para o passageiro em questão, atualizando esse valor no ficheiro passageiros.txt. Se houver algum erro na operação (e.g., erro na escrita do ficheiro), dá so_error S1.3.2 e termina. Caso tudo tenha corrido bem, dá o resultado so_success S1.3.2 <Saldo> (substituindo pelo valor saldo atualizado no ficheiro passageiros.txt).

if [ $existe_passageiro = 0 ]; then #Caso passageiro EXISTA 
	linha=$(grep -F "$1" passageiros.txt) #procura pelo nome no ficheiro passageiros.txt exatamente (-F) como argumento 1

	# Extrair o saldo atual do passageiro utilizando a linha retirada anteriormente
	saldo_atual=$(echo "$linha" | cut -d: -f6) #como o saldo é a coluna 6, cut coluna 6

	# Novo saldo -> saldo atual  + saldo a adicionar (arg 3)
	novo_saldo=$(echo "$saldo_atual + $3" | bc) # Como o argumento 3 e o saldo guardado no ficheiro txt são strings, para somar terá que ser desta forma. 
	#bc --> operações matemáticas

	# Substituir o saldo atual pelo novo saldo numa linha nova
	linha_atualizada=$(echo "$linha" | awk -F: -v novo_saldo="$novo_saldo" '{$6 = novo_saldo; output = $1; for (i = 2; i <= NF; i++) {output = output ":" $i}; print output}')
	#awk para processar o texto, -F: para delimitar colunas por ":", definição da variável novo_saldo (criada dentro do awk),
	# posteriormente substituir coluna seis pelo novo_saldo; por fim, loop (ciclo for) para reescrever restantes colunas (print ouput), espaçamento (":"). NF é o limite das colunas identificadas.
	# isto tudo acaba por ser guardado na variável linha_atualizada.

	# Escrever a linha atualizada de volta no ficheiro passageiros.txt
	sed -i "s|$(echo "$linha" | sed 's/[\&/]/\\&/g')|$(echo "$linha_atualizada" | sed 's/[\&/]/\\&/g')|" passageiros.txt

	#sed para editar e substituir uma linha especifica, -i para mudar no ficheiro diretamente, s|search_pattern|replacement_string| para substituir $linha por $linha_atualizada no ficheiro passageiros.txt.
	
	if [ $? -eq 0 ]; then #por fim, verificar se houve sucesso na operação anterior (=0).
    	so_success S1.3.2 $novo_saldo #caso afirmativo, fazer display do sucesso e o novo saldo.
	else
    	so_error S1.3.2
		exit 1
	fi
fi
## S1.4. Lista todos os passageiros registados, mas ordenados por saldo:
## S1.4.1. O script deve criar um ficheiro chamado passageiros-saldos-ordenados.txt igual ao que está no ficheiro passageiros.txt, com a mesma formatação, mas com os registos ordenados por ordem decrescente do campo <Saldo> dos passageiros. Se houver algum erro (e.g., erro na leitura ou escrita do ficheiro), dá so_error S1.4.1, e termina. Caso contrário, dá so_success S1.4.1.

sort -t: -k6,6nr passageiros.txt > passageiros-saldos-ordenados.txt #sort para classificar linhas; -t: para definir delimitador entre dados ":";
#-k6,6 - classificar 6ª coluna, referente aos saldos; nr - ordenação numérica e em reverso (do maior para o menor); passageiros.txt (ficheiro sorted) > (a guardar) num novo ficheiro passageiros-saldos-ordenados.txt

if [ $? -eq 0 ]; then #por fim, verificar se houve sucesso na operação anterior (=0).
    so_success S1.4.1
else
    so_error S1.4.1
	exit 1
fi