import random as rnd

# Class Vertice
class Vertex:
    ''' Estrutura de Vértice para um grafo: encapsula um elemento (vertex_id)
        que é o identificador deste nó.

        O elemento (vertex_id) deve ser hashable:
        - Um objeto hashable é aquele que pode ser utilizado como uma chave num dicionário Python.
        - Isto inclui strings, números, tuplas, etc.
    '''

    def __init__(self, vertex_id):
        '''O vértice será inserido no Grafo usando o método insert_vertex(x) que cria um Vertex'''
        self._vertex_id = vertex_id  # Id do vértice (elemento a inserir no grafo)

    def __hash__(self):
        '''O valor do elemento é usado como hash para o vértice (o elemento deve ser hashable)'''
        return hash(self._vertex_id)  # devolve o hash do elemento

    def __str__(self):
        '''Devolve a representação do objeto vértice em string.'''
        return 'v{0}'.format(self._vertex_id)

    def __eq__(self, vertex):
        return self._vertex_id == vertex._vertex_id  # Deve-se garantir que: se hash(vertex)==hash(self), entao vertex==self

    def __lt__(self, vertex):
        return self._vertex_id < vertex._vertex_id

    def __le__(self, vertex):
        return self._vertex_id <= vertex._vertex_id

    def __gt__(self, vertex):
        return self._vertex_id > vertex._vertex_id

    def __ge__(self, vertex):
        return self._vertex_id >= vertex._vertex_id

    def vertex_id(self):
        ''' Devolve o elemento guardado neste vértice.'''
        return self._vertex_id


# Class Edge
class Edge:
    ''' Estrutura de Aresta para um Grafo: (origem, destino) e peso '''

    def __init__(self, vertex_1, vertex_2, weight):
        self._vertex_1 = vertex_1
        self._vertex_2 = vertex_2
        self._weight = weight

    def __hash__(self):
        # Função que mapeia a aresta a uma posição no dicionário (hash map)
        return hash((self._vertex_1, self._vertex_2))

    def __str__(self):
        ''' Devolve a representação do objeto aresta em string: (origem, destino)w=peso '''
        return 'e({0},{1})w={2}'.format(self._vertex_1, self._vertex_2, self._weight)

    def __eq__(self, other):
        # define igualdade de duas arestas (deve ser consistente com a função hash)
        return self._vertex_1 == other._vertex_1 and self._vertex_2 == other._vertex_2

    def endpoints(self):
        ''' Devolve a tupla (vertex_1, vertex_2) os vértices adjacentes vertex_1 e vertex_2.'''
        return (self._vertex_1, self._vertex_2)

    def cost(self):
        ''' Devolve o peso associado a este arco.'''
        return self._weight

    def opposite(self, vertex):
        ''' Indica o vértice oposto ao vertex nesta aresta
            (apenas se vertex fizer parte da aresta).'''
        if vertex == self._vertex_1:
            return self._vertex_2
        elif vertex == self._vertex_2:
            return self._vertex_1
        else:
            return None


class Graph:
    '''
    Representação de um grafo usando dicionários encadeados (nested dictionaries).

    Atributos:
    ----------
    adjancencies: Dicionário externo que associa um vértice (Vertex) a um
                  mapa de adjacências (dicionario interno)
                  Em vez de utilizar uma estrutura com um array (como nos slides),
                  utiliza uma estrutura do tipo "dicionário de dicionários":
                  < vertex , < neighbor_vertex , edge > >

    vertices: Dicionário auxiliar que associa o id dos vértices do grafo
              a um objeto Vertex (tabela de símbolos):
              < vertex_id , vertex >

    n: Número de vértices no Grafo
    m: Número de arestas no Grafo

    ----------
'''

    def __init__(self):
        '''Construtor: Cria um grafo vazio (dicionário de _adjancencies).'''
        self._adjancencies = {}  # dicionário que associa o par chave-valor: <Vertex v, Mapa de adjacências de v>
        self._vertices = {}  # dicionário que associa o par: <id do vértice, objeto Vertex correspondente>
        self._n = 0  # número de vértices do grafo
        self._m = 0  # número de arestas do grafo

    def __str__(self):
        '''Devolve a representação do grafo em string (toString)'''
        if self._n == 0:
            ret = "DAA-Graph: <empty>\n"
        else:
            ret = "DAA-Graph:\n"
            for vertex in self._adjancencies.keys():
                # ret += "vertex-"
                ret += str(vertex) + ": "
                for edge in self.incident_edges(vertex.vertex_id()):
                    ret += str(edge) + "; "
                ret += "\n"
        return ret

    def is_directed(self):
        '''A classe Graph representa um grafo não orientado.'''
        return False

    def order(self):
        '''Ordem de um grafo: a quantidade de vértices no Grafo.'''
        return self._n

    def size(self):
        '''Dimensão de um grafo: a quantidade total de arestas do Grafo.'''
        return self._m

    def has_vertex(self, vertex_id):
        '''Verifica se o vértice de id vertex_id está no grafo.'''
        return vertex_id in self._vertices

    def has_edge(self, u_id, v_id):
        '''Verifica se a aresta (u_id, v_id) existe no grafo.'''
        if not self.has_vertex(u_id) or not self.has_vertex(v_id):
            return False
        else:
            vertex_u = self._vertices[u_id]
            vertex_v = self._vertices[v_id]
            return vertex_v in self._adjancencies[vertex_u]

    def insert_vertex(self, vertex_id):
        '''Insere um novo vértice com o id vertex_id.'''
        if not self.has_vertex(vertex_id):
            vertex = Vertex(vertex_id)  # instancia um objeto do tipo Vertex
            self._vertices[vertex_id] = vertex  # insere o novo vértice no dicionário de vertices
            self._adjancencies[vertex] = {}  # inicializa o mapa de adjacências deste vértice a vazio
            self._n += 1  # mais um vértice no grafo

    def insert_edge(self, u_id, v_id, weight=0):
        ''' Cria e insere uma nova aresta entre u_id e v_id com peso weight.
            Se a aresta já existe no grafo, atualiza-se o seu peso.
            Também insere os vértices u_id e v_id, caso não existam.'''
        if not self.has_vertex(u_id):
            self.insert_vertex(u_id)  # insere novo vertex e atualiza n
        if not self.has_vertex(v_id):
            self.insert_vertex(v_id)  # insere novo vertex e atualiza n
        if not self.has_edge(u_id, v_id):
            self._m += 1  # atualiza m apenas se a aresta ainda não existir no grafo
        else:
            print(f"Existing edge {u_id} and {v_id}. Will only update weight")
        vertex_u = self._vertices[u_id]  # acede ao objeto Vertex associado a u_id
        vertex_v = self._vertices[v_id]  # acede ao objeto Vertex associado a v_id
        e = Edge(vertex_u, vertex_v, weight)
        self._adjancencies[vertex_u][vertex_v] = e  # coloca v nas adjacências de u
        self._adjancencies[vertex_v][
            vertex_u] = e  # e u nas adjacências de v (para facilitar a procura de todas as arestas incidentes num vértice)

    def degree(self, vertex_id):
        '''Quantidade de arestas incidentes no vértice v.
        '''
        return len(self._adjancencies[self._vertices[vertex_id]])

    def get_vertex(self, vertex_id):
        ''' Devolve o objeto Vertex associado ao elemento vertex_id no grafo
        '''
        return None if not self.has_vertex(vertex_id) else self._vertices[vertex_id]

    def get_edge(self, u_id, v_id):
        ''' Devolve o objeto aresta (Edge) que liga u_id a v_id.
            Devolve None se não forem adjacentes ou se (um d)os vértices não existirem.'''
        if not self.has_edge(u_id, v_id):
            return None
        else:
            vertex_u = self._vertices[u_id]
            vertex_v = self._vertices[v_id]
            return self._adjancencies[vertex_u][vertex_v]

    def vertices(self):
        '''Devolve um iterável sobre todos os vértices do Grafo (tipo Vertex)'''
        return self._vertices.values()

    def edges(self):
        '''Devolve um iterável sobre todas as arestas do Grafo (sem arestas duplicadas).'''
        seen = {}  # evita a repetição de arestas no grafo não orientado
        for adj_map in self._adjancencies.values():
            for edge in adj_map.values():
                if edge not in seen:
                    yield edge
                seen[edge] = True

    def incident_edges(self, vertex_id):
        '''Devolve um iterável (gerador) com todas as arestas de um vértice com id vertex_id.'''
        vertex = self._vertices[vertex_id]
        for edge in self._adjancencies[vertex].values():  # para todas as arestas incidentes em v:
            yield edge

    def has_neighbors(self, vertex_id):
        '''Verifica se o vértice de id vertex_id tem vértices adjacentes (vizinhos).'''
        if not self.has_vertex(vertex_id):
            return False
        return self.degree(vertex_id) == 0

    def remove_vertex(self, vertex_id):
        '''Remove o vértice com id vertex_id. Se o vértice não existir, não faz nada.'''
        # Passo 1: remover todas as arestas do vértice dado
        # Passo 2: remover todas as arestas incidentes em vertex_id dos mapas de outros vertices
        # Passo 3: remover o vértice com id vertex_id do grafo
        # Passo 4: decrementa contador de vértices
        if self.has_vertex(vertex_id):
            lst_copied = list(self.incident_edges(
                vertex_id))  # copia para a lista para evitar erros de concorrência (remove enquanto itera na lista)
            for edge in lst_copied:
                x, y = edge.endpoints()
                self.remove_edge(x.vertex_id(), y.vertex_id())  # (Passos 1 e 2)
            del self._adjancencies[self._vertices[vertex_id]]  # (Passo 3 - remove do dicionário de adjacências)
            del self._vertices[vertex_id]  # (Passo 3 - remove do dicionário de vértices)
            self._n -= 1  # (Passo 4 - decrementa contador)

    def remove_edge(self, u_id, v_id):
        '''Remove a aresta entre u_id e v_id. Se a aresta não existir, não faz nada.'''
        if self.has_edge(u_id, v_id):
            vertex_u = self._vertices[u_id]
            vertex_v = self._vertices[v_id]
            del self._adjancencies[vertex_u][vertex_v]
            if vertex_u != vertex_v:  # laços são removidos apenas uma vez
                del self._adjancencies[vertex_v][vertex_u]
            self._m -= 1

g = Graph()                 # criar uma instância Grafo não orientado
vert_list = []              # lista auxiliar para guardar os vértices inseridos para construção das arestas
for i in range(0, 10):
    g.insert_vertex(i)
    vert_list.append(i)     # inserção dos 10 vertices V = {0, 1, ..., 9} no grafo e na lista de vertices

rnd.seed(10)                # para futura replicação deste grafo
for i in range(1, 21):      # criação de 20 arestas a partir dos vértices inseridos
    u, v = rnd.sample(vert_list, k=2)   # gera aleatoriamente uma aresta entre 2 vértices deste grafo
    x = rnd.randint(1, 10)              # com peso inteiro aleatório entre 0 e 10
    g.insert_edge(vert_list[u], vert_list[v], x)   # inserção desta aresta no grafo

print(g)

print('Ordem:', g.order(),'Tamanho:', g.size())

print(g.has_vertex(vert_list[2]))
print(g.has_vertex(vert_list[6]))
print(g.has_vertex(10))

# garanta que também escolhe uma aresta que existe no grafo
print(g.has_vertex(vert_list[2]))
print(g.has_vertex(vert_list[5]))

print(g.has_edge(vert_list[2],vert_list[5]))
print(g.get_edge(vert_list[2],10))

# teste à remoção de uma aresta
print(vert_list)
g.remove_edge(vert_list[7],vert_list[6])
print(g)

# teste à remoção de um vértice
print(vert_list)
g.remove_vertex(vert_list[2])
print(g)

print('Ordem:', g.order(),'Tamanho:', g.size())

print(g.has_vertex(vert_list[2]))
print(g.has_edge(vert_list[1],vert_list[2]))
print(g.get_edge(vert_list[2],vert_list[1]))

# teste aos métodos iteradores do grafo
for v in g.vertices():
    print(v, end=" ")
print()
for e in g.edges():
    print(e, end=" ")

# teste à remoção total (seria preferível fazer um método clear_graph...)
for v in vert_list:
    g.remove_vertex(v)
print(g)
print('Ordem:', g.order(),'Tamanho:', g.size())
print(g.has_edge(vert_list[3],vert_list[0]))




#  ### Classe DiGrafo
class DiGraph(Graph):
    '''
    Representação de um digrafo usando dicionários encadeados (nested dictionaries).

    Atributos:
    ----------
    in_adjancencies: Dicionário dos arcos de entrada (mapa contém antecessores)

    ----------


    Atributos herdados da classe Graph :
    ----------
    adjancencies: Dicionário dos arcos de saída (mapa contém sucessores)
    vertices: Dicionário que associa o id dos nós do grafo a um objeto Vertex (tabela de símbolos).
    n: Número de nós no Grafo
    m: Número de arcos no Grafo
    ----------


    Métodos herdados da classe Graph (que não são sobrescritos nesta classe):
    ----------
    order(): Devolve o número de nós do grafo - Deve-se manter o atributo super()._n atualizado
    size(): Devolve o número de arestas do grafo - Deve-se manter o atributo super()._m atualizado
    has_vertex(v_id):  Verifica se o nó com id v_id está no grafo - Os dois mapas de adjacências devem conter o mesmo conjunto de nós
    has_edge(u_id, v_id): Verifica se existe o arco u_id -> v_id - O atributo super()._adjancencies contém todos os sucessores
    get_edge(u_id, v_id): Devolve o arco u_id -> v_id, se existir.
    degree(v_id): Devolve o mesmo que out_degree(v_id)
    has_neighbors(v_id): Verifica se o nó v_id tem nós adjacentes (sucessores)
    vertices(): Devolve um iterável com os vértices (tipo Vertex) contidos no grafo.
    incident_edges(v_id): Devolve o mesmo que successors(v_id)
    ----------
'''

    def __init__(self):
        '''Construtor: Cria um grafo vazio (dicionário de _adjancencies).'''
        super().__init__()
        self._in_adjancencies = {}  # dicionário com chave vértice e valor mapa de adjacências das arestas de entrada

    def is_directed(self):
        '''A classe DiGraph representa um grafo orientado.'''
        return True

    def __repr__(self):
        '''Devolve a representação completa do grafo em string (debug).'''
        if self._n == 0:
            ret = "DAA-DiGraph out-Representation: <empty>\n"
            ret += "DAA-DiGraph in-Representation: <empty>\n"
        else:
            ret = "DAA-DiGraph out-Representation:\n"
            for vertex in self._adjancencies.keys():
                ret += str(vertex)
                ret += " out-deg " + str(self.out_degree(vertex.vertex_id())) + ":\t"
                for edge in self.successors(vertex.vertex_id()):
                    ret += str(edge) + "; "
                ret += "\n"
            ret += "DAA-DiGraph in-Representation:\n"
            for vertex in self._in_adjancencies.keys():
                ret += str(vertex)
                ret += " in-deg " + str(self.in_degree(vertex.vertex_id())) + ":\t"
                for edge in self.predecessors(vertex.vertex_id()):
                    ret += str(edge) + "; "
                ret += "\n"
        return ret

    def __str__(self):
        '''Devolve a representação do grafo em string.'''
        if self._n == 0:
            ret = "DAA-DiGraph: <empty>\n"
        else:
            ret = "DAA-Graph:\n"
            for vertex in self._adjancencies.keys():
                # ret += "vertex-"
                ret += str(vertex) + ": "
                for edge in self.successors(vertex.vertex_id()):
                    ret += str(edge) + "; "
                ret += "\n"
        return ret

    def is_successor(self, u_id, v_id):
        """Verifica se o nó u é sucessor do vértice v no grafo."""
        return super().has_edge(v_id, u_id)

    def is_predecessor(self, u_id, v_id):
        """ Verifica se o vértice u é antecessor do vértice v no grafo.
            Corresponde ao método super().has_edge(u,v). """
        return super().has_edge(u_id, v_id)

    def insert_vertex(self, vertex_id):
        '''Insere um novo nó com id vertex_id.'''
        super().insert_vertex(vertex_id)  # inicializa o mapa dos arcos de saída (_adjancencies)
        vertex = self._vertices[vertex_id]
        self._in_adjancencies[vertex] = {}  # inicializa o mapa dos arcos de entrada (_in_adjancencies)

    def insert_edge(self, u_id, v_id, weight=0):
        ''' Cria e insere um novo arco entre u_id e v_id com peso weight.
            Se o arco já existe no grafo, atualiza-se o seu peso.
            Também insere os nós u_id e v_id, caso não existam.'''
        if not self.has_vertex(u_id):
            self.insert_vertex(u_id)  # insere novo vertex e atualiza n
        if not self.has_vertex(v_id):
            self.insert_vertex(v_id)  # insere novo vertex e atualiza n
        if not self.has_edge(u_id, v_id):
            self._m += 1  # atualiza m apenas se a aresta ainda não existir no grafo
        else:
            print(f"Existing edge {u_id} and {v_id}. Will only update weight")
        vertex_u = self._vertices[u_id]
        vertex_v = self._vertices[v_id]
        e = Edge(vertex_u, vertex_v, weight)
        self._adjancencies[vertex_u][vertex_v] = e  # coloca v nas adjacências de u
        self._in_adjancencies[vertex_v][
            vertex_u] = e  # e u nas adjacências de v (para facilitar a procura de todas as arestas incidentes num vértice)

    def out_degree(self, vertex_id):
        '''Quantidade de arcos que saem do nó v.
        '''
        return len(self._adjancencies[self._vertices[vertex_id]])  # Verifica o tamanho do mapa de saída de v

    def in_degree(self, vertex_id):
        '''Quantidade de arcos que entram no nó v.
        '''
        return len(self._in_adjancencies[self._vertices[vertex_id]])  # Verifica o tamanho do mapa de entrade de v

    def edges(self):
        '''Devolve um iterável sobre todos os arcos do Grafo.'''
        for adj_map in self._adjancencies.values():
            for edge in adj_map.values():
                yield edge

    def successors(self, vertex_id):  # igual ao super().incident_edges(v)
        '''Devolve um iterável com todos os arcos que saem de v.'''
        return self.incident_edges(vertex_id)
        '''
        vertex = self._vertices[vertex_id]
        for edge in self._adjancencies[vertex].values(): # para todas os arcos que saem do vértice
            yield edge 
            '''

    def predecessors(self, vertex_id):
        '''Devolve um iterável com todos os arcos que entram em v.'''
        vertex = self._vertices[vertex_id]
        for edge in self._in_adjancencies[vertex].values():  # para todas os arcos que entram no vértice
            yield edge

    def remove_edge(self, u_id, v_id):
        '''Remove o arco u_id -> v_id. Se o arco não existir, não faz nada.'''
        if self.has_edge(u_id, v_id):
            vertex_u = self._vertices[u_id]
            vertex_v = self._vertices[v_id]
            del self._adjancencies[vertex_u][vertex_v]
            del self._in_adjancencies[vertex_v][vertex_u]
            self._m -= 1

    def remove_vertex(self, vertex_id):
        '''Remove o nó com id vertex_id. Se o nó não existir, não faz nada.'''
        # Passo 1: remover todos os arcos que saem do vértice dado
        # Passo 2: remover todos os arcos que entram no vértice dado
        # Passo 3: remover o vértice do grafo
        if self.has_vertex(vertex_id):
            lst_out = list(self.successors(vertex_id))  # copia para a lista para evitar erros de concorrência
            for edge in lst_out:
                x, y = edge.endpoints()
                self.remove_edge(x.vertex_id(), y.vertex_id())  # (Passos 1)
            lst_in = list(self.predecessors(vertex_id))  # copia para a lista para evitar erros de concorrência
            for edge in lst_in:
                x, y = edge.endpoints()
                self.remove_edge(x.vertex_id(), y.vertex_id())  # (Passos 2)
            del self._adjancencies[self._vertices[vertex_id]]  # (Passo 3 - remove do dicionário de saídas)
            del self._in_adjancencies[self._vertices[vertex_id]]  # (Passo 3 - remove do dicionário de entradas)
            del self._vertices[vertex_id]  # (Passo 3 - remove do dicionário de vértices)
            self._n -= 1


# Agora um teste a um grafo orientado:
g1 = DiGraph()
node_list = []  # lista auxiliar para guardar os vértices inseridos para construção das arestas
for i in range(0, 10):
    g1.insert_vertex(i)  # inserção dos 10 nós V = {0, 1, ..., 9} no grafo e na lista de nós
    node_list.append(i)

rnd.seed(170)  # para futura replicação deste grafo
for i in range(1, 41):  # criação de 20 arestas a partir dos vértices inseridos
    u, v = rnd.sample(node_list, k=2)  # gerar aleatoriamente uma aresta entre 2 vértices deste grafo
    x = rnd.randint(1, 10)  # com peso inteiro aleatório entre 0 e 10
    g1.insert_edge(node_list[u], node_list[v], x)  # inserção desta aresta no grafo

# print(g1)
print(repr(g1))

print('Ordem:', g1.order(),'Tamanho:', g1.size())

print(g1.has_vertex(node_list[2]))
print(g1.has_edge(node_list[1],node_list[3]))
print(g1.has_edge(node_list[3],node_list[1]))
print(g1.get_edge(node_list[1],node_list[3]))
print(g1.is_predecessor(node_list[1],node_list[3]))
print(g1.is_successor(node_list[3],node_list[1]))

print(g1.is_predecessor(node_list[3],node_list[1]))
print(g1.is_successor(node_list[1],node_list[3]))

# teste aos métodos iteradores do grafo
for e in g1.predecessors(node_list[5]):
    print(e, end=" ")
print()
for e in g1.successors(node_list[5]):
    print(e, end=" ")

g1.has_edge(node_list[0],node_list[6])
g1.remove_edge(node_list[0],node_list[6])
print('Ordem:', g1.order(),'Tamanho:', g1.size())
print(g1)

g1.remove_vertex(node_list[2])
print('Ordem:', g1.order(),'Tamanho:', g1.size())
print(repr(g1))

for e in g1.edges():
    print(e, end="; ")

# teste à remoção total (seria preferível fazer um método clear_graph...)
for v in node_list:
    g1.remove_vertex(v)
print(repr(g1))
print('Ordem:', g1.order(),'Tamanho:', g1.size())
print(g1.has_edge(node_list[3],node_list[0]))