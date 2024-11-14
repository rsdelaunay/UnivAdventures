import java.util.Iterator;

public class submissao_4 {
	//Aluno n.º 122123 Rodrigo Delaunay
	
		//A fila terá de manter, no mínimo, uma referência Node first para o primeiro nó. 
		//Pode ser adicionada uma referência Node last para o último nó caso considere relevante.

		//A representação dos nós ligados deve seguir a seguinte estrutura. 
		//Pode ser adicionado um campo Node previous caso pretenda implementar uma lista 
		//duplamente ligada.
		
		public class Queue<T> implements Iterable<T> {
			private class Node { //privada - so se pode utilizar na classe Queue. Representa linked objects
				//Como é que sei que nó é o primeiro? Tenho que dizer ao java para ele saber.
				public T item; 
				public Node next; //próximo node
			
				public Node(T item, Node next) {
					this.item = item;
					this.next = next;
				}
			}
		
			private Node first; //para criar o primeiro nó e saber-se qual é que é. vai ser um atributo da queue.
			private int size; //para guardar o tamanho e não termos um custo linear para verificar o tamanho da queue.	
			
			public Queue() {
				first = null; // quando começo uma queue, o first não existe.
				//logica das queue --> à medida que vai aumentando, o first é como se fosse o top. Liga-se o último elemento
				//da queue a esse first.
				size = 0; //Tamanho da queue é 0
			}

		}
		
		public Queue() {} //constructor
		
		public void enqueue(T item) {} //add item to the queue
		
		public T dequeue() {} //remove and return the least recently added item
		
		public boolean isEmpty() {} //is the queue empty?
		
		public int size() {} //number of items in the queue
		
		public void shift() {} //move the last element to the start of the queue
		
		public Iterator<T> iterator(){} //return the iterator for this queue
		
		
		
}
