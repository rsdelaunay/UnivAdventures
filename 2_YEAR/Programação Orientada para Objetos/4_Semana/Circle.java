public class Circle extends AbstractFigure{
    private double radius;

    public Circle (int x, int y, double radius){
        super(x,y);
        this.radius = radius;
    }

    @Override
    public double getArea(){
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter(){
        return 2*Math.PI * radius;
    }

    @Override
    public String toString(){
        return "Circle [radius = " + radius + "area = " + getArea() + "]";
    }

}
