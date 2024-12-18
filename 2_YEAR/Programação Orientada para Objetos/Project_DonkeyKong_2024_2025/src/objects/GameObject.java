package objects;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.gui.ImageTile;

public abstract class GameObject implements ImageTile {
    private String name;
    private Point2D position;
    private int layer;
    private boolean solid;
    private boolean climbable;


    public GameObject(String name, Point2D position, int layer, boolean solid, boolean climbable) {
        this.name = name;
        this.position = position;
        this.layer = layer;
        this.solid = solid;
        this.climbable = climbable;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid() { solid = true;}

    public boolean isClimbable() {
        return climbable;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


}
