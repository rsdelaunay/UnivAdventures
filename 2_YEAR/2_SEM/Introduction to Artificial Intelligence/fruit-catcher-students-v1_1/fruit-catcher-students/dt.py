# Elaborado por : 122123 Rodrigo Delaunay, 111651 Lurdes Horta 
#  
import numpy as np
import csv

def entropy(y): #calcular entropia conjunto de rotulos. 
    #mede mistura de um conjunto de classes
    total = len(y)
    if total == 0:
        return 0.0
    values = {}
    for label in y:
        values[label] = values.get(label, 0) + 1

    #forrmula da entropia: -sum(p_i * log2(p_i))
    return -sum((count / total) * np.log2(count / total) for count in values.values())

def most_common_label(y): #encontrar caracteristica mais comum
    if not y:
        return None
    label_counts = {}
    for label in y:
        label_counts[label] = label_counts.get(label, 0) + 1
    return max(label_counts, key=label_counts.get)


class DecisionTree:
    def __init__(self, X, y, threshold=0.0, max_depth=None, current_depth=0): #construtor da árvore de decisão
        #X - caracteristicas dados treino
        #y - rótulos correspondentes aos dados de treino
        #threshold - limiar de entropia para parar divisao
        #max_depth - profundidade máxima da árvore
        #current_depth - profundidade atual do nó na árvore (0 para a raiz)

        # atributos da arvore decisao (inputs funcao)
        self.threshold = threshold
        self.max_depth = max_depth
        self.current_depth = current_depth
        # Define caracteristica predominante para este no, caso ele se torne uma folha.
        #seria a previsão que a árvore faria se parasse de dividir aqui.
        self.label = most_common_label(y)

        # Atributos para definir se o no é uma folha e qual feature foi usada para dividir
        self.is_leaf = False      # Verdadeiro se este no for um nó folha (fim divisao)
        self.feature_index = None # O índice da feature usada para a melhor divisão neste nó.
        self.children = {}        # dicionario para nos filhos

        # Condicoes para parar de crescer (nó torne-se folha)
        # 1. 'y' estiver vazio: Não há dados para dividir.
        # 2. A entropia de 'y' for menor ou igual ao threshold-> dados puros o suf
        # 3. A profundidade atual (current_depth) atingiu a max_depth: Para evitar arvores muito grandes.
        if not y or entropy(y) <= self.threshold or \
           (self.max_depth is not None and self.current_depth >= self.max_depth): #condicoes para parar
            self.is_leaf = True #marcar no comp uma folha pois cumpriu condicoes para parar
            return

        # viariaveis para armazenar o melhor ganho de informação, índice da feature e os subconjuntos criados
        #para decidir qual feature dividir os dados
        best_gain = 0           # maior ganho
        best_feature_index = None # indice que gerou o maior ganho
        best_splits = {}          # Armazena os subconjuntos de dados criados pela melhor divisao

        if not X:# para segurança - caso X estiver vazio, não há features para processar.
            self.is_leaf = True
            return

        num_features = len(X[0]) #numero features (colunas) de x
        
        #iterar sobre cada feature (coluna) dos dados de entrada para procurar a melhor caracteristica para dividir
        for i in range(num_features):
            current_splits = {} # Dicionário temporário para armazenar as divisões para a feature 'i' atual. Chaves
            #sao o valor da feature, X ou y para esse valor.

            #itera sobre cada exemplo (linha) nos dados de treinamento.
            for xi, label in zip(X, y):
                feature_val = xi[i] #i para feature index atual
                if feature_val not in current_splits: #nao visto - nova entrada dicionario
                    current_splits[feature_val] = {'X': [], 'y': []}
                #adiciona o exemplo e seu rótulo ao grupo correspondente ao valor da feature
                current_splits[feature_val]['X'].append(xi)
                current_splits[feature_val]['y'].append(label)

            # Calcula ganho info para a divisao feature 'i'-->  
            # entropia do conjunto original menos a entropia ponderada dos subconjuntos
            gain = entropy(y) # Entropia antes da divisão
            for group_data in current_splits.values(): # iterar grupos criados pela divisao
                gain -= (len(group_data['y']) / len(y)) * entropy(group_data['y'])

            if gain > best_gain: #atualiza caso melhor ganho encontrado
                best_gain = gain
                best_feature_index = i
                best_splits = current_splits

        # Se o melhor ganho de informação encontrado for 0, significa que nenhuma feature
        # conseguiu melhorar a pureza dos dados. Neste caso, o nó torna-se uma folha.
        if best_gain == 0:
            self.is_leaf = True
            return

        #boa divisao encontrada -> criar nos filhos

        self.feature_index = best_feature_index # indice da feature que foi escolhida para dividir este nó
        # Para cada subconjunto de dados criado pela melhor divisão,
        # recursivamente cria um novo nó filho - nova instancia de DecisionTree
        for feature_val, group_data in best_splits.items():
            child = DecisionTree(
                group_data['X'],
                group_data['y'],
                threshold=self.threshold,
                max_depth=self.max_depth,
                current_depth=self.current_depth + 1 # Incrementa a profundidade para o nó filho.
            )
            # Associa o nó filho ao valor da feature que o levou a esse caminho.
            self.children[feature_val] = child


    def predict(self, x): #classificar novo item com a arvore de decisao
        #x - vetor de features do item a ser classificado
        #retorna o rótulo previsto para o item 'x' (1 para fruta, -1 para bomba)

        if self.is_leaf: #caso seja folha, estamos no final -> logo rotulo mais comum. self.label
            return self.label
        
        feature_val = x[self.feature_index] #caso n seja folha, temos que ver para onde vai no proximo no
        #valor da feature que este nó usa para dividir os dados

        if feature_val in self.children:# existe um nó filho correspondente a este valor de feature
            #recursivamente o método 'predict' no nó filho correspondente para descer ate ao no folha
            return self.children[feature_val].predict(x)
        else: #caso a feature de 'x' nao exista - arvore nao sabe para onde ir
            # neste caso, nó retorna o seu próprio rótulo mais comum (self.label) como previsão
            return self.label 
 
def train_decision_tree(X, y, threshold=0.0, max_depth=None): # funcao auxiliar para treinar a arvore de decisao
    # X - lista de listas com as features dos dados de treino enunciado
    # y - lista com os rótulos correspondentes aos dados de treino enunciado

    # Cria e retorna um objeto DecisionTree que treian no init
    return DecisionTree(X, y, threshold=threshold, max_depth=max_depth)