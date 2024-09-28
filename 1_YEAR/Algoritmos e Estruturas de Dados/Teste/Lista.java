package teste1;

import teste1.List.Node;

public class Lista<T> implements Iterable<T>{
	private class Node() {
		public T item;
		public Node next;
		public Node previous;
		
        public Node(T item, Node next, Node previous) { //construtor do node
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }
	
	private Node first,last;
	private int size;
	
	public List() {
		first = null;
		last = null;
		size = 0;
	}
	
	
	

}
