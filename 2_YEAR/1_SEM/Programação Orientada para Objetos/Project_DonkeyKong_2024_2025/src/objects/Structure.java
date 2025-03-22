package objects;

import pt.iscte.poo.utils.Point2D;

public abstract class Structure extends GameObject{

    public Structure(String name, Point2D position, int layer, boolean solid, boolean climbable) {
        super(name, position, layer,solid,climbable);
    }

    public void heroStandsOn(Point2D position){}

}
