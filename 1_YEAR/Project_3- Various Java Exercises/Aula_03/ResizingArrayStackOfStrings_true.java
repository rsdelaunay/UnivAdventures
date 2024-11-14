package Aula_03;

public class ResizingArrayStackOfStrings_true {
	
		//Começando com uma pilha (stack) sem resizing
		//
		//atributos
		private String[] stack; //array de strings
		private int top; //topo do stack. Apontador
		
		
		public ResizingArrayStackOfStrings_true() {
			stack = new String[1];
			top = 0; //inicia no 0.
		}
		
		public void push(String item) { //para inserir
			if (top == stack.length)
				resize(2 * stack.length);
			stack[top] = item; //insere-se no topo do stack
			top++; //incrementa um
		}
		
		
		public String pop(){
			if (top == 0)
				throw new IllegalStateException("Stack underflow");
			String item = stack[top-1]; //guarda o que esta na ultima posição
			stack[top-1]=null; // tem que se eliminar o que la está.
			top--;
			
			//If is 1/4 full, resize to half capacity
			if (top == stack.length / 4)
				resize(stack.length/2);
			return item
		}
		//NOTA : stack[--top] --> tira um ao top e depois é que escreve no array. se fosse stack[top--] --> escrevia primeiro e depois é que tirava um valor 
		
		private void resize(int newSize) {
			String[] newArray = new String[newSize]; //novo array com novo size, seja metade ou o dobro
			for (int i = 0; i < top; i++)
				newArray[i] = stack[i]; 
			this.stack = newArray; //stack inicial vai ser igual a este novo array
		}
		
		public boolean isEmpty(){
			return top == 0; //se o topo for 0, quer dizer que está vazio
		}
		
		public int size(){
			return top; //tamanho do stack
		}
		
		public static void main(String[] args) {
			ResizingArrayStackOfStrings_true stack = new ResizingArrayStackOfStrings_true();
			stack.push("LEI");
			stack.push("PL");
			stack.push("são");
			stack.push("a");
			stack.push("melhor");
			stack.push("turma");
			stack.push(":)");
			System.out.println(stack.size());
			
			
		}

		
}
