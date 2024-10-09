import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class LineObjectReader<T> {
    private File file;

    public LineObjectReader(File file) {
        this.file = file;
    }

    public List<T> read() throws FileNotFoundException {
        List<T> objects = new ArrayList<>();
        Scanner sc = new Scanner(file);
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            objects.add(lineToObject(line));
        }
        sc.close();
        return objects;
    }

    public abstract T lineToObject(String line);
}
