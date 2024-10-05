import java.util.Comparator;

public class DecrescenteAnoNascimento implements Comparator<Aluno> {

    @Override
    public int compare(Aluno o1, Aluno o2) {
        if (o1.getAnoNascimento() == o2.getAnoNascimento()){
            if (o1.getAnoMatricula() == o2.getAnoMatricula()){
                return String.CASE_INSENSITIVE_ORDER.compare(o1.getNome(), o2.getNome());
            }
            else return (o2.getAnoMatricula() - o1.getAnoMatricula());
        }
        else return o2.getAnoNascimento() - o1.getAnoNascimento();
        }
    }
