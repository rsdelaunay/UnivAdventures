# Elaborado por : 122123 Rodrigo Delaunay, 111651 Lurdes Horta 

import random

def create_individual(individual_size): #criar individuo com pesos aleatórios [-1;1]
    return [random.uniform(-1, 1) for _ in range(individual_size)]

def generate_population(individual_size, population_size): #gerar população inicial
    return [create_individual(individual_size) for _ in range(population_size)]

def crossover(parent1, parent2): #cruzar dois pais para gerar um filho

    # Garante que os pais têm o mesmo tamanho e que o ponto de corte e valido
    if len(parent1) != len(parent2) or len(parent1) < 2:
        #se os pais são muito pequenos ou têm tamanhos diferentes,
        #simplesmente retorna uma cópia de um dos pais (ou um novo indivíduo, dependendo da estratégia)
        return list(parent1)

    point = random.randint(1, len(parent1) - 1)
    return parent1[:point] + parent2[point:]

def mutate(individual, mutation_rate, mutation_strength=0.1, min_gene_value=-1.0, max_gene_value=1.0): #alteracoes de genes
    #para manter a diversidade genética
    #individual - individuo -- lista de genes (pesos)
    # mutation_rate - probabilidade de um gene sofrer mutação
    # min_gene_value - valor mínimo que um gene pode ter
    # max_gene_value - valor máximo que um gene pode ter
  
    mutated_individual = []
    for gene in individual:
        if random.random() < mutation_rate: # probabilidade de mutação
            new_gene = gene + random.uniform(-mutation_strength, mutation_strength) #aplicar mutação
            new_gene = max(min_gene_value, min(max_gene_value, new_gene)) # limitar o novo gene para o intervalo [-1, 1]
            mutated_individual.append(new_gene)
        else:
            mutated_individual.append(gene)
    return mutated_individual

def genetic_algorithm(individual_size, population_size, fitness_function, target_fitness, generations, elite_rate=0.2, mutation_rate=0.05): #algoritmo genético
    # retorna o melhor individuo encontrado e sua aptidão (tuplo (melhor_individuo, melhor_aptidao))

    population = generate_population(individual_size, population_size) #gerar população inicial
    best_individual_overall = None
    best_fitness_overall = float('-inf') #inicializa com o menor valor possível

    for generation in range(generations):
        # Usar cache para evitar recalcular fitness de indivíduos idênticos na mesma geração.
        # Converter para tupla é necessário para usar como chave de dicionario, pois listas não são hashable
        fitness_cache = {}
        fitnesses = []
        for ind in population:
            key = tuple(ind)
            if key not in fitness_cache:
                #passa a geração como uma semente para a função de fitness (se aplicável)
                fitness_cache[key] = fitness_function(ind, generation)
            fitnesses.append(fitness_cache[key])

        # Encontra o melhor indivíduo e sua aptidão na geração atual
        max_fitness_generation = max(fitnesses)
        max_index_generation = fitnesses.index(max_fitness_generation)
        best_individual_generation = population[max_index_generation]

        #atualiza o melhor indivíduo e aptidão encontrados até agora
        if max_fitness_generation > best_fitness_overall:
            best_fitness_overall = max_fitness_generation
            best_individual_overall = list(best_individual_generation) # Cria uma cópia para evitar modificações futuras

        # imprimir progresso geração a geração
        print(f"Geração {generation + 1}: Melhor Fitness = {max_fitness_generation:.4f} | Melhor Geral = {best_fitness_overall:.4f} | Genes iniciais do melhor: {best_individual_generation[:3]}")


        num_elites = int(elite_rate * population_size) # elitismo
        # Garante que pelo menos 1 elite é selecionado, a menos que a população seja muito pequena
        num_elites = max(1, num_elites) if population_size > 0 else 0

        #combina indivíduos com suas fitness e ordena do melhor para o pior
        # 'zip' para emparelhar, 'sorted' para ordenar, 'lambda' para definir a chave de ordenação
        sorted_population_with_fitness = sorted(zip(fitnesses, population), key=lambda pair: pair[0], reverse=True)
        
        #pega apenas os indivíduos (ignorando o fitness) dos melhores
        elites = [ind for fitness, ind in sorted_population_with_fitness[:num_elites]]
        
        #inicia a nova população com os elites
        new_population = list(elites) 

        # gerar nova populacao com crossover e mutação
        # Seleção de pais: usa a metade superior da população classificada (selecao truncada cfr aulas)
        selection_pool = [ind for fitness, ind in sorted_population_with_fitness[:population_size // 2]]

        while len(new_population) < population_size: # enquanto a nova população não atingir o tamanho desejado
            if len(selection_pool) < 2:
                # Se não há pais suficientes, pode gerar um novo indivíduo aleatório ou breaks
                new_population.append(create_individual(individual_size))
                continue

            # Seleciona dois pais aleatoriamente do pool de seleção
            parent1 = random.choice(selection_pool)
            parent2 = random.choice(selection_pool)
            
            # Garante que parent1 e parent2 são diferentes para promover diversidade
            while parent1 == parent2 and len(selection_pool) > 1:
                parent2 = random.choice(selection_pool)

            child = crossover(parent1, parent2)
            child = mutate(child, mutation_rate)
            new_population.append(child)
        
        #substitui a população antiga pela nova
        population = new_population

    print("\nAlgoritmo Genético Terminado.")
    return best_individual_overall, best_fitness_overall