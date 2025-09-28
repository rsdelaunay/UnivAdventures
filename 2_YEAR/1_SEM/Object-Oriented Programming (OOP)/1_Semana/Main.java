//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import static java.lang.System.out;

//Aluno n.º 122123 Rodrigo Delaunay

public class Main {
    //1.1.
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            out.println(i + ": " + args[i]);
        }
        //1.2.
        out.println(containsInteger(args[0]));

        //1.3.
        out.print(StringtoInt(args[0]));


    }
    //1.2.
    public static boolean containsInteger(String s){
        for (int i = 0; i < s.length(); i++){
            if(s.charAt(i) < 48 || s.charAt(i) > 57){
                return false;
            }
        }
        return true;
    }

    //1.2. continuation
    public static int StringtoInt(String s){
            int num =0;
            for(int i =0; i<s.length();i++)
            {
                    num = num * 10 + ((int)s.charAt(i)-48);
            }
            return num;
        }

}