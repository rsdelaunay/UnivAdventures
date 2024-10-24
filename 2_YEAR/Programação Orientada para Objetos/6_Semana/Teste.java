import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Teste {

    public static void main(String[] args) {

//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//
//        Iterable<Integer> nonEmpty = selectT(list, i -> i < 2 ); // {"a","b"}
//
//        for(Integer i : nonEmpty){
//            System.out.println(i);
//        }


        public static void main (String[]args){

            //	List<Pessoa> pessoas = new ArrayList<>();
            //	pessoas.add(new Pessoa("Ana", 60, 165));
            //	pessoas.add(new Pessoa("Tomas", 70, 165));
            //	pessoas.add(new Pessoa("Rita", 50, 160));
            //	pessoas.add(new Pessoa("Bruno", 80, 180));
            //	pessoas.add(new Pessoa("Joana", 55, 160));
            //	pessoas.add(new Pessoa("Guilherme", 75, 170));
            //	pessoas.add(new Pessoa("Vitor", 80, 185));
            //	pessoas.add(new Pessoa("Pedro", 70, 170));
            //	pessoas.add(new Pessoa("Vasco", 50, 175));


            // selecionar todas as pessoas com peso superior a 65 Kg
            //	List<Pessoa> selecao = select(pessoas, p -> p.getPeso() > 65);

            // Mostrar na consola
            //	selecao.forEach(p -> System.out.println(p));

            //Altura para 6.1. Podia ser pesos
            //	LinkedList<Integer> altura = new LinkedList<>();
            //	for (Pessoa p : pessoas) {
            //		altura.add(p.getAltura());
            //	}

            //Media (6.2.)
            //	double media = average(altura);
            //	System.out.println("MÃ©dia da altura: " + media);

            //6.3.
            // a)
//		Interval test = new Interval(5, 10); // [5, 10]
//		Interval nat = Interval.naturals(10); // [1, 10]
//		Interval indexes = Interval.arrayIndexes(new int[5]); // [0, 4]
//		Interval empty = Interval.empty(); // vazio
//
//		// Um Ãºnico output consolidado com todos os intervalos
//		System.out.println(
//				String.format("Intervalo test: %s\nIntervalo naturals: %s\nIntervalo indexes: %s\nIntervalo vazio: %s",
//						test, nat, indexes, empty)
//		);

            //b) - Implementacao Iterator<> no Interval. Basicamente hasNext e next() nessa
            //propria classe.
            //	Interval test = new Interval(5, 10); // [5, 10]

            //	System.out.println("Intervalo test: " + test);
            //	System.out.print("Iterando: ");

            //	Iterator<Integer> it = test.iterator();
            //	while (it.hasNext()) {
            //		System.out.print(it.next() + " ");
            //	}

            //c) - Implementacao Iterable - hasNext e next no iterator()
            //	Interval test = new Interval(5, 7); // [5, 6, 7]

            // Utilizando um ciclo for-each para iterar sobre o intervalo
            //	System.out.println("Iterando com for-each:");
            //	for (Integer i : test) {
            //		System.out.println(i);
            //	}

            //	System.out.println(average(test));

//            //6.7.
//            Week week = new Week();
//            for (WeekDay w : week) {
//                System.out.println(w);
//            }
//
//        }
//
//        static double average(Iterable<Integer> iterable){ //recebe um objeto iteravel
//            //sendo objeto iteravel, tem iterador. Vou busca-lo para depois fazer
//            //o hasNext desse iterador
//            Iterator<Integer> it = iterable.iterator();
//            int sum = 0;
//            int nElems = 0;
//
//            while(it.hasNext()) {
//                sum += it.next(); //vai buscar os elementos e somando
//                nElems++;
//            }
//            return (double) sum /nElems;
//        }
//
//
//    }
//
//    //6.4
//    static Iterable<String> select(Iterable<String> it, Predicate<String> pred) {
//        List<String> list = new ArrayList<>();
//        for(String s : it){ //Percorrer iterável, em cada String
//            if(pred.test(s)) //verificar se é de acordo com o predicado
//                list.add(s); //se for adicionar a lista
//        }
//        return list; //retornar lista com elementos que passem no test
//    }
//
//    //6.5 mm coisa q 6.4 mas geral
// //   static <T> Iterable<T> selectT(Iterable<T> iterable, Predicate<T> pred) { //...
//  //      List<T> list = new ArrayList<>();
//        for(T t : iterable){
//            if(pred.test(t))
//                list.add(t);
//        }
//        return list;
//    }


//
//
//        //Teste 6.6
//        // Vetor de inteiros
//        Integer[] intArray = {1, 2, 3, 4, 5};
//        ArrayIterator<Integer> intIterator = new ArrayIterator<>(intArray);
//        while (intIterator.hasNext()) {
//            System.out.println(intIterator.next());
//        }
//
//        // Vetor de strings
//        String[] stringArray = {"a", "b", "c"};
//        ArrayIterator<String> stringIterator = new ArrayIterator<>(stringArray);
//        while (stringIterator.hasNext()) {
//            System.out.println(stringIterator.next());
//        }
//            // Metodo flexivel para selecionar, onde a condicao para selecionar
//            // e' dada por um objeto que implementa Predicate
//            static List<Pessoa> select(List<Pessoa> pessoas, Predicate<Pessoa> pred) {
//
//                List<Pessoa> selecao = new ArrayList<>();
//                for (Pessoa p : pessoas)
//                    if (pred.test(p))
//                        selecao.add(p);
//
//                return selecao;
//
//            }
            
        }
    }
