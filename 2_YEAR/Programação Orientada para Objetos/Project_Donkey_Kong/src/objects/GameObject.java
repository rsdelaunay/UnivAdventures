package objects;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.gui.ImageTile;

public abstract class GameObject implements ImageTile {
    private String name;
    private Point2D position;
    private int layer;
    private boolean isClimbable;

    public GameObject(String name, Point2D position, int layer, boolean isClimbable) {
        this.name = name;
        this.position = position;
        this.layer = layer;
        this.isClimbable = isClimbable;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    public boolean isClimbable() {
        return isClimbable;
    }

    public void setClimbable(boolean climbable) {
        isClimbable = climbable;
    }
}
