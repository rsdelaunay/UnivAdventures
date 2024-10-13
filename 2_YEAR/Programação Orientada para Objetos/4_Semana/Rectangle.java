public class Rectangle extends AbstractFigure {
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height) {
        super(x,y);
        this.width = width;
        this.height = height;
    }

    @Override
    public double getPerimeter() {
        return 2*width + 2*height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public String toString(){
        return "Rectangle [area=" + getArea() + "]";
    }
}
