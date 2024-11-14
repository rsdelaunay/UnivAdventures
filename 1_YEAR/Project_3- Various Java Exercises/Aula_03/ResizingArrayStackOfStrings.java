package Aula_03;

public class ResizingArrayStackOfStrings {
	//Começando com uma pilha (stack) sem resizing
	//
	//atributos
	private String[] stack; //array de strings
	private int top; //topo do stack. Apontador
	
	
	public ResizingArrayStackOfStrings(int capacity) {
		stack = new String[capacity];
		top = 0; //inicia no 0.
	}
	
	public void push(String item) { //para inserir
		if (top == stack.length)
			throw new IllegalStateException("Stack overflow!"); // stack esta cheio. nao da para escrever mais
		stack[top] = item; //insere-se no topo do stack
		top++; //incrementa um
	}
	
	public String pop(){
		if (top == 0)
			throw new IllegalStateException("Stack overflow!");
		String item = stack[top-1]; //guarda o que esta na ultima posição
		stack[top-1]=null; // tem que se eliminar o que la está.
		top--;
		return item;
	}
	//NOTA : stack[--top] --> tira um ao top e depois é que escreve no array. se fosse stack[top--] --> escrevia primeiro e depois é que tirava um valor 
	
	public boolean isEmpty(){
		return top == 0; //se o topo for 0, quer dizer que está vazio
	}
	
	public int size(){
		return top; //tamanho do stack
	}

}
