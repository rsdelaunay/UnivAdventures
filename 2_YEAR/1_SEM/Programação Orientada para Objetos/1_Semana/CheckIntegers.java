
import static java.lang.System.out;

public class CheckIntegers {
    //Aluno n.º 122123
    public static void main(String[] args){
        //Contar inteiros
        int c_int = 0;
        int c_char = 0;
        int sum_int = 0;

        for (int i = 0; i < args.length; i++){
            if(Main.containsInteger(args[i]) == true){
                c_int++;
                sum_int += Main.StringtoInt(args[i]);
            }
            else c_char++;
        }
        out.println("Encontrei " + c_int + " inteiros");
        out.println("Encontrei " + c_char + " não inteiros");
        out.println("Somatorio dos inteiros: " + sum_int);

    }

}
