
public class Aula_05 {
	//Bag - pode-se ir buscar em qualquer posi��o
	//Deque - double ended que (podemos adicionar e remover no inicio e fim.
	//List	
	//Piority queue - fila com alguns elementos com prioridade (prioridades fila supermercado
	
	//Vamos focar-nos na lista
	//Podemos fazer com um array ou com n�s (linked list)
	
	//C array
	//remove - retirar numa determinada posi��o em rela��o ao first
	//depois, caso haja um nulo entre posi��es com informa��o - descobrir que lado tem menos
	//n�mero de elementos e fazer shift para left ou para shift right
	
	//Agora com n�s!
	//first, last iguais 
	//next null no inicio
	//
	//depois de uma lista muito cheia
	// caso retire no meio
	// tem que se iterar at� se ver onde se pode ligar o n� anterior ao n� removido ao next do n� removido

	// procurar na lista --> iterar a lista inteira para ver se determinado valor est� presente na lista
	
	//removeFirst(T item) --> remove do primeiro item que apareceu
	//removeAll(T item) --> removo e continua-se no next
	//removeLast(T item) --> al�m de existir um next, existe um previous. Lista duplamente ligada.
		// em vez de fazer .next como o outro, fazia-se .previous .
	
	
}
