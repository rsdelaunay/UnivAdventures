public abstract class AbstractFigure implements Figure {
    private int x;
    private int y;
    public AbstractFigure(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public abstract double getPerimeter();
    public abstract double getArea();

}