import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Canvas canvas = new Canvas();

        //Circle c1 = new Circle(10,20,5);
        //Circle c2 = new Circle(10,20,5);
        //Rectangle r1 = new Rectangle(10, 20, 10,20);
        //Rectangle r2 = new Rectangle(5,10,20,30);

        //ArrayList<Figure> figuras = new ArrayList<>();
        //canvas.add(c1);
        //canvas.add(c2);
        //canvas.add(r1);
        //canvas.add(r2);

        //System.out.println(canvas);

        //System.out.println(canvas.greaterAreaFigure());

        //canvas.removeGreaterAreaFigure();

        //System.out.println(canvas.totalAreaOfFigures());

        //System.out.println(canvas);

        //File studentFile = new File("students.txt");
        //LineStudentReader studentReader = new LineStudentReader(studentFile);
        //try {
        //    List<Aluno> alunos = studentReader.read();
        //    for (Aluno aluno : alunos) {
        //        System.out.println(aluno);
        //    }
        //} catch (FileNotFoundException e) {
        //    e.printStackTrace();
        //}

        // Teste com LineCircleReader
        //File circleFile = new File("circles.txt");
        //LineCircleReader circleReader = new LineCircleReader(circleFile);
        //try {
        //   List<Circle> circles = circleReader.read();
        //    for (Circle circle : circles) {
        //        System.out.println(circle);
        //    }
        //} catch (FileNotFoundException e) {
        //    e.printStackTrace();
        //}

        Empresa empresa = new Empresa();

        // Adicionar empregados comuns
        empresa.adicionarEmpregado(new Empregado());

        // Adicionar gerentes de loja
        empresa.adicionarEmpregado(new GerenteDeLoja(true));  // Cumpriu os objetivos
        empresa.adicionarEmpregado(new GerenteDeLoja(false)); // Não cumpriu os objetivos

        // Adicionar diretores regionais
        empresa.adicionarEmpregado(new DiretorRegional(10000)); // Lucro de 10.000€
        empresa.adicionarEmpregado(new DiretorRegional(5000));  // Lucro de 5.000€

        // Mostrar total de salários
        System.out.println(empresa);

    }
}