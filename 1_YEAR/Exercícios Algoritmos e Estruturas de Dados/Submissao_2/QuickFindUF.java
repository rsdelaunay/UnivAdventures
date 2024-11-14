package submissao_2;

public class QuickFindUF {
	//Aluno n.º 122123 Rodrigo Delaunay
	//Atributos
	private int[] id; //matriz nós
		
    public QuickFindUF(int N) {//constructor (N is the number of points, 0 to N-1)
        this.id = new int[N]; //novo vetor com N fornecido
        for (int i = 0; i < N; i++)
            id[i] = i; //inicialização com nós = ao número dado
    }
    //Connected
    public boolean connected(int p, int q) {
        return id[p] == id[q]; //return true se tiverem o mesmo valor
    }
    
    //Uniao
    public void union(int p, int q) {
        int p_id = id[p];
        int q_id = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == p_id)
                id[i] = q_id;
        }
    }
    
    public static void main(String[] args) {
    	//Testes
    	QuickFindUF qf = new QuickFindUF(4);
    	qf.union(0, 3);
    	qf.union(2, 3);
    	boolean teste = qf.connected(0, 2);
    	System.out.println(teste);
    }
}
