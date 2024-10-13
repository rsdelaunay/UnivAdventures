import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {


///*        Circle c1 = new Circle(0,0,2);
//        Circle c2 = new Circle(1,1,5);
//        Rectangle r1 = new Rectangle(0,0,5,10);
//        Rectangle r2 = new Rectangle(0,0,15,10);
//
//        Canvas canvas = new Canvas();
//
//        canvas.add(c1);
//        canvas.add(c2);
//        canvas.add(r1);
//        canvas.add(r2);
//
//        System.out.println(canvas.greaterAreaFigure());
//
//        canvas.removeGreaterArea();
//
//        System.out.println(canvas.greaterAreaFigure());*/
//
//        //Teste estudante
//        File studentFile = new File("TesteAula4");
//        LineStudentReader studentReader = new LineStudentReader(studentFile);
//        List<Aluno> alunos = studentReader.read();
//        for (Aluno aluno : alunos) {
//                System.out.println(aluno);
//        }
//
//
//        //Teste com LineCircleReader
//        File circleFile = new File("Circles");
//        LineCircleReader circleReader = new LineCircleReader(circleFile);
//        try {
//           List<Circle> circles = circleReader.read();
//            for (Circle circle : circles) {
//                System.out.println(circle);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        Empresa empresa = new Empresa();
//
//        // Adicionar empregados comuns
//        empresa.adicionarEmpregado(new Empregados("Josh"));
//
//        // Adicionar gerentes de loja
//        empresa.adicionarEmpregado(new GerenteDeLoja(true,"António"));  // Cumpriu os objetivos
//        empresa.adicionarEmpregado(new GerenteDeLoja(false,"Jose")); // Não cumpriu os objetivos
//
//        // Adicionar diretores regionais
//        empresa.adicionarEmpregado(new DiretorRegional("Joana",10000)); // Lucro de 10.000€
//        empresa.adicionarEmpregado(new DiretorRegional("Tiago",5000));  // Lucro de 5.000€
//
//        // Mostrar total de salários
//        System.out.println(empresa);

        List<Cliente> clientes= new ArrayList<>();

        Cliente c1 = new Cliente("Antonio",1,30);
        Cliente c2 = new Cliente("Jose",2,550);
        Cliente c3 = new Cliente("Maria",3,100);
        Cliente c4 = new Cliente("Marta",4,120);


        clientes.add(c1);
        clientes.add(c2);
        clientes.add(c3);
        clientes.add(c4);


        List<ClienteVIP> clientesVip = ClienteVIP.promoverParaVIP(clientes);
        System.out.println(clientesVip);

    }




}