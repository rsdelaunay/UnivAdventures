import java.util.ArrayList;

public class Canvas {

    private ArrayList<AbstractFigure> figuras; // criar lista

    public Canvas(){
        figuras = new ArrayList<>();
    }

    public void add(AbstractFigure f){
        figuras.add(f);
    }

    public void remove(AbstractFigure f){
        figuras.remove(f);
    }

    public AbstractFigure greaterAreaFigure(){ //devolver
        AbstractFigure maior = figuras.getFirst();
        for(AbstractFigure f : figuras){
           if(figuras.isEmpty()) return null;
           if(maior.getArea() < f.getArea())
               maior = f;
        }
        return maior;
    }

    public void removeGreaterArea(){
        figuras.remove(greaterAreaFigure());
    }

    public double totalAreas() {
        double s = 0;
        for (AbstractFigure f : figuras) {
            if (figuras.isEmpty()) return 0;
            s += f.getArea();
        }
        return s;
    }
}
