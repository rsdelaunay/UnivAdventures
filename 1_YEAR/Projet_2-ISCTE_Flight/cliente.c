/******************************************************************************
 ** ISCTE-IUL: Trabalho prático 3 de Sistemas Operativos 2023/2024, Enunciado Versão 3+
 **
 ** Aluno Nº: 122123       Nome: Rodrigo Miguel da Silva Delaunay
 ** Nome do Módulo: cliente.c
 ** Descrição/Explicação do Módulo:
 ** Módulo responsável pela gestão do processo Cliente. Por conseguinte, elaboram-se mensagens para comunicacao com o
 ** servidor (discriminando as informações do cliente, request do cliente, voo e lugar que pretende). Permite gerir os
 ** vários processos clientes, terminando assim que determinado
 ******************************************************************************/

// #define SO_HIDE_DEBUG                // Uncomment this line to hide all @DEBUG statements
#include "defines.h"

/*** Variáveis Globais ***/
int msgId;                              // Variável que tem o ID da Message Queue
MsgContent clientRequest;               // Variável que serve para as mensagens trocadas entre Cliente e Servidor
int nrTentativasEscolhaLugar = 0;       // Variável que indica quantas tentativas houve até conseguir encontrar um lugar

/**
 * @brief Processamento do processo Cliente
 *        "os alunos não deverão alterar a função main(), apenas compreender o que faz.
 *         Deverão, sim, completar as funções seguintes à main(), nos locais onde está claramente assinalado
 *         '// Substituir este comentário pelo código da função a ser implementado pelo aluno' "
 */
int main () {
    // C1
    msgId = initMsg_C1();
    so_exit_on_error(msgId, "initMsg_C1");
    // C2
    so_exit_on_error(triggerSignals_C2(), "triggerSignals_C2");
    // C3
    so_exit_on_error(getDadosPedidoUtilizador_C3(), "getDadosPedidoUtilizador_C3");
    // C4
    so_exit_on_error(sendRequest_C4(), "sendRequest_C4");
    // C5: CICLO6
    while (TRUE) {
        // C5
        configureTimer_C5(MAX_ESPERA);
        // C6
        so_exit_on_error(readResponseSD_C6(), "readResponseSD_C6");
        // C7
        int lugarEscolhido = trataResponseSD_C7();
        if (RETURN_ERROR == lugarEscolhido)
            terminateCliente_C9();
        // C8
        if (RETURN_ERROR == sendSeatChoice_C8(lugarEscolhido))
            terminateCliente_C9();
    }
}

/**
 *  "O módulo Cliente é responsável pela interação com o utilizador.
 *   Após o login do utilizador, este poderá realizar atividades durante o tempo da sessão.
 *   Assim, definem-se as seguintes tarefas a desenvolver:"
 */

/**
 * @brief C1: Ler a descrição da tarefa no enunciado
 * @return o valor de msgId em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int initMsg_C1 () {
    msgId = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");
    //Associar variavel msgId a msgqueue criada em servidor
     msgId = msgget(IPC_KEY, 0);
    if (msgId < 0){ //caso < 0, erro a abrir e a associar a msgId
        so_error("C1","");
        return -1;
    }
    else{ //caso contrario, sucesso
        so_success("C1","%d",msgId); //mensagem de sucesso conforme enunciado
    }

    so_debug("> [@return:%d]", msgId);
    return msgId;
}

/**
 * @brief C2: Ler a descrição da tarefa no enunciado
 * @return RETURN_SUCCESS (0) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int triggerSignals_C2 () {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");
    //Armar sinais
    //SIGHUP
    if (signal(SIGHUP, trataSinalSIGHUP_C10) != 0) { //Armar signal SIGHUP para executar função C10 caso triggered. Caso dê != 0, houve erro a armar e deverá dar error
        so_error("C2", "");
        return -1;// Termina com erro (-1)
    }//SIGINT
    if (signal(SIGINT, trataSinalSIGINT_C11) != 0) { //Armar signal SIGINT para executar função S9 caso triggered. Caso dê != 0, houve erro a armar e deverá dar error
        so_error("C2", "");
        return -1;// Termina com erro (-1)
    }//SIGALRM
    if (signal(SIGALRM, trataSinalSIGALRM_C12) != 0) { //Armar signal SIGALRM para executar função S9 caso triggered. Caso dê != 0, houve erro a armar e deverá dar error
        so_error("C2", "");
        return -1;// Termina com erro (-1)
    }
    so_success("C2", ""); //Caso não tenha dado erro nas operações anteriores, sucesso em armar os sinais.
    result = 0;
    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief C3: Ler a descrição da tarefa no enunciado
 * @return RETURN_SUCCESS (0) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int getDadosPedidoUtilizador_C3 () {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");
    printf("IscteFlight: Check-in Online\n"); // Printfs com mensagens previstas no enunciado
    printf("----------------------------\n");
    printf("Introduza o NIF do passageiro: \n");
    int nif = so_geti(); //macro so_utils.h para obter string por parte do utilizador e converter para número inteiro. 
    if (nif <= 0 || nif > 999999999){ //caso a variavel nif seja negativa ou maior que 999999999 (superior a 9 dígitos)
        so_error("C3", ""); //erro
        return -1; //terminar com erro
    }
    else{
        clientRequest.msgData.infoCheckIn.nif = nif; //após validar, definir
    }//senha
    so_gets(clientRequest.msgData.infoCheckIn.senha, 20); //macro so_utils, em que se guarda a string em request.senha e dá-se como buffer 20 pois o tamanho da senha deverá ser no máximo essa
    //Apos obtencao nif e senha, sucesso
    so_success("C3","");
    result = 0;
    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief C4: Ler a descrição da tarefa no enunciado
 * @return RETURN_SUCCESS (0) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int sendRequest_C4 () {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");
    clientRequest.msgType = MSGTYPE_LOGIN; //definir msgType como MSGTYPE_LOGIN conforme enunciado
    clientRequest.msgData.infoCheckIn.pidCliente = getpid(); //obtencao do pid atual para associar ao pidcliente
    clientRequest.msgData.infoCheckIn.pidServidorDedicado = PID_INVALID; //definicao conforme enunciado pois ainda não foi criado SD para o efeito
    if (msgsnd(msgId, &clientRequest, sizeof(clientRequest.msgData), 0) == -1) { //msgsnd para enviar, ao msgId identificado no inicio, clientrequest a mensagem a enviar, sizeof do que pretendemos enviar (msg toda) e 0 o número de flags.
        so_error("C4", ""); //caso operacao anterior == -1, deu erro
        return -1;
    }
    else{ //sucesso
        so_success("C4","<%d><%s><%d>",clientRequest.msgData.infoCheckIn.nif, clientRequest.msgData.infoCheckIn.senha, clientRequest.msgData.infoCheckIn.pidCliente); //conforme enunciado
        return 0;
    }

    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief C5: Ler a descrição da tarefa no enunciado
 * @param tempoEspera o tempo em segundos que queremos pedir para marcar o timer do SO (i.e., MAX_ESPERA)
 */
void configureTimer_C5 (int tempoEspera) {
    so_debug("< [@param tempoEspera:%d]", tempoEspera);

    if (alarm(tempoEspera) == 0){ //caso dê sucesso a configurar, success.
        so_success("C5", "Espera resposta em %d segundos.\n", tempoEspera); //conforme enunciado
    }
    else{
        so_error("C5","");
    }

    so_debug(">");
}

/**
 * @brief C6: Ler a descrição da tarefa no enunciado
 * @return RETURN_SUCCESS (0) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int readResponseSD_C6 () {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");

    if (msgrcv(msgId, &clientRequest, sizeof(clientRequest.msgData), clientRequest.msgData.infoCheckIn.pidCliente, 0) == -1) { //vai ler uma msg em msgId, guardar em clientRequest, tamanho da msgData (pois o tipo n interessa guardar), igual ao pidcliente, flags 0
        so_error("C6", ""); //caso erro na operação anterior, erro na leitura da mensagem
        result = -1;
    }
    else{ //sucesso na leitura
        so_success("C6", "%d %d %d",clientRequest.msgData.infoCheckIn.nif, clientRequest.msgData.infoCheckIn.lugarEscolhido, clientRequest.msgData.infoCheckIn.pidCliente); //mensagem sucesso conforme enunciado
        result = 0;
    }

    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief C7: Ler a descrição da tarefa no enunciado
 * @return Nº do lugar escolhido (0..MAX_SEATS-1) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int trataResponseSD_C7 () {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");
    //C7.1
    alarm(0); // desliga o alarme configurado em C5
    //C7.2
    if (clientRequest.msgData.infoCheckIn.pidServidorDedicado == PID_INVALID) { // Caso pidSD seja PID_INVALID, houve erro na operação de login
        so_error("C7.2", "");
        exit(-1);
    } else {
        //C7.3
        if (clientRequest.msgData.infoCheckIn.lugarEscolhido != EMPTY_SEAT) { // indica que o utilizador já conseguiu escolher um lugar
            //sucesso
            so_success("C7.3", "Reserva concluída: %s %s %d", clientRequest.msgData.infoVoo.origem, clientRequest.msgData.infoVoo.destino, clientRequest.msgData.infoCheckIn.lugarEscolhido); // sucesso conforme enunciado
            exit(0);
        } else { // precisamos de escolher um lugar, pois ainda está empty
            if (nrTentativasEscolhaLugar == 0) {
                // Se é a primeira tentativa, dá sucesso
                so_success("C7.4.1", "");
                nrTentativasEscolhaLugar++; // Indica que não será mais a primeira tentativa
            } else {
                // Se não é a primeira tentativa, dá erro
                so_error("C7.4.1", "Erro na reserva de lugar.");
            }
            printf("Lugares disponíveis: ");
            int cont_print = 0;
            for (int i = 0; i < MAX_SEATS; i++) {
                if (clientRequest.msgData.infoVoo.lugares[i] == EMPTY_SEAT) {
                    if (cont_print == 0) { 
                        printf("%d", i); // printf desses lugares (primeiro)
                    } else {
                        printf(", %d", i); // printf dos lugares subsequentes
                    }
                    cont_print++;
                }
            }
            printf("\n");
            printf("Introduza o lugar que deseja reservar: ");
            int lugarEscolhido = so_geti(); // guardar na variável lugarEscolhido
            if (lugarEscolhido < 0 || lugarEscolhido >= MAX_SEATS || clientRequest.msgData.infoVoo.lugares[lugarEscolhido] != EMPTY_SEAT) { // número inserido pelo cliente errado
                // Erro
                so_error("C7.4.3", "");
                result = -1;
            } else { // sucesso no número dado
                so_success("C7.4.3", "%d", lugarEscolhido);
                result = lugarEscolhido;
            }
        }
    }
    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief C8: Ler a descrição da tarefa no enunciado
 * @param lugarEscolhido índice do array lugares que o utilizador escolheu, entre 0 e MAX_SEATS-1
 * @return RETURN_SUCCESS (0) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int sendSeatChoice_C8 (int lugarEscolhido) {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("< [@param lugarEscolhido:%d]", lugarEscolhido);

    // Cria uma mensagem clientRequest com os valores preenchidos
    clientRequest.msgType = clientRequest.msgData.infoCheckIn.pidServidorDedicado; //getpid para obter pid Client
    clientRequest.msgData.infoCheckIn.lugarEscolhido = lugarEscolhido;
    clientRequest.msgData.infoCheckIn.pidCliente = getpid();

    if (msgsnd(msgId, &clientRequest, sizeof(clientRequest.msgData), 0) == -1) { //msgsnd para enviar, ao msgId identificado no inicio, clientrequest a mensagem a enviar, sizeof do que pretendemos enviar (msg toda) e 0 o número de flags.
        so_error("C8", ""); //caso operacao anterior == -1, deu erro
        result = -1;
    }
    else{//sucesso ao enviar mensagem com informacoes
        so_success("C8", "%d %d %d", clientRequest.msgData.infoCheckIn.nif,clientRequest.msgData.infoCheckIn.lugarEscolhido, clientRequest.msgData.infoCheckIn.pidCliente); //caso operacao anterior == -1, deu erro
        result = 0;
    }
    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief C9: Ler a descrição da tarefa no enunciado
 */
void terminateCliente_C9 () {
    so_debug("<");
    if(clientRequest.msgData.infoCheckIn.pidServidorDedicado == PID_INVALID){ //caso não tenha SD associado, error
        so_error("C9","");
    }
    else{
        so_success("C9",""); //sucesso e enviar msg SIGUSR1 para o SD
        if(kill(clientRequest.msgData.infoCheckIn.pidServidorDedicado,SIGUSR1) != -1){ //enviar sinal SIGUSR1 para SD
        //caso operacao anterior != -1, enviou com sucesso
        so_success("C9","");
        }
        else{
        so_error("C9","");
        }
    }
    so_debug(">");
    exit(0);
}

/**
 * @brief C10: Ler a descrição da tarefa no enunciado
 * @param sinalRecebido nº do Sinal Recebido (preenchido pelo SO)
 */
void trataSinalSIGHUP_C10 (int sinalRecebido) {
    so_debug("< [@param sinalRecebido:%d]", sinalRecebido);

    so_success("C10","Check-in concluído sem sucesso");
    terminateCliente_C9(); //cfr enunciado

    so_debug(">");
}

/**
 * @brief C11: Ler a descrição da tarefa no enunciado
 * @param sinalRecebido nº do Sinal Recebido (preenchido pelo SO)
 */
void trataSinalSIGINT_C11 (int sinalRecebido) {
    so_debug("< [@param sinalRecebido:%d]", sinalRecebido);

    so_success("C11","Cliente: Shutdown");
    terminateCliente_C9(); //cfr enunciado

    so_debug(">");
}

/**
 * @brief C12: Ler a descrição da tarefa no enunciado
 * @param sinalRecebido nº do Sinal Recebido (preenchido pelo SO)
 */
void trataSinalSIGALRM_C12 (int sinalRecebido) {
    so_debug("< [@param sinalRecebido:%d]", sinalRecebido);

    so_error("C12","Cliente: Timeout");
    terminateCliente_C9(); 

    so_debug(">");
}