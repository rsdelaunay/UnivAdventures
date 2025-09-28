# Elaborado por : 122123 Rodrigo Delaunay, 111651 Lurdes Horta 

import numpy as np

class NeuralNetwork:
    # Neural Network Feedforward cfr enunciado
    # Dados processados numa só direção - entrada para saida. Sem ciclos.

    def __init__(self, input_size, hidden_architecture, hidden_activation, output_activation): #iniciar rede neuronal
        #input_size - numero neuronios camada de entrada rede (10 para estado jogo Fruit Catcher)
        #hidden_architecture - tupla que define numero neuronios em cada camada oculta
        #hidden_activation - funcao ativacao a ser usada pelos neuronios nas camadas ocultas (Sigmoide cfr enunciado)
        #output_activation - funcao ativacao a ser usada pelo neuronio da camada de saida (Sinal para acao -1/+1 cfr enunciado)
        
        #inicializar atributos
        self.input_size = input_size
        self.hidden_architecture = hidden_architecture
        self.hidden_activation = hidden_activation
        self.output_activation = output_activation

        # listas para armazenar os pesos e biases das camadas ocultas. (load_weights vai preencher)
        self.hidden_weights = []
        self.hidden_biases = []

        # variáveis para armazenar os pesos e bias da camada de saida (load_weights vai preencher)
        self.output_weights = None
        self.output_bias = None

    def compute_num_weights(self): #dá numero total de pesos e biases da rede neuronal

        num_weights = 0
        #comecar c numero entradas rede (primeira camada -> oculta ou de saida).
        current_layer_input_size = self.input_size

        #itterar por cada camada oculta definida na estrutura
        for num_neurons_in_hidden_layer in self.hidden_architecture: 
        #para cada neuronio numa camada oculta, ele precisa de:
        # (nº de entradas da camada anterior + 1 para o bias) * nº de neurónios na camada atual
            num_weights += (current_layer_input_size + 1) * num_neurons_in_hidden_layer
            #a saida da camada atual torna-se a entrada para a prox camada
            current_layer_input_size = num_neurons_in_hidden_layer

        #adiciona os pesos e bias da camada de saida
        num_weights += (current_layer_input_size + 1) * 1
        
        return num_weights

    def load_weights(self, weights): #carregar lista unidimensional de pesos e biases (gerada pelo algoritmo genetico)
        #converter a lista de pesos numa array NumPy para mexer mais facilmente
        w = np.array(weights)

        #limpa quaisquer pesos/biases ocultos existentes antes de carregar os novos
        self.hidden_weights = []
        self.hidden_biases = []

        #'start_idx' é um ponteiro que indica onde no array 'w' estamos a ler os pesos
        start_idx = 0
        #'current_layer_input_size' controla o número de entradas para a camada que esta a ser carregada
        current_layer_input_size = self.input_size

        #loop para carregar os pesos e biases de cada camada oculta definida
        for num_neurons_in_hidden_layer in self.hidden_architecture:
            #numero total pesos + bias para a camada atual.
            layer_total_params = (current_layer_input_size + 1) * num_neurons_in_hidden_layer
            
            #extrai os biases da camada atual -> são os primeiros 'num_neurons_in_hidden_layer' valores
            biases_current_layer = w[start_idx : start_idx + num_neurons_in_hidden_layer]
            
            #Extrai os pesos da camada atual e transforma numa matriz (reshape)
            #a matriz terá "current_layer_input_size" linhas (entradas da camada anterior)
            # e 2num_neurons_in_hidden_layer" colunas (neuronios na camada atual).
            weights_current_layer = w[start_idx + num_neurons_in_hidden_layer : start_idx + layer_total_params].reshape(current_layer_input_size, num_neurons_in_hidden_layer)
            
            #adicionar os biases e pesos carregados as listas correspondentes.
            self.hidden_biases.append(biases_current_layer)
            self.hidden_weights.append(weights_current_layer)

            #atualiza 'start_idx' para apontar para o início dos parâmetros da próxima camada.
            start_idx += layer_total_params
            #a saida da camada atual torna-se a entrada para a próxima camada.
            current_layer_input_size = num_neurons_in_hidden_layer

        # carregar o bias do neuronio da camada de saida (prox valor de w)
        self.output_bias = w[start_idx]
        # carrega os pesos do neuronio da camada de saida (restantes valores de w).
        # concta a ultima camada processada (oculta ou de entrada) ao neuronio de saida
        self.output_weights = w[start_idx + 1 : start_idx + 1 + current_layer_input_size]


    def forward(self, x): #propagação feedforward da rede neuronal
        a = np.array(x, dtype=float) # converte a entrada x (inputs basket_x, fruit_x, ...) para um array NumPy de tipo float
        # normalização dos inputs considerando sigmoid func ativação: passar de [0, 1] para [-1, 1]
        # transforma --> y = 2x - 1 para inputs que vem de 0 a 1.
        # Os inputs de is_fruit sao -1 ou 1,entao nao precisam de ser ajustados.
        
        # criar copia para nao mexer no array original "a"
        a_scaled = np.copy(a) 
        
        #basket_x normalizar cfr funcao acima
        a_scaled[0] = a_scaled[0] * 2 - 1 

        # escalar fruit_x e fruit_y para as 3 frutas/bombas
        for i in range(3):
            # fruit_x
            a_scaled[1 + i * 3] = a_scaled[1 + i * 3] * 2 - 1 
            # fruit_y
            a_scaled[2 + i * 3] = a_scaled[2 + i * 3] * 2 - 1 
            # is_fruit (já é -1 ou 1, não precisa de ajuste)
        
        # Propagacao camadas ocultas
        current_activations = a_scaled
        for W_h, b_h in zip(self.hidden_weights, self.hidden_biases):
            z = np.dot(current_activations, W_h) + b_h
            current_activations = self.hidden_activation(z)

        # Propagacao através da camada de saida
        z_output = np.dot(current_activations, self.output_weights) + self.output_bias
        
        return self.output_activation(z_output.item())


def create_network_architecture(input_size): #arquitetura rede neuronal especifica feedforward
    #input_size - numero de entradas para a rede (10 para estado jogo Fruit Catcher)

    # enunciado
    hidden_fn = lambda x: 1 / (1 + np.exp(-x))  # sigmoid ativacao
    output_fn = lambda x: 1 if x > 0 else -1    # sinal
    #enunciado
    
    #neuronio unico

    return NeuralNetwork(input_size, (), hidden_fn, output_fn)
    
    #Nota IMPORTANTE - Apos realizacao de testes conforme enunciado, NN com um unico neuronio provou para uma populacao
    #de 200 e geracoes de 200, ter melhor fitness (40+), enquanto outras arquiteturas com camadas ocultas Feedforward mantiveram
    #valores de fitness gerais ligeiramente inferiores (30-35), apesar de terem mais pesos e complexidade.
    #
    #Isto podera dever-se ao facto do processo de normalizacao dos inputs e a funcao de ativacao utilizada (sigmoid) terem
    #limitado a capacidade do Algoritmo Genetico de otimizar eficazmente os pesos, resultando em gradientes quase nulos que impedem
    #melhor aprendizagem profunda.
    #
    #Como sugestao, se fosse possivel alterar a funcao de ativacao da Sigmoid por funcoes de ativacao como Tanh ou ReLU,
    #seria possivel mitigar o problema anteriormente referido e provavelmente melhorar o desempenho da rede feedforward.