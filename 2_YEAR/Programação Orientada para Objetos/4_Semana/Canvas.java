import java.util.ArrayList;
import java.util.List;

public class Canvas {
    private static List<AbstractFigure> figuras;

    public Canvas() {
        figuras = new ArrayList<>();
    }

    public void add(AbstractFigure f){
        figuras.add(f);
    }

    public void remove(AbstractFigure f){
        figuras.remove(f);
    }


    public AbstractFigure greaterAreaFigure() {
        if(figuras.isEmpty()) return null;
        AbstractFigure maior = figuras.getFirst(); //inicializar com o primeiro elemento da figura
        for (AbstractFigure figure : figuras) {
            if (maior.getArea() < figure.getArea())
                maior = figure;
        }
        return maior;
    }

    public void removeGreaterAreaFigure() {
        AbstractFigure maior = greaterAreaFigure();
        remove(maior);
    }

    public int totalAreaOfFigures(){
        int area = 0;
        for (AbstractFigure figure : figuras) {
            area += figure.getArea();
        }
        return area;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Figure figure : figuras) {
            sb.append(figure.toString()).append("\n"); // Adiciona a string de cada figura e quebra de linha
        }
        return sb.toString(); // Retorna a string concatenada
    }


}