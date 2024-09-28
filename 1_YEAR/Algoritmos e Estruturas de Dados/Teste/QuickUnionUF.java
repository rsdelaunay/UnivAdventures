package teste1;
public class QuickUnionUF {
	//Aluno n.º122123 Rodrigo Delaunay
	//Atributos
	private int[] id;
	
	public QuickUnionUF(int N) {
		this.id = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
		}
	}
	
	public int root(int p){ // root of p
		while (p != id[p]) { //while o p não for igual ao que estiver no array não é o nó pai
			p = id[p];
		}
		return p; //retorna nó pai assim que quebrar o while
	}
	
	public boolean connected(int p, int q) { //conectados?
		return root(p) == root(q); //se a root de ambos for igual, estao connectados
	}
	
	public void union(int p, int q) { //unir
		int r_p = root(p);//Guardar roots separadamente
		int r_q = root(q);
		id[r_p] = r_q; //igual a primeira à segunda. ficam com as roots iguais.
	}

	
	public static void main(String Args[]) {
		//Testar
		QuickUnionUF t = new QuickUnionUF(10);
		t.union(0, 1);
		t.union(2, 4);
		t.union(4, 7);
		t.union(4,9);
		boolean teste = t.connected(0, 2);
		System.out.print(teste);
		System.out.print("\n");
		int teste_2 = t.root(2);
		System.out.print(teste_2);        
	   }
	
}
