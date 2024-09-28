package Aula_03;

public class ResizingArrayQueueOfStrings {
	//Atributos
	private String[] queue;
	private int first;
	private int last;
	
	public ResizingArrayQueueOfStrings() {
		queue = new String[1];
		first = -1; //para indicar que est� vazia
		last = -1; 
	}
	
	 private void resize(int capacity) { //m�todo para escrever na nova queue via resize
      String[] newQueue = new String[capacity];
	  int size = size();
	  for (int i = 0; i < size; i++) {
	       newQueue[i] = queue[(first + i) % queue.length]; //ciclo for para escrever na nova queue os elementos j� existentes
	  }
	  queue = newQueue; //definir queue atributo com essa nova queue
	  first = 0; //first = 0 novamente
	  last = size - 1; //ultimo elemento ser� o tamanho -1
	  }
	
	private int next(int pos){ //m�todo auxiliar para efetuar opera��o next na posi��o
		if (pos == queue.length-1)
			return 0;
		return pos +1; //posi��o +1 para o next
	}
	
	public void enqueue(String item) {
		if (isEmpty())
			first = 0;
		if (next(last) == first)// se do ultimo andar para a frente, bato no primeiro
			resize(2*queue.length); //dobro pois est� cheio. 
		last = next(last); //last ser� o pr�ximo
		queue[last] = item;
		}
	
	public String dequeue() {
		if (isEmpty())
			throw new IllegalStateException("Queue underflow!"); //fila est� vazia e nao d� para retirar ninguem
		while (queue[first] == null) {
			first +=1;
		}
		String item = queue[first]; //guardar quem chegou primeiro
		queue[first] = null; // para evitar o loitering
		if (first == last) { //se tivesse s� um, mete-se -1 como inicialmente pois fica vazio
			first = -1;
			last = -1;
		}
		else first = next(first);
        if (size() <= queue.length / 4 && size() > 0) { //se o size for menor que 1/4, dividir a length por metade
            resize(queue.length / 2);
        }
        return item; //elemento retirado
		}
	
	public boolean isEmpty() {
		return first == -1; //queue vazia
	}
	
	public int size(){
		if (isEmpty()) //se estiver vazio, fica 0
			return 0;
		if (last >= first)
			return last - first + 1; //caso last seja maior que o first, fica a diferen�a entre eles +1. Se forem iguais - subtraem-se igualmente e fica 0 +1 = 1 elemento na queue
		else
			return (queue.length - first+1) + last; //conta-se do first at� ao final
	}
	
	public void shift() {
		if (isEmpty()) { 
			throw new IllegalStateException("Fila vazia!"); //se estiver vazio, n�o h� nada para fazer shift
        }
	    // Encontra o pr�ximo �ndice que cont�m um elemento na fila
	    int index = first;
	    while (queue[index] == null) {
	        index = next(index);
	    }
	    //Guarda elemento inicial
	    String inicial = queue[index];
	    // Move o �ltimo elemento para o �ndice encontrado
	    queue[index] = queue[last];
	    queue[last] = inicial; // Limpa a posi��o do �ltimo elemento na fila
		}
	
	public static void main(String[] args) {
		ResizingArrayQueueOfStrings teste = new ResizingArrayQueueOfStrings();
		teste.enqueue("1");
		teste.enqueue("2");
		teste.enqueue("3");
		teste.enqueue("4");
		teste.shift();
		teste.enqueue("5");
		teste.dequeue();		
		for (int i = 0; i < teste.queue.length; i++) {
			System.out.println(teste.queue[i]);
		}
	}
	
}


