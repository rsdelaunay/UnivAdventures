#!/bin/bash
# SO_HIDE_DEBUG=1                   ## Uncomment this line to hide all @DEBUG statements
# SO_HIDE_COLOURS=1                 ## Uncomment this line to disable all escape colouring
. ./so_utils.sh                     ## This is required to activate the macros so_success, so_error, and so_debug

###############################################################################
## ISCTE-IUL: Trabalho prático de Sistemas Operativos 2023/2024, Enunciado Versão 3+
##
## Aluno: Nº: 122123      Nome: Rodrigo Miguel da Silva Delaunay
## Nome do Módulo: S3. Script: estado_voos.sh
## Descrição/Explicação do Módulo:
## O presente script visa estruturar a criação de uma página HTML, chamada voos_disponiveis.html, onde lista os voos com lugares disponíveis
## que pertencem à plataforma IscteFlight. Para a atualização da página em questão, define ainda um script ficheiro cron.def
## para atualizar as informações dos voos em questão a cada hora.
###############################################################################

## Este script não recebe nenhum argumento, e é responsável pelo relatório do estado dos voos que pertencem à plataforma IscteFlight.

## S3.1. Validações:
## S3.1.1. O script valida se o ficheiro voos.txt existe. Se não existir, dá so_error e termina. Senão, dá so_success.

if [ ! -f "voos.txt" ]; then # if com [] para teste; ! nega, -f procura existencia do file voos.txt
    so_error S3.1.1 #caso não exista, erro.
    exit 1
else
    so_success S3.1.1
fi

## S3.1.2. O script valida se os formatos de todos os campos de cada linha do ficheiro voos.txt correspondem à especificação indicada em S2, nomeadamente se respeitam os formatos de data e de hora. Se alguma linha não respeitar, dá so_error <conteúdo da linha> e termina. Caso contrário, dá so_success.

while IFS= read -r linha; do
     formato="^([A-Z]{2}[0-9]{4}):[A-Za-z ]+:[A-Za-z ]+:[0-9]{4}-[0-9]{2}-[0-9]{2}:[0-9]{2}h[0-9]{2}:[0-9]+:[0-9]+:[0-9]+$"
     #Definição de variável formato conforme previsto no enunciado, com colunas separadas por ':' 
     #(1) NrVoo: duas letras [A-Z] e quatro dígitos [0-9];
     #(2) e (3) Origem e Destino: Letras maiusculas e minusculas com espaços incluídos;
     #(4) Data Partida: formato AAAA-MM-DD de números [0-9]
     #(5) Hora Partida: formato HHhMM em que também terão que ser dígitos [0-9]
     #(6) Preço, (7) Lugares totais e (8) Lugares disponíveis: Dígitos [0-9].

     #Validação da linha à luz do formato
    if [[ ! $linha =~ $formato ]]; then #se for diferente
        so_error S3.1.2 "$linha"
        exit 1
    fi

    #Condições especiais que importa validar (algumas acabam por se verificar com o formato disposto em cima, nomeadamente limita inserção de lugares e datas negativos)
    #1. Hora e Minutos
    #Extrair hora e minutos da linha em análise
    hora_min=$(echo "$linha" | cut -d':' -f5) #extrair hora e minutos como um todo da coluna 5
    hora=$(echo "$hora_min" | cut -d'h' -f1 | sed 's/^0*//') #remover zero à esquerda e separar por hora/min
    min=$(echo "$hora_min" | cut -d'h' -f2 | sed 's/^0*//') #remover zero à esquerda e separar por hora/min

    # Verificar se a hora e minutos estão dentro do intervalo permitido
    if [[ "$hora" -gt 23 || "$hora" -lt 0 || "$min" -gt 59 || "$min" -lt 0 ]]; then #horas até 23, minutos ate 59 e valores não negativos!
       so_error S3.1.2
       echo "$linha - Horas de voo inválidas" #caso não se cumpra o disposto anteriormente, erro.
       exit 1
    fi

done < "voos.txt" #fechar voos.txt

if [ $? -eq 0 ]; then #por fim, verificar se houve sucesso na operação anterior (=0).
    so_success S3.1.2
else
    so_error S3.1.2 "$linha"
	exit 1
fi

## S3.2. Processamento do script:
## S3.2.1. O script cria uma página em formato HTML, chamada voos_disponiveis.html, onde lista os voos com lugares disponíveis, indicando nº, origem, destino, data, hora, lotação, nº de lugares disponíveis, e nº de lugares ocupados (para isso deve calcular a diferença dos anteriores). Em caso de erro (por exemplo, se não conseguir escrever no ficheiro), dá so_error e termina. Caso contrário, dá so_success.
 
html_file="voos_disponiveis.html" #conforme enunciado, nome tem que ser voos_disponiveis.html

# Verificar se o arquivo já existe e eliminar se sim
if [ -f "$html_file" ]; then # -f para procurar o file com nome do html_file
    rm "$html_file" #remove
fi

data_hora_atual=$(date +"%Y-%m-%d %H:%M:%S") #guardar data e hora atual formato AAAA-MM-dd; HH:MM:ss para header conforme enunciado

echo "<html><head><meta charset=\"UTF-8\"><title>IscteFlight: Lista de Voos Disponíveis</title></head>" > "$html_file"

echo "<body><h1>Lista atualizada em $data_hora_atual</h1>" >> "$html_file" #guardar primeira linha conforme exemplo dado pelo prof para header do website. código html

# Considerando que existem diversos voos disponíveis, é preferível criar uma função para adicionar informações de voo ao ficheiro html a ser produzido
adicionar_voo_html() { 
    #echo estrutura das linhas de cada voo conforme enunciado website fornecido
    echo "<h2>Voo: $1, De: $2 Para: $3, Partida em $4</h2>" >> "$html_file" #$1, $2 e $3 serão, respetivamente, n.º voo, origem e destino. $4 será hora de partida. Guardar linha por linha no ficheiro html
    echo "<ul>" >> "$html_file" 
    echo "<li><b>Lotação:</b> $5 Lugares</li>" >> "$html_file"
    echo "<li><b>Lugares Disponíveis:</b> $6 Lugares</li>" >> "$html_file"
    echo "<li><b>Lugares Ocupados:</b> $7 Lugares</li>" >> "$html_file"
    echo "</ul>" >> "$html_file"
}

# Ler informações de voo do arquivo voos.txt e adicionar ao arquivo HTML
while IFS=: read -r linha; do
    voo=$(echo "$linha" | cut -d ':' -f 1) #cut 1a coluna do ficheiro voos.txt - NR VOO
    origem=$(echo "$linha" | cut -d ':' -f 2) #cut 2a coluna do ficheiro voos.txt - origem
    destino=$(echo "$linha" | cut -d ':' -f 3) #cut 3a coluna do ficheiro voos.txt - destino
    data=$(echo "$linha" | cut -d ':' -f 4) #cut 4a coluna do ficheiro voos.txt - data
    hora_partida=$(echo "$linha" | cut -d ':' -f 5) #cut 5a coluna do ficheiro voos.txt - partida
    lotacao=$(echo "$linha" | cut -d ':' -f 7) #cut 7a coluna do ficheiro voos.txt - lotacao total do voo 
    lugares_disponiveis=$(echo "$linha" | cut -d ':' -f 8) #cut 8a coluna do ficheiro voos.txt - lugares disponiveis
    lugares_ocupados=$(expr "$lotacao" - "$lugares_disponiveis") #expr - expressão aritmética de dois números guardados em strings

    if [ "$lugares_disponiveis" -ne 0 ]; then 
        adicionar_voo_html "$voo" "$origem" "$destino" "$data $hora_partida" "$lotacao" "$lugares_disponiveis" "$lugares_ocupados"
    fi
done < "voos.txt"

# Fechar o arquivo HTML
echo "</body></html>" >> "$html_file"

# Verificar se a operação foi bem-sucedida
if [ $? -eq 0 ]; then
    so_success S3.2.1
else
    so_error S3.2.1
    exit 1
fi


## S3.3. Invocação do script estado_voos.sh:
## S3.3.1. Altere o ficheiro cron.def fornecido, por forma a configurar o seu sistema para que o script seja executado de hora em hora, diariamente. Nos comentários no início do ficheiro cron.def, explique a configuração realizada, e indique qual o comando que deveria utilizar para despoletar essa configuração. O ficheiro cron.def alterado deverá ser submetido para avaliação juntamente com os outros Shell scripts.

#Ver ficheiro cron.def enviado em anexo ao presente trabalho.