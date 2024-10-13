import java.io.File;
import java.io.FileNotFoundException;

public class LineCircleReader extends LineObjectReader<Circle>{

    private File file;

    public LineCircleReader (File file) throws FileNotFoundException {
         super(file);
    }

    @Override
    public Circle lineToObject(String line) {
        String[] tokens = line.split(",");
        int x = Integer.parseInt(tokens[0].trim());
        int y = Integer.parseInt(tokens[1].trim());
        int radius = Integer.parseInt(tokens[2].trim());
        return new Circle (x,y,radius);
    }
}
