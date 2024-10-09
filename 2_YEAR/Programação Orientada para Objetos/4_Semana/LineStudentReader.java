import java.io.File;

public class LineStudentReader extends LineObjectReader<Aluno> {

    public LineStudentReader(File file) {
        super(file);
    }

    @Override
    public Aluno lineToObject(String line) {
        String[] parts = line.split(",");
        int numero = Integer.parseInt(parts[0].trim());
        String nome = parts[1].trim();
        return new Aluno(numero, nome);
    }
}