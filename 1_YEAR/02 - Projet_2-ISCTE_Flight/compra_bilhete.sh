#!/bin/bash
# SO_HIDE_DEBUG=1                   ## Uncomment this line to hide all @DEBUG statements
# SO_HIDE_COLOURS=1                 ## Uncomment this line to disable all escape colouring
. ./so_utils.sh                     ## This is required to activate the macros so_success, so_error, and so_debug

###############################################################################
## ISCTE-IUL: Trabalho prático de Sistemas Operativos 2023/2024, Enunciado Versão 3+
##
## Aluno: Nº: 122123       Nome: Rodrigo Miguel da Silva Delaunay
## Nome do Módulo: S2. Script: compra_bilhete.sh
## Descrição/Explicação do Módulo:
## O presente script permite ao passageiro comprar um bilhete para um voo da lista de voos disponíveis (com lugares disponíveis).
## Para o efeito, o passageiro deverá fazer login para confirmar sua identidade e saldo disponível. Posteriormente, após inserir a cidade de origem
## ou destino do voo, é solicitado ao utilizador para escolher um dos voos disponíveis. Naturalmente, o passageiro só poderá comprar
## bilhetes para os voos cujo preço seja inferior ao saldo disponível. Após a reserva ser efetuada, a mesma é armazenada num ficheiro .txt dedicado para o efeito.
###############################################################################

## Este script não recebe nenhum argumento, e permite que o passageiro compre um bilhete para um voo da lista de voos disponíveis. Para realizar a compra, o passageiro deve fazer login para confirmar sua identidade e saldo disponível. Os voos disponíveis estão listados no ficheiro voos.txt. Se não quiser produzir o seu próprio ficheiro, pode utilizar o ficheiro exemplo fornecido, dando o comando: cp voos-exemplo.txt voos.txt. O mesmo procedimento pode ser realizado também com o ficheiro passageiros.txt.

## S2.1. Validações e Pedido de informações interativo:
## S2.1.1. O script valida se os ficheiros voos.txt e passageiros.txt existem. Se algum não existir, dá so_error e termina. Caso contrário, dá so_success.

if [ ! -e "voos.txt" ] || [ ! -e "passageiros.txt" ]; then # if com || OR; [] para teste; ! nega, -e procura existencia de file; voos.txt e passageiros.txt ficheiros procurados
    so_error S2.1.1 #caso algum dos ficheiros nao exista, erro.
    exit 1
else
    so_success S2.1.1
fi

## S2.1.2. Na plataforma é possível consultar os voos pela sua <Origem> ou <Destino>. Pedindo "Insira a cidade de origem ou destino do voo: _". O utilizador insere a cidade Origem ou Destino do voo (o interesse é que pesquise nos 2 campos). Caso o utilizador tenha introduzido uma cidade que não exista no ficheiro voos.txt, ou se não existirem voos com lugares disponíveis com origem ou destino nessa cidade, dá so_error e termina. Caso contrário, dá so_success <Cidade>.
 
# Solicita ao utilizador prompt para inserir a cidade de origem ou destino do voo
read -rp "Insira a cidade de origem ou destino do voo: " cidade

# Remove as aspas duplas da entrada do utilizador
cidade=$(echo "$cidade" | sed 's/"//g') #sed, Stream EDitor, para apagar padrão " " da variável cidade

# Primeiro, grep para encontrar a cidade e depois passa o resultado para awk para verificar lugares disponíveis
resultado=$(grep -i "$cidade" voos.txt | awk -F: '$8 > 0')

if [ -n "$resultado" ]; then
    # Se $resultado não está vazio, significa que encontramos um ou mais voos com lugares disponíveis.
    so_success S2.1.2 $cidade

else
    so_error S2.1.2
fi

## S2.1.3. O programa pede ao utilizador para inserir uma opção de voo, listando os voos que existem de acordo com a origem/destino inserida anteriormente, da seguinte forma: "Lista de voos, numerada, ou 0 para sair, Insira o voo que pretende reservar: _" O utilizador insere a opção do voo (neste exemplo, números de 1 a 3 ou 0). Se o utilizador escolheu um número de entre as opções de voos apresentadas (neste caso, entre 1 e 3), dá so_success <opção>. Caso contrário, dá so_error e termina.

opcoes=()  # Array para armazenar os números de voo disponíveis para a cidade especificada
contador=0 # Contador sequencial para apresentar as possibilidade de voo ao utilizador

while IFS= read -r linha; do #read para ler linha a linha do ficheiro voos.txt
    origem=$(echo "$linha" | cut -d ':' -f 2) #cut 2a coluna do ficheiro voos.txt - origem
    destino=$(echo "$linha" | cut -d ':' -f 3) #cut 3a coluna do ficheiro voos.txt - destino
    data=$(echo "$linha" | cut -d ':' -f 4) #cut 4a coluna do ficheiro voos.txt - data
    partida=$(echo "$linha" | cut -d ':' -f 5) #cut 5a coluna do ficheiro voos.txt - partida
    preco=$(echo "$linha" | cut -d ':' -f 6) #cut 6a coluna do ficheiro voos.txt - preco
    lugares=$(echo "$linha" | cut -d ':' -f 8) #cut 8a coluna do ficheiro voos.txt - lugares disponiveis
    
    if [ "$lugares" -ne 0 ]; then #caso a variável lugares lida seja != 0, há possibilidade do voo ser escolhido.
        contador=$(( ${#opcoes[@]} + 1 )) #adiciona-se um ao contador cfr opções disponíveis.
        opcoes+=("$linha") #adicionar linha às opções       
        echo "$contador.$origem para $destino, $data, Partida:$partida, Preço: $preco, Disponíveis:$lugares lugares" #display conforme enunciado para o utilizador ver as opções disponíveis
    fi
done < <(grep -E ":$cidade\b" voos.txt) #grep para procurar $cidade no ficheiro voos.txt; : para ser a começar com os ":"; \b para encontrar palavras literais (mesmo com espaço incluido, pois há voos com duas palavras)
#-E para incluir espaços caso hajam. Precisamos de igualdade exata.

if [ "$contador" -eq 0 ]; then #caso não se tenha identificado um voo para $cidade com lugares disponíveis, apresentar mensagem erro.
    so_error S2.1.3
    exit
else #como existem opções (contador != 0), falta fazer o display da opção sair (0.)
    echo "0.Sair" 
fi

# Input usuário para inserir número sequencial do voo indicado anteriormente  ou 0 para sair
read -p "Lista de voos, numerada, ou 0 para sair, Insira o voo que pretende reservar: " opcao #read para ler, -p para prompt, opcao para guardar na variavel opcao

# Se for um número inválido (fora do intervalo 0 a 9), também dar erro.
if ! [[ "$opcao" =~ ^[0-9]+$ ]]; then # variável opcao =~ ^[0-9]+$ --> só digitos entre 0 e 9 do início (^) ao fim ($)
    so_error S2.1.3
    exit 1
fi

# Caso a opcao também esteja fora do intervalo especificado (inclui o 0) -erro.
if (( opcao < 1 || opcao > contador )); then #caso opcao seja menor que um ou maior que o número sequencial de opcoes apresentado
    so_error S2.1.3 #erro opcao fora do contador indicado.
    exit 1
else
     linha_selecionada="${opcoes[opcao-1]}" #vamos guardar a linha selecionada para posteriormente utilizarmos. -1 porque é um array e começa a contar do 0,"${}" para guardarmos como string.
    so_success S2.1.3 $opcao #se tiver no intervalo de opcoes, sucesso e demonstra a opcao escolhida
fi

## S2.1.4. O programa pede ao utilizador o seu <ID_passageiro>: "Insira o ID do seu utilizador: _" O utilizador insere o respetivo ID de passageiro (dica: UserId Linux). Se esse ID não estiver registado no ficheiro passageiros.txt, dá so_error e termina. Caso contrário, reporta so_success <ID_passageiro>.

# Solicitar ao utilizador para inserir ID do passageiro
read -p "Insira o ID do seu utilizador: " id_passageiro #read prompt ao utilizador referente ao ID. Guarda na variavel id_passageiro

# Verifica se o ID do passageiro existe no ficheiro passageiros.txt
if ! grep -q "^$id_passageiro:" passageiros.txt; then #grep para pesquisar id no ficheiro passageiros.txt; ^ inicia com $id_passageiro e termina com :
#-q para grep operar em modo silencioso (para não dar outputs); ! para caso seja diferente do obtido, da erro.
    so_error S2.1.4
    exit 1
else 
    so_success S2.1.4 $id_passageiro #caso seja igual, será um sucesso.
fi

## S2.1.5. O programa pede ao utilizador a sua <Senha>: "Insira a senha do seu utilizador: _" O utilizador insere a respetiva senha. Caso o script veja que essa senha não é a registada para esse passageiro no ficheiro passageiros.txt, dá so_error e termina. Caso contrário, reporta so_success.

# Solicita ao utilizador que insira a senha do passageiro
read -p "Insira a senha do seu utilizador: " senha #read para ler prompt, prompt para input e resultado será guardado na variável senha

# Verifica se a senha fornecida corresponde à senha registada no ficheiro passageiros.txt
senha_registada=$(grep "^$id_passageiro:" passageiros.txt | cut -d ':' -f 5) #grep para processar texto; "^ para procurar $id_passageiro fornecido na alínea anterior no ficheiro passageiros.txt
# | pipe para seguir com a linha identificada para cut dessa linha delimitada por ':' -f 5 indica que queremos a quinta coluna, referente à senha dos utilizadores.
if [ "$senha" != "$senha_registada" ]; then #caso senha indicada seja diferente da registada
    so_error S2.1.5 #erro
    exit 1
else 
    so_success S2.1.5 #sucesso
fi

## S2.2. Processamento da resposta:
## S2.2.1. Valida se o passageiro possui <Saldo>, definido no ficheiro passageiros.txt, para comprar o bilhete selecionado no passo S2.1.3. Se a compra não é possível por falta de saldo, dá so_error <preço voo> <Saldo> e termina. Caso contrário, dá so_success <preço voo> <Saldo>.

preco_voo=$(echo "$linha_selecionada" | awk -F ':' '{print $6}') #retira a coluna 6 da linha selecionada em S2.1.3, sendo esta referente ao preço do voo selecionado
#echo imprime linha inteira, awk posteriormente processa o texto separado (-F) por ':', especificamente a coluna 6 e atribui à variável preco_voo

saldo_passageiro=$(grep "^$id_passageiro:" passageiros.txt | cut -d ':' -f 6) #retirar saldo do id_passageiro fornecido anteriormente.
#grep para procurar pelo id_passageiro fornecido anteriormente no ficheiro passageiros.txt
#cut da coluna 6 pelos dados separados por ':'

# Verifica se o saldo do passageiro é suficiente para comprar o bilhete
if (( $(echo "$saldo_passageiro < $preco_voo" | bc -l) )); then #caso saldo do passageiro seja menor que o preço do voo
    so_error S2.2.1 $preco_voo $saldo_passageiro #não teria dinheiro para o voo
    exit 1
else
    so_success S2.2.1 $preco_voo $saldo_passageiro #teria dinheiro para o voo, logo sucesso.
fi

## S2.2.2. Subtrai o valor do <preço voo> no <Saldo> do passageiro, e atualiza o ficheiro passageiros.txt. Em caso de erro (e.g., na escrita do ficheiro), dá so_error e termina. Senão, dá so_success <Saldo Atual>.

saldo_atual=$(echo "$saldo_passageiro - $preco_voo" | bc) #subtrair ao saldo do passageiro o preço do voo e guardar numa variável
linha_passageiro=$(grep "^$id_passageiro:" passageiros.txt) #com o ID do passageiro já identificado em passo anterior, vamos retirar a linha inteira com a sua informação do ficheiro passageiros.txt para posteriormente manipular.

# Substituir o novo saldo na linha do passageiro e guardar numa linha nova
linha_pass_atualizada=$(echo "$linha_passageiro" | awk -v novo_saldo="$saldo_atual" -F ':' '{OFS=":"; $6=novo_saldo; print}')
#$echo para imprimir o que foi guardado na variavel $linha_passageiro; | pipe; awk -v para manipular, variavel novo_saldo com o valor $saldo_atual calculado anteriormente, -F para delimitador ':',
#OFS para definir campo de saida como ":" também e, por fim, definir coluna 6, referente ao saldo do utilizador, com o valor novo_saldo referido anteriormente. Isto tudo guarda-se numa variável $linha_pass_atualizada, com todas as informacoes corretas.
sed -i "s|$linha_passageiro|$linha_pass_atualizada|" passageiros.txt
#sed para processar texto, -i para mudar no ficheiro diretamente, "s|....|....|" para substituir $linha_passageiro por $linha_pass_atualizada no ficheiro passageiros.txt.

if [ $? -eq 0 ]; then #por fim, verificar se houve sucesso na operação anterior (=0).
    so_success S2.2.2 $saldo_atual
else
    so_error S2.2.2
	exit 1
fi

## S2.2.3. Decrementa uma unidade aos lugares disponíveis do voo escolhidos no passo S2.1.3, e atualiza o ficheiro voos.txt. Em caso de erro (por exemplo, na escrita do ficheiro), dá so_error e termina. Senão, dá so_success.

lugares_disponiveis=$(echo "$linha_selecionada" | cut -d ':' -f 8) #echo para mostrar linha_selecionada como um todo, cut coluna 8 delimitada por ':' referente ao preço dos voos e guardar na variável lugares_disponiveis
lugares_apos_reserva=$((lugares_disponiveis - 1)) #subtrair 1 aos lugares disponíveis e guardar na variável lugares_apos_reserva

# Atualizar apenas o número de lugares disponíveis na linha selecionada no arquivo voos.txt
linha_atualizada=$(echo "$linha_selecionada" | awk -v novo_lugares="$lugares_apos_reserva" -F ':' '{OFS=":"; $8=novo_lugares; print}') 
#tal como no caso do saldo dos passageiros, guardar numa variável linha atualizada, echo para ver a linha como um todo, | pipe, awk -v para modificar com variável novo_lugares = $lugares_apos_reserva
#-F ':' e {OFS=":"... para definir como delimitadores iniciais e finais ":". $8 para alterar a oitava coluna referente aos lugares disponíveis do voo
sed -i "s|$linha_selecionada|$linha_atualizada|" voos.txt
#sed para processar texto, -i para mudar no ficheiro diretamente, "s|....|....|" para substituir $linha_selecionada por $linha_atualizada no ficheiro voos.txt.
if [ $? -eq 0 ]; then #caso operacao anterior tenha sido um sucesso (=0), sucesso.
    so_success S2.2.3
else
    so_error S2.2.3
    exit 1
fi

## S2.2.4. Regista a compra no ficheiro relatorio_reservas.txt, inserido uma nova linha no final deste ficheiro. Em caso de erro (por exemplo, na escrita do ficheiro), dá so_error e termina. Caso contrário, dá so_success.

if [ -s relatorio_reservas.txt ]; then
    # Extrai o último ID usado e incrementa.
    ultimo_id=$(tail -n 1 relatorio_reservas.txt | cut -d ':' -f 1)
    ultimo_id_decimal=$((10#$ultimo_id + 1))
else
    # Se o arquivo não existir ou estiver vazio, começa com 12091, conforme enunciado.
    ultimo_id_decimal=12091
fi

novo_id=$(printf "%05d" $ultimo_id_decimal)

#Data atual e hora atual de reserva.
data_reserva=$(date "+%Y-%m-%d") #date para aceder à data; %d dia-%m(onth)-%y(ear) conforme enunciado
hora_reserva=$(date "+%Hh%M") #date para aceder à hora atual; %H(our)h%M(inutes) conforme enunciado

# Definir variáveis para os dados da reserva
NrVoo=$(echo "$linha_atualizada" | cut -d ':' -f 1) #cut 1a coluna da linha atualizada do voo escolhido - refernete ao número do voo
Origem=$(echo "$linha_atualizada" | cut -d ':' -f 2) #cut 2a coluna da linha atualizada do voo escolhido - referente à origem do voo
Destino=$(echo "$linha_atualizada" | cut -d ':' -f 3) #cut 3a coluna da linha atualizada do voo escolhido - referente ao destino do voo
Preco=$(echo "$linha_atualizada" | cut -d ':' -f 6) #cut 6a coluna da linha atualizada do voo escolhido - refernete ao preço
ID_passageiro=$(echo $linha_pass_atualizada | cut -d ':' -f 1) #cut 1a coluna da linha passageiro atualizada - refernete ao user id
Data_reserva="$data_reserva" #data identificada anteriormente
Hora_reserva="$hora_reserva" #hora identificada anteriormente

# Juntar os dados numa única linha
nova_linha="$novo_id:$NrVoo:$Origem:$Destino:$Preco:$ID_passageiro:$Data_reserva:$Hora_reserva"
#conforme enunciado

# Adicionar a nova linha ao final do arquivo relatorio_reservas.txt
echo "$nova_linha" >> relatorio_reservas.txt # >> guarda numa nova linha do ficheiro relatorio_reservas.txt

# Atualizar o ID de reserva no ficheiro de controlo de IDs para manter informacao atualizada
echo "$novo_id" > controlo_id.txt

if [ $? -eq 0 ]; then #caso tenha sido sucesso nas operações anteriores, sucesso
    so_success S2.2.4
else
    so_error S2.2.4
    exit 1
fi