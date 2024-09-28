import java.util.Iterator;

public class submissao_4 {
	//Aluno n.� 122123 Rodrigo Delaunay
	
		//A fila ter� de manter, no m�nimo, uma refer�ncia Node first para o primeiro n�. 
		//Pode ser adicionada uma refer�ncia Node last para o �ltimo n� caso considere relevante.

		//A representa��o dos n�s ligados deve seguir a seguinte estrutura. 
		//Pode ser adicionado um campo Node previous caso pretenda implementar uma lista 
		//duplamente ligada.
		
		public class Queue<T> implements Iterable<T> {
			private class Node { //privada - so se pode utilizar na classe Queue. Representa linked objects
				//Como � que sei que n� � o primeiro? Tenho que dizer ao java para ele saber.
				public T item; 
				public Node next; //pr�ximo node
			
				public Node(T item, Node next) {
					this.item = item;
					this.next = next;
				}
			}
		
			private Node first; //para criar o primeiro n� e saber-se qual � que �. vai ser um atributo da queue.
			private int size; //para guardar o tamanho e n�o termos um custo linear para verificar o tamanho da queue.	
			
			public Queue() {
				first = null; // quando come�o uma queue, o first n�o existe.
				//logica das queue --> � medida que vai aumentando, o first � como se fosse o top. Liga-se o �ltimo elemento
				//da queue a esse first.
				size = 0; //Tamanho da queue � 0
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
