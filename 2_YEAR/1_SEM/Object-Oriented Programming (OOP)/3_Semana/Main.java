import java.util.*;

public class Main {

    public static void main(String[] args) {
        //32
        Aluno a1 = new Aluno(933, "Pedro", 2022, 2000);
        Aluno a2 = new Aluno(234, "Bita", 2022, 2000);
        Aluno a3 = new Aluno(345, "Joao", 1991, 1982);

        ArrayList<Aluno> alunos = new ArrayList<>();
        alunos.add(a1);
        alunos.add(a2);
        alunos.add(a3);

        Comparator c = new CrescentePorNumero();
        Comparator c2 = new CrescentePorNome();
        Comparator c3 = new DecrescenteAnoNascimento();
        alunos.sort(c3);


        for (Aluno a : alunos) {
            System.out.println(a);
        }

        //33
        // Lista de palavras para filtrar
        List<String> palavras = Arrays.asList("barbara", "barba", "rodrigo", "tiago", "ba");

        // Criar critério para filtrar palavras que começam com "ap"
        CriterioDeSelecao<String> filtroPorPrefixo = new FilterWordsPrefix("ba");
        List<String> palavrasComPrefixo = Filter.filterWords(palavras, filtroPorPrefixo);
        System.out.println("Palavras que começam com 'ab': " + palavrasComPrefixo);

        CriterioDeSelecao<String> filtroPorTamanho = new WordsLessThanThree(3);
        List<String> palavrasPequenas = Filter.filterWords(palavras, filtroPorTamanho);
        System.out.println("Palavras com tamanho menor que 3: " + palavrasPequenas);

        //34
        List<String> arrayList = new ArrayList<>();
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");

        String resultArrayList = StringToText.separatedBy(arrayList, ", ");
        System.out.println("ArrayList: " + resultArrayList);

        List<String> linkedList = new LinkedList<>();
        linkedList.add("x");
        linkedList.add("y");
        linkedList.add("z");

        String resultLinkedList = StringToText.separatedBy(linkedList, ", ");
        System.out.println("LinkedList: " + resultLinkedList);

        System.out.println("");

        List<Operator> operators = new ArrayList<>();
        operators.add(new Addition());
        operators.add(new Power());
        String[] expression = { "(", "(", "2", "^", "8", ")", "+", "2", ")" };
        ExpressionEvaluator calc = new ExpressionEvaluator(operators);
        int result = calc.evaluate(expression); // 258

        System.out.println("Expression: ");
        for (int i = 0; i < expression.length; i++) {
            System.out.print(expression[i]);
        }
        System.out.println("");

        System.out.println("Result: " + result);
    }
}