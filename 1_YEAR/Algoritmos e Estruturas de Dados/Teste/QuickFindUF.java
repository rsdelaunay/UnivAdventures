package teste1;

public class QuickFindUF {
    
    private int[] id;
    
    // Linear = Θ(N)
    public QuickFindUF(int N) {
        this.id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }
    
    // Linear = Θ(N)
    public void union(int p, int q) {
        int idP = id[p];
        int idQ = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == idP)
                id[i] = idQ;
        }
    }
    
    // Constant = Θ(1)
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }
    
    // Auxiliary operation - do N union() operations
    private static void doNUnionOperations(int N) {
        QuickFindUF uf = new QuickFindUF(N);
        for (int i = 0; i < N; i++) {
            int p = (int) (N * Math.random());
            int q = (int) (N * Math.random());
            uf.union(p, q);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("N\tT(N)\tlg(Ratio)");
        
        int START = 100;
        
        double start = System.currentTimeMillis();
        doNUnionOperations(START);
        double end = System.currentTimeMillis();
        double previousElapsedTime = (end - start) / 1000.0;
        
        for (int N = 2 * START; N <= 1000000; N = N * 2) {
            start = System.currentTimeMillis();
            doNUnionOperations(N);
            end = System.currentTimeMillis();
            double elapsedTime = (end - start) / 1000.0; // T(N)
            
            double ratio = elapsedTime / previousElapsedTime;
            
            double lgRatio = Math.log(ratio) / Math.log(2);
            
            System.out.println(N + "\t" + elapsedTime + "\t" + lgRatio);
            
            previousElapsedTime = elapsedTime;
        }
    }
}