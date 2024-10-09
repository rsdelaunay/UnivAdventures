import java.io.File;

public class LineCircleReader extends LineObjectReader<Circle> {

    public LineCircleReader(File file) {
        super(file);
    }

    @Override
    public Circle lineToObject(String line) {
        // Partindo do princípio que a linha contém: radius, centerX, centerY
        String[] parts = line.split(",");
        double radius = Double.parseDouble(parts[0].trim());
        int centerX = Integer.parseInt(parts[1].trim());
        int centerY = Integer.parseInt(parts[2].trim());
        return new Circle(centerX, centerY, radius);
    }
}
