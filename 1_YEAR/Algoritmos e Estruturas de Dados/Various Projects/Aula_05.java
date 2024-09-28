
public class Aula_05 {
	//Bag - pode-se ir buscar em qualquer posição
	//Deque - double ended que (podemos adicionar e remover no inicio e fim.
	//List	
	//Piority queue - fila com alguns elementos com prioridade (prioridades fila supermercado
	
	//Vamos focar-nos na lista
	//Podemos fazer com um array ou com nós (linked list)
	
	//C array
	//remove - retirar numa determinada posição em relação ao first
	//depois, caso haja um nulo entre posições com informação - descobrir que lado tem menos
	//número de elementos e fazer shift para left ou para shift right
	
	//Agora com nós!
	//first, last iguais 
	//next null no inicio
	//
	//depois de uma lista muito cheia
	// caso retire no meio
	// tem que se iterar até se ver onde se pode ligar o nó anterior ao nó removido ao next do nó removido

	// procurar na lista --> iterar a lista inteira para ver se determinado valor está presente na lista
	
	//removeFirst(T item) --> remove do primeiro item que apareceu
	//removeAll(T item) --> removo e continua-se no next
	//removeLast(T item) --> além de existir um next, existe um previous. Lista duplamente ligada.
		// em vez de fazer .next como o outro, fazia-se .previous .
	
	
}
