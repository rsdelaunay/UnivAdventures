package unionfind;

public class QuickFindUF {	
	
	private int[] id; //guardar ids elementos
	
	public QuickFindUF(int N) { //decidir número de elementos
		id = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i; //tem que se por o indice senao ficam todos igual a 0 e unidos
		}
	}
	//Union - Unir
	public void union (int p, int q) { //alterar id do p para id do q
		int idP = id[p];
		int idQ = id[q];
		for (int i = 0; i < id.length; i++) {
			if (id[i] == idP)
				id[i] = idQ; //basicamente altera todos os que tinham o idP para idQ
		}
	}
	
	//Connected - dizer se dois elementos estão ligados
	public boolean connected(int p, int q) {
		return id[p] == id[q]; //vai-se ver se têm o mesmo id
	}
	
	
	private static void doNUnionOperations(int N) {
		QuickFindUF uf = new QuickFindUF(N);
		for (int i = 0; i < N; i++) {
			int p = (int) (N*Math.random()); //numeros aleatorios
			int q = (int) (N*Math.random()); // numeros aleatorios
			uf.union(p, q);
		}
	}
		
		
		
	public static void main(String[] args) {
		//Double hypotesis
		// T(2N)/T(N) = T(N)/T(N/2)
		// Escolher N arbitariamente e duplicar posteriormente.
		// Medir tempos
		// Ir duplicando ate pc praticamente crashar e depois
		System.out.println("N\tT(N)\tRatio\tlg(Ratio)"); //colunas tabela nanaliselise
		
		int START = 5;
		// fazer a primeira para termos previous
		double start = System.currentTimeMillis(); //retorna tempo em determinado instante
		doNUnionOperations(START);
		double end = System.currentTimeMillis();
		double previousElapsedTime = end - start;
		
		for(int N = START*2; N <= 1000000; N *= 2) { //começamos com 10 e multiplica ate 1 milhao
			start = System.currentTimeMillis(); //retorna tempo em determinado instante
			doNUnionOperations(START);
			end = System.currentTimeMillis();
			double elapsedTime = (end-start)/1000.0; //converter em segundos
			//elapsedtime é T(N).
			
			double ratio = elapsedTime / previousElapsedTime; // T(N) / T(N/2)
			double lgRatio = Math.log(ratio) / Math.log(2); // logaritmo base 2 para calcular o b
			//Para ir vendo os resultados
			System.out.println(N+ "\t" + elapsedTime + "\t" + ratio + "\t" + lgRatio); // \t --> tab. se fosse \n seria new line
			
			previousElapsedTime = elapsedTime; //para guardar e utilizar como previous no próximo
			
			//comecou a dar arredondado a 2 --> b = 2
			//calcular a --> a = T(N) / N^2
			//ex do prof: N = 81920, T(N) = 10.045 e b = 2 logo
			// a = (10.045)/(81920)^2 = 1,4968 * 10^-9
			//
			//logo T(N) = 1.497*10^-9*N^2
			//ordem de crescimento - N^2.
			//dá para estimar. Substituir o N na função acima
		}
	}
// QuickFind, Weighted ...
//Usar double hypotesis!!
	

	
	
}
