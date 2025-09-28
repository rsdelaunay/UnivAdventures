import random  # Importa o módulo random para gerar valores aleatórios
from enum import Enum, auto  # Importa Enum e auto para criar enums personalizados

class Direction(Enum):  # Define uma enumeração para direções possíveis
    UP = auto()         # Direção para cima
    DOWN = auto()       # Direção para baixo
    LEFT = auto()       # Direção para a esquerda
    RIGHT = auto()      # Direção para a direita

class Agent:  # Define a classe Agent que representa um agente no ambiente

    def __init__(self, pos: tuple[int,int], name: str): #posicao é uma tupla (x,y) 
        #atributos
        self.pos = pos      # Armazena a posição atual do agente como uma tupla (x, y)
        self.name = name    # Armazena o nome do agente

    def get_pos(self) -> tuple[int,int]:
        return self.pos     # Retorna a posição atual do agente

    def get_name(self) -> str:
        return self.name    # Retorna o nome do agente

    def get_random_move(self) -> Direction:
        random_direction = random.choice(list(Direction))  # Escolhe uma direção aleatória
        return random_direction                            # Retorna a direção escolhida

    def get_next_pos(self, direction: Direction) -> tuple[int,int]:
        match direction:  # Usa pattern matching para decidir o próximo movimento
            case Direction.UP:
                # print(self.name + " might go UP")
                return (self.pos[0], self.pos[1] - 1)  # Move para cima (y - 1)
            case Direction.DOWN:
                # print(self.name + " might go DOWN")
                return (self.pos[0], self.pos[1] + 1)  # Move para baixo (y + 1)
            case Direction.LEFT:
                # print(self.name + " might go LEFT")
                return (self.pos[0] - 1, self.pos[1])  # Move para a esquerda (x - 1)
            case Direction.RIGHT:
                # print(self.name + " might go RIGHT")
                return (self.pos[0] + 1, self.pos[1])  # Move para a direita (x + 1)

    def set_new_position(self, pos: tuple[int,int]) -> None:
        self.pos = pos  # Atualiza a posição do agente