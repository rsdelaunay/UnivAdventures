/******************************************************************************
 ** ISCTE-IUL: Trabalho prático 3 de Sistemas Operativos 2023/2024, Enunciado Versão 1+
 **
 ** Aluno: Nº: 122123      Nome: Rodrigo Miguel da Silva Delaunay
 ** Nome do Módulo: servidor.c
 ** Descrição/Explicação do Módulo:
 ** Módulo responsável pela gestão do servidor, servidores dedicados, semáforos e mensagens a enviar entre servidores
 ** dedicados e processos cliente. Acaba por ser um módulo de gestão macro das reservas de voo, havendo gestão da shared
 ** memory, semáforos, términos/criação de servidores dedicados, sinais, entre outros.
 ******************************************************************************/

// #define SO_HIDE_DEBUG                // Uncomment this line to hide all @DEBUG statements
#include "defines.h"

/*** Variáveis Globais ***/
int shmId;                              // Variável que tem o ID da Shared Memory
int msgId;                              // Variável que tem o ID da Message Queue
int semId;                              // Variável que tem o ID do Grupo de Semáforos
MsgContent clientRequest;               // Variável que serve para as mensagens trocadas entre Cliente e Servidor
DadosServidor *database = NULL;         // Variável que vai ficar com UM POINTER PARA a memória partilhada
int indexClient = -1;                   // Índice do passageiro que fez o pedido ao servidor/servidor dedicado na BD
int indexFlight = -1;                   // Índice do voo do passageiro que fez o pedido ao servidor/servidor dedicado na BD
int nrServidoresDedicados = 0;          // Número de servidores dedicados (só faz sentido no processo Servidor)

/**
 * @brief Processamento do processo Servidor e dos processos Servidor Dedicado
 *        "os alunos não deverão alterar a função main(), apenas compreender o que faz.
 *         Deverão, sim, completar as funções seguintes à main(), nos locais onde está claramente assinalado
 *         '// Substituir este comentário pelo código da função a ser implementado pelo aluno' "
 */
int main () {
    // S1
    shmId = initShm_S1();
    if (RETURN_ERROR == shmId) terminateServidor_S7();
    // S2
    msgId = initMsg_S2();
    if (RETURN_ERROR == msgId) terminateServidor_S7();
    // S3
    semId = initSem_S3();
    if (RETURN_ERROR == semId) terminateServidor_S7();
    // S4
    if (RETURN_ERROR == triggerSignals_S4()) terminateServidor_S7();

    // S5: CICLO1
    while (TRUE) {
        // S5
        int result = readRequest_S5();
        if (CICLO1_CONTINUE == result) // S5: "Se receber um sinal (...) retorna o valor CICLO1_CONTINUE"
            continue;                  // S5: "para que main() recomece automaticamente o CICLO1 no passo S5"
        if (RETURN_ERROR == result) terminateServidor_S7();
        // S6
        int pidServidorDedicado = createServidorDedicado_S6();
        if (pidServidorDedicado > 0)   // S6: "o processo Servidor (pai) (...) retorna um valor > 0"
            continue;                  // S6: "(...) recomeça o Ciclo1 no passo S4 (ou seja, volta a aguardar novo pedido)"
        if (RETURN_ERROR == pidServidorDedicado) terminateServidor_S7();
        // S6: "o Servidor Dedicado (...) retorna 0 para que main() siga automaticamente para o passo SD10

        // SD10
        if (RETURN_ERROR == triggerSignals_SD10()) terminateServidorDedicado_SD18();
        // SD11
        indexClient = searchClientDB_SD11();
        int erroValidacoes = TRUE;
        if (RETURN_ERROR != indexClient) {
            // SD12: "Se o passo SD11 concluiu com sucesso: (...)"
            indexFlight = searchFlightDB_SD12();
            if (RETURN_ERROR != indexFlight) {
                // SD13: "Se os passos SD11 e SD12 tiveram sucesso, (...)"
                if (!updateClientDB_SD13())
                    erroValidacoes = FALSE; // erroValidacoes = "houve qualquer erro nas validações dos passos SD11, SD12, ou SD13"
            }
        }
        // SD14: CICLO5
        int escolheuLugarDisponivel = FALSE;
        while (!escolheuLugarDisponivel) {
            // SD14.1: erroValidacoes = "houve qualquer erro nas validações dos passos SD11, SD12, ou SD13"
            if (RETURN_ERROR == sendResponseClient_SD14(erroValidacoes)) terminateServidorDedicado_SD18();
            if (erroValidacoes)
                terminateServidorDedicado_SD18();

            // SD15: "Se os pontos anteriores tiveram sucesso, (...)"
            if (RETURN_ERROR == readResponseClient_SD15()) terminateServidorDedicado_SD18();
            // SD16
            if (RETURN_ERROR == updateFlightDB_SD16())  // SD16: "Se lugarEscolhido no pedido NÃO estiver disponível (...) retorna erro (-1)"
                continue;                               // SD16: "para que main() recomece o CICLO5 em SD14"
            else
                escolheuLugarDisponivel = TRUE;
        }
        sendConfirmationClient_SD17();
        terminateServidorDedicado_SD18();
    }
}

/**
 *  "O módulo Servidor é responsável pelo processamento do check-in dos passageiros.
 *   Está dividido em duas partes, um Servidor (pai) e zero ou mais Servidores Dedicados (filhos).
 *   Este módulo realiza as seguintes tarefas:"
 */

/**
 * @brief S1: Ler a descrição da tarefa no enunciado
 * @return o valor de shmId em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int initShm_S1 () {
    shmId = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");
// S1.1 - Valida se os ficheiros bd_passageiros.dat e bd_voos.dat existem e se temos permissão de leitura e escrita
    if (access(FILE_DATABASE_PASSAGEIROS, F_OK) == 0 && access(FILE_DATABASE_VOOS, F_OK) == 0 &&
        access(FILE_DATABASE_PASSAGEIROS, R_OK | W_OK) == 0 && access(FILE_DATABASE_VOOS, R_OK | W_OK) == 0) {
        so_success("S1.1", "");
    } else {
        so_error("S1.1", "");
        return -1;
    }
    // S1.2 - Abre a SHM com KEY IPC_KEY
    shmId = shmget(IPC_KEY, 0, 0);

    if (shmId >= 0) {
        // Anexa a SHM existente
        database = (DadosServidor *) shmat(shmId, NULL, 0);
        if (database == (void *) -1) {
            so_error("S1.2.1", "");
            return -1; //erro
        } else {
            so_success("S1.2", "");
            so_success("S1.2.1", "%d", shmId); // sucesso conforme enunciado
            return shmId;
        }
    } else {
        so_error("S1.2","");
        // S1.3 - Valida se o erro foi por não existir ainda nenhuma SHM criada com essa KEY
        if (errno == ENOENT) {
            so_success("S1.3", "");
        } else {
            so_error("S1.3", "");
            return shmId;
        }
    }

    // S1.4 - Cria uma SHM em modo exclusivo com a KEY IPC_KEY e tamanho adequado
    size_t shm_size = sizeof(DadosServidor);
    shmId = shmget(IPC_KEY, shm_size, IPC_CREAT | IPC_EXCL | 0666);
    if (shmId < 0) {
        so_error("S1.4", "");
        return -1; //erro
    }
    database = (DadosServidor *) shmat(shmId, NULL, 0);
    if (database == (void *) -1) {
        so_error("S1.4", "");
        return -1; //erro
    }
    so_success("S1.4", "%d", shmId);

    // S1.5 - Inicia a Lista de Passageiros e a Lista de Voos
    for (int i = 0; i < MAX_PASSENGERS; ++i) {
        database->listClients[i].nif = PASSENGER_NOT_FOUND; //copiar para database com NOT_FOUND
    }
    for (int i = 0; i < MAX_FLIGHTS; ++i) {
        strcpy(database->listFlights[i].nrVoo, FLIGHT_NOT_FOUND); //copiar para database com NOT_FOUND
    }
    so_success("S1.5", ""); //sucesso

    // S1.6 - Lê o ficheiro bd_passageiros.dat e preenche a lista de passageiros na SHM
    FILE *file_pass = fopen(FILE_DATABASE_PASSAGEIROS, "r+");
    if (file_pass == NULL) {
        so_error("S1.6", "");
        return -1;
    }
    CheckIn temp_pass; //variavel do tipo CheckIn temporária
    for (int i = 0; i < MAX_PASSENGERS; i++) {
        if (fread(&temp_pass, sizeof(temp_pass), 1, file_pass) != 1) { // caso não leia 1 elemento
            break;
        }
        database->listClients[i] = temp_pass; //guardar o que foi lido para a data base
        database->listClients[i].pidCliente = PID_INVALID; //cfr enunciado
        database->listClients[i].pidServidorDedicado = PID_INVALID; //cfr enunciado
    }
    if (feof(file_pass) == 0){ //não chegou ao fim do ficheiro
        so_error("S1.6",""); 
        fclose(file_pass);
        return -1;
    }
    fclose(file_pass);
    so_success("S1.6", "");

    // S1.7 - Lê o ficheiro bd_voos.dat e preenche a lista de voos na memória partilhada
    FILE *file_voos = fopen(FILE_DATABASE_VOOS, "r+");
    if (file_voos == NULL) {
        so_error("S1.7", "");
        return -1; //erro
    }
    Voo temp_voo;
    for (int i = 0; i < MAX_FLIGHTS; i++) {
        if (fread(&temp_voo, sizeof(temp_voo), 1, file_voos) != 1){ 
            break; 
        }
        database->listFlights[i] = temp_voo;
    }
    if (feof(file_voos) == 0){ //não chegou ao fim do ficheiro
        so_error("S1.7",""); 
        fclose(file_voos);
        return -1;
    }
    fclose(file_voos);
    so_success("S1.7", "");

    so_debug("> [@return:%d]", shmId);
    return shmId;
}

/**
 * @brief S2: Ler a descrição da tarefa no enunciado
 * @return o valor de msgId em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int initMsg_S2 () {
    msgId = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");
    //S2.1 
    // Verifica se já existem msgs associadas à IPC_KEY. Caso exista, remover. Caso dê erro ao remover, error.
    msgId = msgget(IPC_KEY, 0666); //msgget para verificar se existem mensagens associadas à IPC_KEY c/ leitura e escrita. Caso não existam, retorna -1
    if (msgId != -1) { //Caso msgId seja diferente de -1, existe.
        // Fila de mensagens existe, então tenta removê-la
        if (msgctl(msgId, IPC_RMID, NULL) == -1) { //msgctl para gerir msgs, msgId com o ID anterior, IPC_RMID para remover a mensagem, NULL no final pois estamos a utilizar IPC_RMID
            //Caso retorne -1 na operação anterior, houve erro a remover mensagem existente.
            so_error("S2.1","");
            exit(-1);
        } else {
            so_success("S2.1","");
        }
    }
    //S2.2.
    // Cria uma nova fila de mensagens
    msgId = msgget(IPC_KEY, 0666 | IPC_CREAT); //msgget novamente, associado à IPC_KEY e com permissões de leitura e escrita, IPC_CREAT para criar msg queue
    if (msgId == -1) { //caso não tenha sido criado um msgId anteriormente, houve erro na operação.
        so_error("S2","");
        exit(-1);
    }
    else{// Sucesso ao criar a fila de mensagens
        so_success("S2.2","%d",msgId);
    }
    so_debug("> [@return:%d]", msgId);
    return msgId;
}

/**
 * @brief S3: Ler a descrição da tarefa no enunciado
 * @return o valor de semId em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int initSem_S3 () {
    // S3.1. Remover semáforos existentes caso se identifiquem
    semId = semget(IPC_KEY, 3, 0666); // Verificar existência de semáforos
    if (semId != -1) { // Caso semId seja != -1, um semáforo existente foi identificado
        if (semctl(semId, 0, IPC_RMID) == -1) { // Remover semáforos existentes
            so_error("S3.1", "");
            exit(-1);
        } else {
            so_success("S3.1", "");
        }
    }

    // S3.2. Criar 3 semáforos
    semId = semget(IPC_KEY, 3, 0666 | IPC_CREAT); // Criar grupo de 3 semáforos
    if (semId == -1) { // Caso Id de semáforos seja igual a -1, houve erro na criação
        so_error("S3.2", "");
        exit(-1);
    } else {
        so_success("S3.2", "%d", semId);
    }

    // S3.3. Setvalue de semáforos: passageiros e voos 1, nr srv dedicados 0
    if (semctl(semId, SEM_PASSAGEIROS, SETVAL, 1) == -1) { // Setvalue para SEM_PASSAGEIROS
        so_error("S3.3", "");
        exit(-1);
    }
    if (semctl(semId, SEM_VOOS, SETVAL, 1) == -1) { // Setvalue para SEM_VOOS
        so_error("S3.3", "");
        exit(-1);
    }
    if (semctl(semId, SEM_NR_SRV_DEDICADOS, SETVAL, 0) == -1) { // Setvalue para SEM_NR_SRV_DEDICADOS
        so_error("S3.3", "");
        exit(-1);
    }
    so_success("S3.3","");
    so_debug("> [@return:%d]", semId);
    return semId;
    }

/**
 * @brief S4: Ler a descrição da tarefa no enunciado
 * @return RETURN_SUCCESS (0) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int triggerSignals_S4 () {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");
    if (signal(SIGINT, trataSinalSIGINT_S8) != 0) { //Armar signal SIGINT para executar função S8 caso triggered. Caso dê != 0, houve erro a armar e deverá dar error
        so_error("S4", "");
        return result; //erro
    }
    if (signal(SIGCHLD, trataSinalSIGCHLD_S9) != 0) { //Armar signal SIGINT para executar função S9 caso triggered. Caso dê != 0, houve erro a armar e deverá dar error
        so_error("S4", "");
        return result; //erro
    }
    so_success("S4", ""); //Caso não tenha dado erro nas operações anteriores, sucesso em armar os sinais.
    result = 0;
    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief S5: O CICLO1 já está a ser feito na função main(). Ler a descrição da tarefa no enunciado
 * @return RETURN_SUCCESS (0) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int readRequest_S5 () {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");
    int msg_rcv;

    // Lê a mensagem do tipo MSGTYPE_LOGIN
    msg_rcv = msgrcv(msgId, &clientRequest, sizeof(MsgContent) - sizeof(long), MSGTYPE_LOGIN, 0); //msgrcv para ler do msgId criado em S2, guardar em clientRequest (mesma struct MsgContent),
    //ler size of MsgContent - o long pois não precisamos do msgType. MSGTYPE_LOGIN  para representar o tipo de msg que queremos ler (=1) e 0 para indicar
    //que irá ficar à espera de uma mensagem deste tipo

    if (msg_rcv == -1) { // caso erro na operacao anterior
        //recebeu interrupcao ou nao
        if (errno == EINTR){ //recebeu interrupcao
            return CICLO1_CONTINUE; //cfr enunciado
        }
        so_error("S5", "");
        return result;// Termina com erro
    }
    //Nao havendo erros nem sinal recebido, sucesso
    so_success("S5","%d %s %d",clientRequest.msgData.infoCheckIn.nif,clientRequest.msgData.infoCheckIn.senha,clientRequest.msgData.infoCheckIn.pidCliente);
    //imprimir nif, senha e pid cliente conforme enunciado. Para tal, temos que seguir as structs tanto da msg como posteriormente do checkin.
    result = 0; //sucesso
    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief S6: Ler a descrição da tarefa no enunciado
 * @return PID do processo filho, se for o processo Servidor (pai),
 *         0 se for o processo Servidor Dedicado (filho),
 *         ou PID_INVALID (-1) em caso de erro
 */
int createServidorDedicado_S6 () {
    int pid_filho = PID_INVALID;    // Por omissão retorna erro
    so_debug("<");
    // Cria um processo filho (Servidor Dedicado)
    pid_filho = fork();

    if (pid_filho < 0) { //Caso não tenha retornado um pid_filho adequado, indica que fork não foi realizado com sucesso
        // Se houver erro na criação do processo filho, erro e exit -1
        so_error("S6","");
    }
    else if (pid_filho == 0) { //Caso pid == 0, é o SD
        so_success("S6", "Servidor Dedicado: Nasci");// conforme enunciado
    } else { //caso > 0, será o pai a executar:
        so_success("S6", "Servidor: Iniciei SD %d", pid_filho);
        nrServidoresDedicados++; //incrementa variavel numero servidores dedicados
         // Retorna um valor > 0 (em return pid_filho) para que main() recomece automaticamente o CICLO1 em S5 (aguarda novo pedido)
    }

    so_debug("> [@return:%d]", pid_filho);
    return pid_filho;
}

/**
 * @brief S7: Ler a descrição da tarefa no enunciado
 */
void terminateServidor_S7 () {
    so_debug("<");
    so_success("S7", "Servidor: Start Shutdown"); //conforme enunciado
    int jump_s7_5 = 0; //para controlar jump posterior

    // S7.1 Verifica se existe SHM aberta e apontada pela variável database
    if (database == NULL || shmId < 0) {
        so_error("S7.1", "");
        jump_s7_5 = 1; //saltar para s7.5
    } else {
        so_success("S7.1", "");
    }
    // S7.2 e S7.3. CICLO 2
    if (jump_s7_5 == 0) {
        struct sembuf smf; //criacao sembuf para utilizarmos operacoes num semáforo e sincronizar concorrência SDs
        //mexer na struct um a um
        smf.sem_num = SEM_NR_SRV_DEDICADOS; //vamos aceder ao semáforo SEM_NR_SRV_DEDICADOS criado anteriormente associado a IPC_KEY
        smf.sem_op = -nrServidoresDedicados; //operacao a realizar
        smf.sem_flg = 0; //flags a 0
        for (int i = 0; i < MAX_PASSENGERS; i++) {
            CheckIn passageiro = database->listClients[i];
            if (passageiro.pidServidorDedicado > 0) {
                if (kill(passageiro.pidServidorDedicado, SIGUSR2) == 0) { //envia sigusr2 para pid sd
                    so_success("S7.2", "Servidor: Shutdown SD %d", passageiro.pidServidorDedicado);
                } else {
                    so_error("S7.2", "");
                }
            }
        }
        semop(semId, &smf, 1); //semop para operacao semaforo, semId para aceder ao grupo de semaforos criado anteriormente, ponteiro para aceder à struct sembuff smf criada anteriormente, 1 numero de operacoes a ser realizada
        so_success("S7.3","");
    // S7.4 Reescreve os ficheiros bd_passageiros.dat e bd_voos.dat
        FILE *file_passageiros = fopen(FILE_DATABASE_PASSAGEIROS, "wb");
        FILE *file_voos = fopen(FILE_DATABASE_VOOS, "wb");
        if (file_passageiros == NULL || file_voos == NULL) {
            so_error("S7.4", "");
        }
        if (fwrite(database->listClients, sizeof(CheckIn), MAX_PASSENGERS, file_passageiros) != MAX_PASSENGERS) { //
            so_error("S7.4", "");
        }
        if (fwrite(database->listFlights, sizeof(Voo), MAX_FLIGHTS, file_voos) != MAX_FLIGHTS) {
            so_error("S7.4", "");
        }
        so_success("S7.4", "");
        fclose(file_passageiros);
        fclose(file_voos);
        jump_s7_5 = 1;
    }
    // S7.5 Remove o segmento de memória compartilhada, semáforos e fila de mensagens
    if (jump_s7_5 == 1) {
        if (shmId != -1) {
            if (shmctl(shmId, IPC_RMID, NULL) == -1) { //remover shm
                so_error("S7.5", "");
            }
        }
        if (semId != -1) {
            if (semctl(semId, 0, IPC_RMID) == -1) { //remover semaforos
                so_error("S7.5", "");
            }
        }
        if (msgId != -1) {
            if (msgctl(msgId, IPC_RMID, NULL) == -1) { //remover msgs
                so_error("S7.5", "");
            }
        }
        so_success("S7.5", "Servidor: End Shutdown");
    }
    so_debug(">");
    exit(0);
}

/**
 * @brief S8: Ler a descrição da tarefa no enunciado
 * @param sinalRecebido nº do Sinal Recebido (preenchido pelo SO)
 */
void trataSinalSIGINT_S8 (int sinalRecebido) {
    so_debug("< [@param sinalRecebido:%d]", sinalRecebido);
    //Apos recebido SIGINT
    so_success("S8", "");
    //Segue para S7 e termina o servidor
    terminateServidor_S7();

    so_debug(">");
}

/**
 * @brief S9: Ler a descrição da tarefa no enunciado
 * @param sinalRecebido nº do Sinal Recebido (preenchido pelo SO)
 */
void trataSinalSIGCHLD_S9 (int sinalRecebido) {
    so_debug("< [@param sinalRecebido:%d]", sinalRecebido);
 
    int pid_filho = wait(NULL); //esperar pelo término de um servidor filho e guardar o pid do mesmo em variavel pid_filho
    struct sembuf smf; //criacao sembuf para utilizarmos operacoes num semáforo e sincronizar concorrência SDs
    smf.sem_num = SEM_NR_SRV_DEDICADOS; //vamos aceder ao semáforo SEM_NR_SRV_DEDICADOS criado anteriormente associado a IPC_KEY
    smf.sem_op = 1; //operacao a realizar, aumentar 1
    smf.sem_flg = 0; //flags a 0
    semop(semId, &smf, 1); //incrementar 1 apos terminar todos os SD
    
    so_success("S9", "Servidor: Confirmo fim de SD %d", pid_filho); //quando receber, faz success e msg enunciado

    so_debug(">");
}

/**
 * @brief SD10: Ler a descrição da tarefa no enunciado
 * @return RETURN_SUCCESS (0) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int triggerSignals_SD10 () {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");
    //SIGUSR1
    if (signal(SIGUSR1, trataSinalSIGUSR1_SD19) != 0) { //Armar signal SIGUSR1 para executar função SD19 caso triggered. Caso dê != 0, houve erro a armar e deverá dar error
        so_error("SD10", "");
        return -1;//retorna erro
    }
    //SIGUSR2
    if (signal(SIGUSR2, trataSinalSIGUSR2_SD20) != 0) { //Armar signal SIGUSR2 para executar função SD19 caso triggered. Caso dê != 0, houve erro a armar e deverá dar error
        so_error("SD10", "");
        return -1; //retorna erro
    }
    //SIGINT
    if (signal(SIGINT, SIG_IGN) != 0) { //programar para ignorar sinal SIGINT
        so_error("SD10", "");
        return -1; //retorna erro
    }
    so_success("SD10", ""); //Caso não tenha dado erro nas operações anteriores, sucesso em armar os sinais.
    result = 0;
    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief SD11: Ler a descrição da tarefa no enunciado
 * @return indexClient em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int searchClientDB_SD11 () {
    indexClient = -1;    // SD11: Inicia a variável indexClient a -1 (índice da lista de passageiros de database)
    so_debug("<");
    //SD11.1 e SD11.2 Procurar passageiro na BD
        // Percorre a lista de passageiros
    for (int i = 0; i < MAX_PASSENGERS; i++) { //do 0 ate max passageiros
        if (database->listClients[i].nif == clientRequest.msgData.infoCheckIn.nif) {  //caso seja igual ao nif da request, foi encontrado.
            indexClient = i;
            //SD11.3 Comparar senha
            if (strcmp(database->listClients[indexClient].senha,clientRequest.msgData.infoCheckIn.senha)==0){ //comparar strings para verificar igualdade
                so_success("SD11.3", "%d",indexClient); //conforme enunciado
                //creio que aqui falta o return indexclient para sair
            }
            else{ //senhas nao correspondem
                so_error("SD11.3", "Cliente %d: Senha errada", clientRequest.msgData.infoCheckIn.nif); //conforme enunciado
                indexClient = -1; //para retornar erro
            }
        }
    }
    // Se chegou ao final da lista sem encontrar o passageiro
    so_error("SD11.1", "Cliente %d: não encontrado", clientRequest.msgData.infoCheckIn.nif);
    so_debug("> [@return:%d]", indexClient);
    return indexClient;
}

/**
 * @brief SD12: Ler a descrição da tarefa no enunciado
 * @return indexFlight em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int searchFlightDB_SD12 () {
    indexFlight = -1;    // SD12: Inicia a variável indexFlight a -1 (índice da lista de voos de database)
    so_debug("<");
    //SD12.1 e SD12.2 Procurar voos na lista de base de dados
    // Percorre a lista de voos
    for (int i = 0; i < MAX_FLIGHTS; i++) { //do 0 ate max voos
        if (strcmp(database->listFlights[i].nrVoo, database->listClients[indexClient].nrVoo) == 0) {  //caso seja igual ao nrvoo da request, foi encontrado.
           indexFlight = i;
           so_success("SD12.2", "%d",indexFlight); //conforme enunciado
           return indexFlight;
        }
    }
    so_error("SD12.1","Voo %s: não encontrado", database->listClients[indexClient].nrVoo);
    so_debug("> [@return:%d]", indexClient);
    return -1;
}

/**
 * @brief SD13: Ler a descrição da tarefa no enunciado
 * @return RETURN_SUCCESS (0) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int updateClientDB_SD13 () {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");
    //SD13.1 - Inicio check-in
    so_success("SD13.1","Start Check-in: %d %d", clientRequest.msgData.infoCheckIn.nif, clientRequest.msgData.infoCheckIn.pidCliente); //conforme enunciado
    
    struct sembuf smf; //criacao sembuf para utilizarmos operacoes num semáforo e sincronizar concorrência SDs
    smf.sem_num = SEM_PASSAGEIROS; //vamos aceder ao semáforo SEM_PASSAGEIROS criado anteriormente associado a IPC_KEY
    smf.sem_op = -1; //operacao a realizar, decrementar 1
    smf.sem_flg = 0; //flags a 0
    semop(semId, &smf, 1); //semop para operacao semaforo, semId para aceder ao grupo de semaforos criado anteriormente, ponteiro para aceder à struct sembuff smf criada anteriormente, 1 numero de operacoes a ser realizada

    //SD13.2. - Valida pidCliente e EmptySeat em indexClient na database
    if (!(database->listClients[indexClient].pidCliente == PID_INVALID && database->listClients[indexClient].lugarEscolhido == EMPTY_SEAT)){ //verifica na databse, list clients no indice identificado para o cliente, se pidCliente está inválido e se lugar esta vazio
        //se nao forem iguais ao verificado, erro conforme enunciado
        so_error("SD13.2","Cliente %d: Já fez check-in",clientRequest.msgData.infoCheckIn.nif);
        //Incrementar semáforo para outro servidor poder atuar
        smf.sem_op = 1; //set operacao a 1
        semop(semId, &smf, 1); //incrementar 1
        return result;
    }
    //SD13.3
    sleep(4); //Aguarda 4 segundos. Outro servidor não há-de mexer porque temos o semáforo passageiros a 0
    //SD13.4. Preencher pidCliente e pidServidorDedicado com informações do pidFilho e do clientRequest
    database->listClients[indexClient].pidCliente = clientRequest.msgData.infoCheckIn.pidCliente;
    database->listClients[indexClient].pidServidorDedicado = getpid();

    //Incrementar semáforo para outro servidor poder atuar
    smf.sem_op = 1; //set operacao a 1
    semop(semId, &smf, 1); //incrementar 1

    //SD13.5 Terminar com mensagem sucesso
    so_success("SD13.5","End Check-in: %d %d",clientRequest.msgData.infoCheckIn.nif, clientRequest.msgData.infoCheckIn.pidCliente);

    result = 0;
    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief SD14: Ler a descrição da tarefa no enunciado
 * @param erroValidacoes booleano que diz se houve algum erro nas validações de SD11, SD12 e SD13
 * @return RETURN_SUCCESS (0) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int sendResponseClient_SD14 (int erroValidacoes) {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("< [@param erroValidacoes:%d]", erroValidacoes);
    //Criação mensagem do tipo MsgContent
    MsgContent response;
    // Define o tipo da mensagem como o PID do cliente
    response.msgType = clientRequest.msgData.infoCheckIn.pidCliente; //tipo de mensagem = ao número do pidCliente conforme enunciado
    // Verifica condições de erro
    if (erroValidacoes > 0) { //verifica o erro de SD11, SD12 e SD13
        so_error("SD14.1", ""); //Erro na validação das anteriores
        response.msgData.infoCheckIn.pidServidorDedicado = PID_INVALID; 
    } else { //caso tenha havido sucesso na validação dos passos anteriores
        so_success("SD14.1", "");
        // Preenche pidServidorDedicado com o PID do processo servidor dedicado
        response.msgData.infoCheckIn.pidServidorDedicado = getpid();
        // Preenche lugarEscolhido com EMPTY_SEAT
        response.msgData.infoCheckIn.lugarEscolhido = EMPTY_SEAT;
        // Copia a estrutura infoVoo do elemento indexFlight da lista de voos
        response.msgData.infoVoo = database->listFlights[indexFlight];
        result = 0; //sucesso
    }
    // Envia a mensagem para a fila de mensagens
    if (msgsnd(msgId, &response, sizeof(response.msgData), 0) != -1){ //msgsnd para enviar, ao msgId identificado no inicio, response a mensagem a enviar, sizeof do que pretendemos enviar (excluindo o tipo de mensagem) e 0 o número de flags.
    so_success("SD14.2",""); //sucesso a enviar a mensagem
    result = 0;
    }
    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief SD15: Ler a descrição da tarefa no enunciado
 * @return RETURN_SUCCESS (0) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int readResponseClient_SD15 () {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");
    //SD15 - ler uma mensagem com tipo pidServidorDedicado e guarda na variável clientRequest
    if (msgrcv(msgId, &clientRequest, sizeof(clientRequest.msgData), getpid(), 0) == -1) { //vai ler uma msg em msgId, guardar em clientRequest, tamanho da msgData (pois o tipo n interessa guardar), igual ao getpid() do servidor dedicado associado, flags 0
        so_error("SD15", ""); //caso erro na operação anterior, erro na leitura da mensagem
    }
    else{ //caso tenha tido sucesso, success conforme enunciado
        so_success("SD15", "%d %d %d", clientRequest.msgData.infoCheckIn.nif, clientRequest.msgData.infoCheckIn.lugarEscolhido, clientRequest.msgData.infoCheckIn.pidCliente);
        result = 0;
    }
    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief SD16: Ler a descrição da tarefa no enunciado
 * @return RETURN_SUCCESS (0) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int updateFlightDB_SD16 () {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");

    so_success("SD16.1","Start Reserva lugar: %s %d %d",clientRequest.msgData.infoCheckIn.nrVoo, clientRequest.msgData.infoCheckIn.nif, clientRequest.msgData.infoCheckIn.lugarEscolhido); //msg inicio conforme enunciado
    int escolha_lugar = clientRequest.msgData.infoCheckIn.lugarEscolhido; //lê no clientrequest o indice do lugarescolhido

    struct sembuf smf; //criacao sembuf para utilizarmos operacoes num semáforo e sincronizar concorrência SDs
    // Baixa o semáforo (p operação)
    smf.sem_num = SEM_VOOS; //vamos aceder ao semáforo SEM_VOOS
    smf.sem_op = -1; //operacao a realizar, decrementar 1
    smf.sem_flg = 0; //flags a 0
    semop(semId, &smf, 1); //semop para operacao semaforo, semId para aceder ao grupo de semaforos criado anteriormente, ponteiro para aceder à struct sembuff smf criada anteriormente, 1 numero de operacoes a ser realizada

    // Verifica se o lugar está disponível SD16.2
    if (database->listFlights[indexFlight].lugares[escolha_lugar] != EMPTY_SEAT) { //aceder à base de dados, listflights no indice identificado, e no indice lugarEscolhido
        // Sobe o semáforo pois o lugar ja esta ocupado (!= EMPTY_SEAT)
        smf.sem_op = 1; //set operacao a 1
        semop(semId, &smf, 1); //incrementar 1
        so_error("SD16.2", "Cliente %d: Lugar já estava ocupado",clientRequest.msgData.infoCheckIn.nif); //msg lugar ocupado conforme enunciado
        return result;
    }
    else{ //Lugar não ocupado... segue para o SD16.3
        sleep(4); //sleep 4s conforme enunciado
    }
    // SD16.4 Atualiza o lugar como ocupado pois ja verificamos que nao estava
    database->listFlights[indexFlight].lugares[escolha_lugar] = clientRequest.msgData.infoCheckIn.nif;

    smf.sem_op = 1; //operacao incrementar o semaforo
    semop(semId, &smf, 1); 

    database->listClients[indexClient].lugarEscolhido = escolha_lugar; //definir na BD cliente o lugar escolhido pelo cliente já assumido. Aqui não há problemas de concorrencia porque já foram lidados anteriormente

    so_success("SD16.6", "End Reserva lugar: %s %d %d", clientRequest.msgData.infoCheckIn.nrVoo, clientRequest.msgData.infoCheckIn.nif, clientRequest.msgData.infoCheckIn.lugarEscolhido);
    result = 0; //sucesso

    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief SD17: Ler a descrição da tarefa no enunciado
 * @return RETURN_SUCCESS (0) em caso de sucesso, ou RETURN_ERROR (-1) em caso de erro
 */
int sendConfirmationClient_SD17 () {
    int result = RETURN_ERROR; // Por omissão, retorna erro
    so_debug("<");

    //Criação mensagem do tipo MsgContent para confirmarmos lugar reservado ao cliente
    MsgContent confirmation;
    confirmation.msgType = clientRequest.msgData.infoCheckIn.pidCliente; //msg type adquire valor do pidCliente conforme enunciado
    confirmation.msgData.infoCheckIn.pidServidorDedicado = getpid(); //preenche campo do pidServidorDedicado com o pid do SD que correr neste momento
    confirmation.msgData.infoCheckIn.lugarEscolhido = clientRequest.msgData.infoCheckIn.lugarEscolhido; //preenche na msg que pretendemos enviar o indice do lugar escolhido pelo cliente
    //preenche campos origem e destino
    strcpy(confirmation.msgData.infoVoo.origem, clientRequest.msgData.infoVoo.origem);
    strcpy(confirmation.msgData.infoVoo.destino, clientRequest.msgData.infoVoo.destino);
    if (msgsnd(msgId, &confirmation, sizeof(confirmation.msgData), 0) == -1) { //enviar mensagem atraves de msgsnd para a msgId, somente informacoes dispostas em msgData (infos pertinentes)
        so_error("SD17",""); //caso dê -1 na operacao anterior, erro ao enviar
        return -1;
    }
    else{ //caso contrario, sucesso no envio da mensagem
        so_success("SD17","");
        result = 0;
    }

    so_debug("> [@return:%d]", result);
    return result;
}

/**
 * @brief SD18: Ler a descrição da tarefa no enunciado
 */
void terminateServidorDedicado_SD18 () {
    so_debug("<");
    if (indexClient >= 0){ // Atualiza os campos pidCliente e pidServidorDedicado no indexClient
    database->listClients[indexClient].pidCliente = PID_INVALID;
    database->listClients[indexClient].pidServidorDedicado = PID_INVALID;
    so_success("SD18",""); //Sucesso
    nrServidoresDedicados--; //decrementa variavel numero servidores dedicados
    }

    so_debug(">");
    exit(0);
}

/**
 * @brief SD19: Ler a descrição da tarefa no enunciado
 * @param sinalRecebido nº do Sinal Recebido (preenchido pelo SO)
 */
void trataSinalSIGUSR1_SD19 (int sinalRecebido) {
    so_debug("< [@param sinalRecebido:%d]", sinalRecebido);
    so_success("SD19", "SD: Recebi pedido do Cliente para terminar"); //conforme enunciado
    terminateServidorDedicado_SD18 (); //terminar SD cfr enunciado
    exit(0); //terminar com sucesso
    so_debug(">");
}

/**
 * @brief SD20: Ler a descrição da tarefa no enunciado
 * @param sinalRecebido nº do Sinal Recebido (preenchido pelo SO)
 */
void trataSinalSIGUSR2_SD20 (int sinalRecebido) {
    so_debug("< [@param sinalRecebido:%d]", sinalRecebido);
    //Success
    so_success("SD20", "SD: Recebi pedido do Servidor para terminar");

    // Verifica se há um cliente associado
    if (clientRequest.msgData.infoCheckIn.pidCliente != PID_INVALID) {
        // Envia sinal SIGHUP para o processo cliente
        kill(clientRequest.msgData.infoCheckIn.pidCliente, SIGHUP);
    }
    terminateServidorDedicado_SD18(); //terminar SD cfr enunciado
    so_debug(">");
}