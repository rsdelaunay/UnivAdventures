import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public  abstract class LineObjectReader<T> {

    private File file;

    public LineObjectReader(File file) throws FileNotFoundException { // ...
        this.file = file;
    }

    public List<T> read() throws FileNotFoundException {
        List<T> objects = new ArrayList<T>(); //criar lista objetos vazia
        Scanner sc = new Scanner(file); //abrir scanner
        while(sc.hasNextLine()) { //enquanto tiver proxima linha
            String objeto = sc.nextLine(); // definir linha como string para objeto
            objects.add(lineToObject(objeto)); //alimentar o lineToObject com a String
        }
        sc.close(); //fechar scanner
        return objects; //returnar lista objetos
    }
    public abstract T lineToObject(String line); // metodo para implementar nas subclasses
}
