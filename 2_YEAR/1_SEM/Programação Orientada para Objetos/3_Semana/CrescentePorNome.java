import java.util.Comparator;

public class CrescentePorNome implements Comparator<Aluno> {

    @Override
    public int compare(Aluno o1, Aluno o2) {
        return String.CASE_INSENSITIVE_ORDER.compare(o1.getNome(), o2.getNome());
    }
}
