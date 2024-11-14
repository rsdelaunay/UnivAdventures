#!/bin/bash
# SO_HIDE_DEBUG=1                   ## Uncomment this line to hide all @DEBUG statements
# SO_HIDE_COLOURS=1                 ## Uncomment this line to disable all escape colouring
. ./so_utils.sh                     ## This is required to activate the macros so_success, so_error, and so_debug

###############################################################################
## ISCTE-IUL: Trabalho prático de Sistemas Operativos 2023/2024, Enunciado Versão 3+
##
## Aluno: Nº: 122123      Nome: Rodrigo Miguel da Silva Delaunay
## Nome do Módulo: S4. Script: stats.sh
## Descrição/Explicação do Módulo:
## O presente script obtém informações sobre o sistema IscteFlight e elabora um ficheiro stats.txt que, conforme
## input por parte do utilizador, identifica (i) o nome de todos os utilizadores que fizeram reservas, por ordem decrescente de número de 
## reservas efetuadas, e mostrando o seu valor total de compras ou; (ii) os <nr> voos mais rentáveis (que tiveram melhores receitas 
###############################################################################

## Este script obtém informações sobre o sistema, afixando resultados diferentes no STDOUT consoante os argumentos passados na sua invocação. A sintaxe resumida é: ./stats.sh <passageiros>|<top <nr>>

## S4.1. Validações:
## S4.1.1. Valida os argumentos recebidos e, conforme os mesmos, o número e tipo de argumentos recebidos. Se não respeitarem a especificação, dá so_error e termina. Caso contrário, dá so_success.

# Verifica se o número de argumentos é diferente de um e dois, limitando a possibilidade de serem dados menos argumentos ou mais
if [ "$#" -ne 1 ] && [ "$#" -ne 2 ]; then 
     so_error S4.1.1
    exit 1
fi

# Se houver dois argumentos, verifica se o primeiro é "top" e se o segundo é um número maior que zero
if [ "$#" -eq 2 ]; then #equal 2
    if [ "$1" != "top" ] || ! [[ "$2" =~ ^[1-9][0-9]*$ ]]; then #se o $1 for diferente de top ou $2 for diferente de dígito entre 1 e 9
        so_error S4.1.1
        exit 1
    fi
fi

# Se houver apenas um argumento, verifica se é "passageiros"
if [ "$#" -eq 1 ] && [ "$1" != "passageiros" ]; then 
    so_error S4.1.1
    exit 1
fi


# Caso não tenha contrariado nenhuma das validações anteriormente expostas, sucesso.
so_success S4.1.1

## S4.2. Invocação do script:
## S4.2.1. Se receber o argumento passageiros, (i.e., ./stats.sh passageiros) cria um ficheiro stats.txt onde lista o nome de todos os utilizadores que fizeram reservas, por ordem decrescente de número de reservas efetuadas, e mostrando o seu valor total de compras. Em caso de erro (por exemplo, se não conseguir ler algum ficheiro necessário), dá so_error e termina. Caso contrário, dá so_success e cria o ficheiro. Em caso de empate no número de reservas, lista o primeiro do ficheiro. Preste atenção ao tratamento do singular e plural quando se escreve “reserva” no ficheiro).

if [ "$1" = "passageiros" ]; then #se o primeiro argumento era passageiros, então faz esta alínea.
    relatorio_reservas="relatorio_reservas.txt" #para ir buscar informações das reservas de voo realizadas
    passageiros_file="passageiros.txt" #para ir buscar infos passageiros

    if [ ! -f "$relatorio_reservas" ]; then #caso ficheiro de relatório de reservas não exista
        so_error S4.2.1 #erro
    fi
    if [ ! -f "$passageiros_file" ]; then #caso ficheiro passageiros.txt não exista,
       so_error S4.2.1 #erro
    fi

    # Função para calcular o número de reservas e respetivo preço total de cada utilizador. Será posteriormente utilizada para escrever no ficheiro stats.txt
     calcular_reservas() {
        # Associar os nomes dos utilizadores com o número total de reservas efetuadas e o custo total das mesmas
        awk -F: 'NR==FNR {n[$1]=$3; next} {n_reservas_passageiros[$6]++; total[$6]+=$5} END {for (passageiro in n_reservas_passageiros) printf "%s: %d reserva%s; %d€\n", n[passageiro], n_reservas_passageiros[passageiro], (n_reservas_passageiros[passageiro] == 1 ? "" : "s"), total[passageiro] | "sort -t: -k2 -rn" }' "$passageiros_file" "$relatorio_reservas"
        #Explicação desta linha complexa:
        #
        # awk para processamento de texto; -F: para utilizar como delimitador entre dados lidos ":"
        # NR==FNR {n[$1]=$3; next} --> Condição que irá resultar na leitura do primeiro ficheiro - passageiros.txt
            #NR representa o número da linha atual e FNR representa o número da linha atual do arquivo corrente. 
            #Quando o awk processa o ficheiro passageiros.txt, NR e FNR serão iguais, o que indica que o awk está a ler o primeiro arquivo.
            #Nesse caso, ele armazena o nome do passageiro (como $1 para posteriormente ser a primeira coluna do ficheiro stats.txt) associado ao número total de reservas ($3 é o nome completo do usuário no ficheiro passageiros.txt) no array n.
            #O next faz com que o awk vá para a próxima linha.
        # {usuarios[$6]++; total[$6]+=$5} --> condição para awk ler o segundo ficheiro - relatorio_reservas.txt
            #Conta o número de reservas para cada passageiro, incrementando o valor por passageiro (coluna $6 do relatorio_reservas.txt) e armazenando no array "n_reservas_passageiros".
            #Calcula o custo total das reservas para cada passageiro (em euros, somando o preço das reservas da coluna $5 relatorio_reservas.txt) e armazena no array "total".
        #END {for (usuario in n_reservas_passageiros) printf "%s:%d reserva%s; %d€\n", n[passageiro], n_reservas_passageiros[passageiro], (n_reservas_passageiros[passageiro] == 1 ? "" : "s"), total[passageiro] | "sort -t: -k2 -rn" }' "$passageiros_file" "$relatorio_reservas"
            #Executado após terminar o processamento awk referido anteriormente.
            #Ciclo for para percorrer o array n_reservas_passageiros e fazer print do número de reservas e preço total das mesmas por passageiro, conforme indicado no enunciado.
            #Importa denotar que se criou a condição(n_reservas_passageiros[passageiro] == 1 ? "" : "s") para caso o número de reservas do passageiro ser igual a 1, fazer print de "reserva" em vez de "reservas"
            #Por fim, pipe | "sort -t: -k2 -rn" }' "$passageiros_file" "$relatorio_reservas" para fazer sort -t: (delimitador :), -k2 coluna 2, -rn por ordem reversa (decrescente). 
            #"$passageiros_file" e "$relatorio_reservas" para indicar os ficheiros que serão processados pelo awk.
    }

    # Função para organizar os passageiros e as suas reservas conforme enunciado
    output="stats.txt" #declaração variável output com nome do ficheiro stats.txt que pretendemos desenvolver.
    calcular_reservas > "$output" #irá correr a função desenvolvida anteriormente e irá guardar no ficheiro stats.txt. > para escrever de início
    if [ $? -ne 0 ]; then #caso operação anterior tenha dado erro (output diferente de 0) - erro.
        so_error S4.2.1
        exit 1
    fi
    so_success S4.2.1
    exit 0
    

else
    so_error S4.2.1
fi

## S4.2.2. Se receber o argumento top <nr:number>, (e.g., ./stats.sh top 4), cria um ficheiro stats.txt onde lista os <nr> (no exemplo, os 4) voos mais rentáveis (que tiveram melhores receitas de vendas), por ordem decrescente. Em caso de erro (por exemplo, se não conseguir ler algum ficheiro necessário), dá so_error e termina. Caso contrário, dá so_success e cria o ficheiro. Em caso de empate, lista o primeiro do ficheiro.

if [ "$1" = "top" ]; then #se o primeiro argumento em vez de ser passageiros foi "top", dá-se seguimento ao seguinte comando
    # Verifica se o arquivo de relatório de reservas existe
    if [ ! -e "relatorio_reservas.txt" ]; then #-e para exists
        so_error S4.2.2
        exit 1
    fi
    # Conta o número de voos diferentes no arquivo de relatório de reservas
    num_voos=$(awk -F: '!a[$2]++ {count++} END {print count}' relatorio_reservas.txt)

    # Verifica se o número fornecido como entrada é maior do que o número de voos diferentes
    if [ "$2" -gt "$num_voos" ]; then
     so_error S4.2.2
     exit 1
    fi

    # Calcula o total de reservas e o custo total das mesmas por número de voo
    awk -F: '{reservas[$2]+=$5} END {for (voo in reservas) printf "%s: %d€\n", voo, reservas[voo] | "sort -t: -k2 -rn"}' relatorio_reservas.txt | head -n "$2" > stats.txt

    # Verifica se o comando awk foi executado corretamente
    if [ "$?" -ne 0 ]; then
        so_error S4.2.2
        exit 1
    fi
    so_success S4.2.2
    exit 0
fi


