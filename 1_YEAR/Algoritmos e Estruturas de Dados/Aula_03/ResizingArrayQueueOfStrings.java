package Aula_03;

public class ResizingArrayQueueOfStrings {
	//Atributos
	private String[] queue;
	private int first;
	private int last;
	
	public ResizingArrayQueueOfStrings() {
		queue = new String[1];
		first = -1; //para indicar que está vazia
		last = -1; 
	}
	
	 private void resize(int capacity) { //método para escrever na nova queue via resize
      String[] newQueue = new String[capacity];
	  int size = size();
	  for (int i = 0; i < size; i++) {
	       newQueue[i] = queue[(first + i) % queue.length]; //ciclo for para escrever na nova queue os elementos já existentes
	  }
	  queue = newQueue; //definir queue atributo com essa nova queue
	  first = 0; //first = 0 novamente
	  last = size - 1; //ultimo elemento será o tamanho -1
	  }
	
	private int next(int pos){ //método auxiliar para efetuar operação next na posição
		if (pos == queue.length-1)
			return 0;
		return pos +1; //posição +1 para o next
	}
	
	public void enqueue(String item) {
		if (isEmpty())
			first = 0;
		if (next(last) == first)// se do ultimo andar para a frente, bato no primeiro
			resize(2*queue.length); //dobro pois está cheio. 
		last = next(last); //last será o próximo
		queue[last] = item;
		}
	
	public String dequeue() {
		if (isEmpty())
			throw new IllegalStateException("Queue underflow!"); //fila está vazia e nao dá para retirar ninguem
		while (queue[first] == null) {
			first +=1;
		}
		String item = queue[first]; //guardar quem chegou primeiro
		queue[first] = null; // para evitar o loitering
		if (first == last) { //se tivesse só um, mete-se -1 como inicialmente pois fica vazio
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
			return last - first + 1; //caso last seja maior que o first, fica a diferença entre eles +1. Se forem iguais - subtraem-se igualmente e fica 0 +1 = 1 elemento na queue
		else
			return (queue.length - first+1) + last; //conta-se do first até ao final
	}
	
	public void shift() {
		if (isEmpty()) { 
			throw new IllegalStateException("Fila vazia!"); //se estiver vazio, não há nada para fazer shift
        }
	    // Encontra o próximo índice que contém um elemento na fila
	    int index = first;
	    while (queue[index] == null) {
	        index = next(index);
	    }
	    //Guarda elemento inicial
	    String inicial = queue[index];
	    // Move o último elemento para o índice encontrado
	    queue[index] = queue[last];
	    queue[last] = inicial; // Limpa a posição do último elemento na fila
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


