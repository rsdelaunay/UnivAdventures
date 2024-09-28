package teste1;
public class WeightedQUPathCompressionUF {
	//Aluno n.� 122123 Rodrigo Delaunay
	//Atributos
	private int[] id; //n�s
	private int[] sz; //tamanho �rvores para posteriormente unir a menor � maior
	
	public WeightedQUPathCompressionUF(int N) {
		this.id = new int[N];
		this.sz = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1; //cada �rvore come�a com 1 de tamanho
		}
	}
	
	public int root(int p){ // root of p
		while (p != id[p]) { //while o p n�o for igual ao que estiver no array n�o � o n� pai
			id[p] = id[id[p]]; // Path compression - pelo caminho vamos tornar os n�s pais em n�s av�, at� todos apontarem para a root
			p = id[p];
		}
		return p; //retorna raiz assim que quebrar o while
	}
	
	public boolean connected(int p, int q) { //conectados?
		return root(p) == root(q); //se a root de ambos for igual, estao connectados
	}
	
	public void union(int p, int q) { //unir
		int r_p = root(p);//Guardar roots separadamente
		int r_q = root(q);
		if (r_p == r_q)
			return; //se j� tiverem a mesma root, n�o se faz nada.
		if (sz[r_p] < sz[r_q]){ //descobrir qual a tree maior e unir a menor � maior
			id[r_p] = r_q;
			sz[r_q] += sz[r_p];} //somar sempre os sizes de ambas as trees
		else {
			id[r_q] = r_p;
			sz[r_p] += sz[r_q];
		}
	}
	
	
    public static void main(String Args[]) {
    //Teste
    	WeightedQUPathCompressionUF t = new WeightedQUPathCompressionUF(100);
    	t.union(5, 10);
    	t.union(6, 11);
    	t.union(5, 15);
    	t.union(3, 10);
    	t.union(11, 3);
    	int root = t.root(6);
    	boolean conn = t.connected(11,5);
    	System.out.println(conn);
    	System.out.println(root);
    	
    }


}


