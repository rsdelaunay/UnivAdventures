import static java.lang.System.out;
import java.util.Scanner;
import java.util.Stack;
// imports

public class Semana1 {

    private static String[] filter;

    public static void main(String[] args) { //função main para correr os exercicios com strings dadas.
        //ex1_1(args);
        //ex1_2(args);
        //ex1_3(args);
        //ex1_4(args);
        ex1_5(args);
    }

    public static void ex1_1(String[] args) {
        for (int i = 0; i < args.length; i++) { //i 0 a args.length, i++
            out.println(i + ": " + args[i]); // print do i e o respetivo argumento
        }
    }

    public static void ex1_2(String[] args) {
        int num =0;
        int sum = 0;
        for(String arg : args) { // para verificar se sao inteiros
            boolean isNumber = true; //boolean para guardar resultado do teste
            int number = 0; //numero
            for(int i = 0; i < arg.length(); i++) {
                int val = arg.charAt(i);
                if (val < 48 || val > 57) {
                    isNumber = false; //caso val retirado do char at i seja fora daqueles valores ASCII, nao e.
                    break; //break do for --> não sai do if mas sim do for.
                }
                number = number * 10 + (val - 48); //caso seja um numero, vai fazendo este calculo para converter
                //numeros ascii em numeros reais
            }
            if (isNumber) { // se for numero
                num++; //conta os numeros
                sum += number; //somatorio do number
            }
        }
        out.println("Encontrei " + num + " inteiros"); //numero de numeros ficou no int num
        out.println("Encontrei " + (args.length - num) + " termos não inteiros"); // nao numeros é o total da length - esse numero
        out.println("Somatório dos inteiros: " + sum); //somatorio sera isso
    }

    public static void ex1_3(String[] args) {
        filter = args;
        Scanner sc = new Scanner(System.in); //scanner com input do utilizador
        String[] words = sc.nextLine().split(" "); //lê a linha do utilizar com espaços

        for(int i = 0; i < words.length; i++)
            if(!isFilteredWord(words[i])) out.print(words[i] + " "); //caso nao deva ser filtrada, segue para print
        out.println("");
    }

    private static boolean isFilteredWord(String w) { //sub funcao para verificar se devera ser filtrada ou nao
        for(int i = 0; i < filter.length; i++) //percorre a string arg
            if(filter[i].equals(w)) return true; //caso seja realmente igual, tem que ser filtrada
        return false;
    }

    public static void ex1_4(String[] args){
        String fullLine = String.join("", args); //juntar as strings args eliminado o espaço
        int SIZE = fullLine.length(); //tamanho real da fulline sem os espacos
        getNextChar(fullLine, fullLine, SIZE); //funcao auxiliar
    }

    private static void getNextChar(String line, String fullLine, int size) {
        if(line.isEmpty()) { //caso a linha estiver empty, zero.
            return;}
        String characters = fullLine.replaceAll(String.valueOf(line.charAt(0)), ""); //remove todas as ocorrences do char na fulline
        String temp = line.replaceAll(String.valueOf(line.charAt(0)), ""); //igualmente
        double percent = (double) ((size - characters.length()) * 100) / size; //a percentagem desse caracter será o size total - o length do que ficou * 100 / size
        out.println(line.charAt(0) + " " + percent + "%");
        getNextChar(temp,fullLine,size); //metodo recursivo para fazer ate o final da string
    }

    public static void ex1_5(String[] args){
        Stack<Integer> intStack = new Stack<>();  //stacks para juntar os inteiros
        Stack<Character> intOps = new Stack<>(); //stack para juntar os caracteres

        for(String arg: args){ //para ler as strings em args e guardar em arg
            if(arg.equals("(")) //se arg for um parentesis
                continue; //continua
            if(arg.equals("+") || arg.equals("-") || arg.equals("x") || arg.equals("/")){
                intOps.push(arg.charAt(0)); //se for um sinal, guardar esse sinal no stack
                continue;
            }

            if(arg.equals(")")) //se houve fecho dos parentesis
            {
                int a = intStack.pop(); //pop dos inteiros
                int b = intStack.pop();
                char op = intOps.pop(); //pop da operacao
                intStack.push(calculate(a, b, op)); //calcula e guarda no int stack o valor
                continue; //continua
            }
            intStack.push(Integer.parseInt(arg));
        }
        out.println(intStack.pop()); //manda pop do intstack
    }

    private static int calculate(int a, int b, char op){
        switch(op){ //consoante a opcao
            case '+': //caso +
                return a + b;
            case '-':
                return a - b;
            case 'x':
                return a * b;
            case '/':
                return a / b;
        }
        return 0;
    }
    public static boolean isStringNumber(String arg){
        boolean isNumber = true;
        int number = 0;
        for(int i = 0; i < arg.length(); i++) {
            int val = arg.charAt(i);
            if (val < 48 || val > 57) {
                isNumber = false;
                break;
            }
            number = number * 10 + (val - 48);
        }
        return isNumber;
    }
}
