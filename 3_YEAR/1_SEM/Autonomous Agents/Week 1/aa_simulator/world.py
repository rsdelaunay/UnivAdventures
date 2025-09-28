import random      # Importa o módulo random para operações aleatórias
import time        # Importa o módulo time para manipular tempo

from agent import Agent, Direction   # Importa as classes Agent e Direction do arquivo agent.py

class World:       # Define a classe World que representa o ambiente
    #atributos
    size = 0       # Variável de classe para o tamanho do mundo (não usada diretamente)
    agents = []    # Variável de classe para os agentes (não usada diretamente)

    #construtor
    def __init__(self, size, agents:list[Agent]):   # Inicializa o mundo com tamanho e lista de agentes
        self.agents = agents       # Salva a lista de agentes no atributo de instância
        self.size = size           # Salva o tamanho do mundo no atributo de instância

    #metodos
    def print(self):               # Método para imprimir o estado atual do mundo (percorre a grid toda do mundo iterativamente)
        for y in range(self.size):         # Percorre cada linha do mundo
            for x in range(self.size):     # Percorre cada coluna do mundo
                # Procura um agente na posiçãco (x, y)
                an_agent = next((a for a in self.agents if a.get_pos() == (x, y)), None)
                if an_agent:   # Se encontrou um agente
                    # Imprime a primeira letra do nome do agente em maiúsculo
                    print(" " + an_agent.get_name()[0:1].upper() + " ", end="")
                else:          # Se não há agente na posição
                    print(" . ", end="")   # Imprime um ponto para indicar espaço vazio
            print("")   # Quebra de linha ao final de cada linha do mundo

    def update(self):            # Método para atualizar a posição dos agentes
        random.shuffle(self.agents)   # Embaralha a ordem dos agentes para movimentação aleatória
        for a in self.agents:         # Para cada agente
            while True:               # Tenta mover até encontrar uma posição válida
                new_position = a.get_next_pos(a.get_random_move())   # Calcula nova posição aleatória
                # Verifica se a nova posição está fora dos limites do mundo
                if new_position[0] < 0 or new_position[0] >= self.size:
                    continue   # Se fora dos limites em x, tenta novamente
                if new_position[1] < 0 or new_position[1] >= self.size:
                    continue   # Se fora dos limites em y, tenta novamente
                # Verifica se a nova posição já está ocupada por outro agente
                is_occupied = next((a2 for a2 in self.agents if a2.get_pos() == new_position), None)
                if is_occupied:
                    continue   # Se ocupada, tenta novamente
                else:
                    a.set_new_position(new_position)   # Atualiza a posição do agente
                    break      # Sai do loop para este agente

def main():    # Função principal do programa
    ITERATIONS = 60           # Número de iterações do loop principal
    LOOP_INTERVAL = 500       # Intervalo entre iterações em milissegundos

    test_agents = [ Agent((0,0), "Smith"), Agent((1,1), "Nicolas")]   # Cria dois agentes de teste
    world = World(5, test_agents)     # Cria o mundo de tamanho 5x5 com os agentes

    for i in range(ITERATIONS):       # Executa o loop principal
        world.print()                 # Imprime o estado atual do mundo
        print("")                     # Imprime uma linha em branco
        time.sleep(LOOP_INTERVAL / 1000)   # Aguarda o intervalo definido (em segundos)
        world.update()                # Atualiza a posição dos agentes

main()   # Executa a função principal