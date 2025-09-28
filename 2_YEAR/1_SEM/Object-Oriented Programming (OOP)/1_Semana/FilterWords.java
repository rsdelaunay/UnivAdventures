import java.util.Scanner;

public class FilterWords {

    public static void main(String[] args) {
        //Recebe input
        System.out.print("frase: ");
        Scanner input = new Scanner(System.in);
        String frase = input.nextLine();
        //Le frase
        Scanner scanner = new Scanner(frase);
        //Cria Array de Strings

        while (scanner.hasNext()) {
            String token = scanner.next();
            for (int i = 0; i < args.length; i++){
                if (token.equals(args[i])){
                    frase = frase.replace(token, ""); //trocar caso seja igual
                }
            }
        }
        scanner.close();

        System.out.println(frase);
    }

}