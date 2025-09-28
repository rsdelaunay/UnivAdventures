import java.io.File;
import java.io.FileNotFoundException;

public class LineStudentReader extends LineObjectReader<Aluno>{


    public LineStudentReader (File file) throws FileNotFoundException {
        super(file);
    }

    @Override
    public Aluno lineToObject(String line) {
        String[] tokens = line.split(","); //divide string por cada ","
        int numero = Integer.parseInt(tokens[0].trim()); //aparece primeiro o numero
        String nome = tokens[1].trim(); // dps o nome
        return new Aluno (numero,nome); //criar aluno
    }

}
