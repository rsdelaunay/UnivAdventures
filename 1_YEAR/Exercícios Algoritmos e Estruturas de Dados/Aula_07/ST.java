package aula_07;

import java.util.NoSuchElementException;

public class ST<Key extends Comparable <Key>, Value> { //Single Table. Key e Value como objetos para poderem ser objetos diferentes
							//Key � compar�vel com ela pr�pria pq vamos comparar keys	

	private class Node { //N� vai ter quatro coisas
		private Key key; //Chave
		private Value value; //Valor
		public Node left; //n� � esquerda
		public Node right; //n� � direita
		
		public Node(Key key, Value value) {
			this.key = key;
			this.value = value;
		}
	}
	
	private Node root; //guarda-se a root tamb�m
	
	public void put(Key key, Value value) {
		root = put(root, key, value); //caso nao haja root, cria um n� e fica esse o root. Caso root ja exista, vai continuar recursivamente e manter esse root, como se faz no left e no right em baixo.
	}
	
	private Node put(Node x, Key key, Value value) { //return no porque quando se descobre um nulo, cria-se um n� novo com esse valor.
		if (x == null)
			return new Node (key, value); //caso veja que o x � nulo, cria-se o n� com o valor da key que queremos put
		int cmp = key.compareTo(x.key); //comparar a key que pretendemos inserir com o n� onde estamos. Caso menor, esquerda. Caso maior, direita.
		if (cmp < 0) //caso menor, vai para a esuqerda
			x.left = put(x.left, key, value); //o no da esquerda fica o put desse no � esuqerda.
		else if (cmp > 0)
			x.right = put(x.right, key, value);
		else
			x.value = value; //caso a key que queiramos inserir ja esteja noutro n�, alteramos o valor desse n�. Sobrep�e. Importa notar - queremos associar a uma chave determinado valor. Caso j� haja essa chave, alteramos o valor.
		return x; //retorna o pr�prio caso nao seja nulo. Devolve o que j� l� est�, n�o criando
	}
	
	public Value get(Key key) { //Quero procurar numa �rvore. Come�o na raiz. Caso seja menor que a raiz, esquerda. Caso maior, direita. At� chegar ao valor correto.
		//Caso n�o exista - chega ao final da �rvore e diz out of bounds.
		//M�todo recursivo
		return get(root, key); //para procurar recursivamente e ser desde a root.
		
	}
	
	private Value get(Node x, Key key) { //funcao auxiliar para fazer recursivamente o get. Dizemos um n� e a key que queremos encontrar
		if (x == null) //para parar a recursividade. � quando fazemos o salto e vamos para um espa�o nulo.
			return null;
		int cmp = key.compareTo(x.key); //comparar a chave que procuro com a chave onde estou
		if (cmp < 0) //caso seja menor, procura � esquerda recursivamente
			return get(x.left, key);
		else if (cmp > 0) //caso maior - procura � direita recursivamente
			return get(x.right, key);
		else //caso igual, � o value.
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
	//return null; //caso n�o encontre, retorna nulo.
	//
	
	public boolean isEmpty() {
		return root == null; //caso root seja nula
	}
	
	
	public Key min() {
		if (isEmpty())
			throw new NoSuchElementException("Empty symbol table!"); //nao existe elemento
		return min(root);//quero que procure o minimo desde a root
	}
	
	public Key min(Node x) { //vamos procurar desde a root. Minimo - procurar se h� algo � esquerda. Se houver, continuar.
		//Andamos at� o left n�o existir. Quer dizer que cheguei � ponta
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
