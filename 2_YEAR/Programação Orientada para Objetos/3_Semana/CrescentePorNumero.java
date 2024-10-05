import java.util.Comparator;

public class CrescentePorNumero implements Comparator<Aluno> {

    @Override
    public int compare(Aluno o1, Aluno o2) {
        return o1.getNumero() - o2.getNumero(); //cumpre criterio para a array list
        // < 0, = 0 ou > 0
    }

}
