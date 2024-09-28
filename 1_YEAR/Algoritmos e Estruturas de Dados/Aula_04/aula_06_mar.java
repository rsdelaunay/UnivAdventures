import java.util.Iterator;

public class aula_06_mar {
	//1. linked objects - em vez de termos custos com inser��o e remo��o 
	
	//2. classe pilha geral para termos stacks de ints, strings, etc...
	//podemos dizer isso ao java.
	// tudo o que � strings - p�r object. Todas as classes do java fazem parte do object. S�o tudo objetos
	
	//assim conseguimos ter um stack com varios tipos. TODOS os tipos. boolean, etc etc
	
	//3. por�m, n�o conv�m. Porque pode aceitar porcaria
	//Stacks gen�ricos (ver abaixo)
	
	public class Stack<Tipo> implements Iterable<Tipo> { //definimos um tipo (objeto) ao longo da classe. Depois, quando a classe for chamada,
		//tem que se indicar o tipo
		
		private class Node { //privada - so se pode utilizar na classe stack. Representa linked objects
			//Como � que sai que n� � o primeiro? Tenho que dizer ao java para ele saber.
			public Tipo item; 
			public Node next; //pr�ximo node
		
			public Node(Tipo item, Node next) {
				this.item = item;
				this.next = next;
			}
		}
		
		private Node first; //para criar o primeiro n� e saber-se qual � que �. vai ser um atributo do stack.
		private int size; //para guardar o tamanho e n�o termos um custo linear para verificar o tamanho do stack.
		
		public Stack() {
			first = null; // quando come�o uma pilha, o first n�o existe.
			//logica das pilhas --> � medida que vamos metendo, o first � como se fosse o top. Liga-se o �ltimo elemento
			//do stack a esse first.
			size = 0; //Tamanho da pilha 0
		}
		
		public void push(Tipo item) { //temos que crescer para o first. New node na dire��o do first.
			first = new Node(item, first); //novo n� ligado a outro n� que t� ligado ao first. Basta ligar o first ao novo n� que criamos
			size++;
		}
		
		public Tipo pop() {
			//guarda-se o que vai ser eliminado
			if (isEmpty())
				throw new IllegalStateException("Stack underflow! :(");
			Tipo item = first.item;
			//first aponta para o next element
			first = first.next;
			//size fica menos
			size--;
			return item;
		}
			
		
		public boolean isEmpty() {
			return (first == null); // se o first for nulo, quer dizer que est� vazio
		}
		
		public int size() {
			return size; //crescimento constante. bem melhor que linear
		}
		
	
	//3
	public Iterator<Tipo> iterator() { //esta fun��o vai cumprir todas as regras necessarias para a classe ser iter�vel
		return new StackIterator(); // return do iterador que eu crio. facil.
	}
	//3.Definimos onde come�a, onde acaba, para onde anda e em que saltos faz.
	private class StackIterator implements Iterator<Tipo> {
		
		private Node current;
		
		public StackIterator() {
			current = first; //quando se come�a o iterador, come�a-se no topo. First.
		}
		
		
		//Estas funcoes tem que ser feitas sempre!
		//em vez de dizer quando para - dizemos - eu ando at� ali
		public boolean hasNext() { //basicamente, ha algum elemento que eu nao tenha obtido? O java obriga este nome,
			//mas o prof preferia stillExists()
			//posicao deixa de existir quando o que tem la dentro � nulo
			return current != null; 
		}
		
		public Tipo next() { 
			//Vamos assumir que estamos numa posi��o v�lida . O java trata disso, pelo que n�o precisamos de if hasnext. Agora precisamos de andar em frente
			Tipo item = current.item;
			
			current = current.next; //o current torna-se o next
		}
		
	
	}
	
	
	public static void main(String[] args) {
		Stack<String> stack1 = new Stack<String>(5);
		
		Stack<Integer> stack2 = new Stack<Integer>(5); //int tem que ser Int, double tem que ser Double, Boolean, etc
		//isto porque sao tipos primitivos. O java nao aceita escrever dessa forma.
		
		
		//3. Iterador 
		//Quero print de um determinado ficheiro --> ciclo for. facil.
		//Agora de um stack?
		//Faz-se deqeue e println ? Mandam-se os dados para o lixo.
		//Nao.
	
		//Vamos ter que explicar ao Java como fazer itera��o desta classe que criei.
		for(String element : stack1) { //por cada elemento no stack, faz-se print.
			System.out.println(element);
		}
		//NOTA - deu stack em LIFO.
		
		//Temos que dizer ao java que a classe que fiz � iter�vel. implements Iterable<Tipo>
	
		
		//Depois do iterador feito, o que est� em cima faz o mesmo que este em baixo.
		Iterator<String> iterator = stack.iterator();
		while (iterator.hasNext())
			System.out.println(iterator.next());
	
		//Magia dos gen�ricos - basta alterar o tipo de stack criado que posteriormente atua a classe gen�rica nesse tipo.
	}
		
