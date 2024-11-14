package aula_07;

import java.util.NoSuchElementException;

public class ST<Key extends Comparable <Key>, Value> { //Single Table. Key e Value como objetos para poderem ser objetos diferentes
							//Key é comparável com ela própria pq vamos comparar keys	

	private class Node { //Nó vai ter quatro coisas
		private Key key; //Chave
		private Value value; //Valor
		public Node left; //nó à esquerda
		public Node right; //nó à direita
		
		public Node(Key key, Value value) {
			this.key = key;
			this.value = value;
		}
	}
	
	private Node root; //guarda-se a root também
	
	public void put(Key key, Value value) {
		root = put(root, key, value); //caso nao haja root, cria um nó e fica esse o root. Caso root ja exista, vai continuar recursivamente e manter esse root, como se faz no left e no right em baixo.
	}
	
	private Node put(Node x, Key key, Value value) { //return no porque quando se descobre um nulo, cria-se um nó novo com esse valor.
		if (x == null)
			return new Node (key, value); //caso veja que o x é nulo, cria-se o nó com o valor da key que queremos put
		int cmp = key.compareTo(x.key); //comparar a key que pretendemos inserir com o nó onde estamos. Caso menor, esquerda. Caso maior, direita.
		if (cmp < 0) //caso menor, vai para a esuqerda
			x.left = put(x.left, key, value); //o no da esquerda fica o put desse no à esuqerda.
		else if (cmp > 0)
			x.right = put(x.right, key, value);
		else
			x.value = value; //caso a key que queiramos inserir ja esteja noutro nó, alteramos o valor desse nó. Sobrepõe. Importa notar - queremos associar a uma chave determinado valor. Caso já haja essa chave, alteramos o valor.
		return x; //retorna o próprio caso nao seja nulo. Devolve o que já lá está, não criando
	}
	
	public Value get(Key key) { //Quero procurar numa árvore. Começo na raiz. Caso seja menor que a raiz, esquerda. Caso maior, direita. Até chegar ao valor correto.
		//Caso não exista - chega ao final da árvore e diz out of bounds.
		//Método recursivo
		return get(root, key); //para procurar recursivamente e ser desde a root.
		
	}
	
	private Value get(Node x, Key key) { //funcao auxiliar para fazer recursivamente o get. Dizemos um nó e a key que queremos encontrar
		if (x == null) //para parar a recursividade. É quando fazemos o salto e vamos para um espaço nulo.
			return null;
		int cmp = key.compareTo(x.key); //comparar a chave que procuro com a chave onde estou
		if (cmp < 0) //caso seja menor, procura à esquerda recursivamente
			return get(x.left, key);
		else if (cmp > 0) //caso maior - procura à direita recursivamente
			return get(x.right, key);
		else //caso igual, é o value.
			return x.value;
	}	
		
	//Metodo iterativo do get
	//
	//Node x = root;
	//	while (x != null) { //enquanto nao chegar a um node nulo
	//	int cmp = key.compareTo(x.key); //compare da chave que eu quero com a chave onde eu estou
	//	if (cmp < 0){//caso o compare da key que procuro com a key onde estou seja < 0, tenho que andar para a esquerda
	//		x = x.left;
	//	}
	//	else if (cmp > 0) {
	//		x = x.right;
	//	}
	//	else //caso seja = 0, sao iguais!
	//		return x.value;
	//}
	//return null; //caso não encontre, retorna nulo.
	//
	
	public boolean isEmpty() {
		return root == null; //caso root seja nula
	}
	
	
	public Key min() {
		if (isEmpty())
			throw new NoSuchElementException("Empty symbol table!"); //nao existe elemento
		return min(root);//quero que procure o minimo desde a root
	}
	
	public Key min(Node x) { //vamos procurar desde a root. Minimo - procurar se há algo à esquerda. Se houver, continuar.
		//Andamos até o left não existir. Quer dizer que cheguei à ponta
		if (x.left == null)
			return x.key;
		return min(x.left);
	}
	
	public Key max() {
		return max(root);
	}
	
	public Key max(Node x) { //semelhante ao min
		if (isEmpty())
			throw new NoSuchElementException("Empty symbol table!"); //nao existe elemento
		if (x.right == null)
			return x.key;
		return max(x.right);
	}
	
	
	
}
