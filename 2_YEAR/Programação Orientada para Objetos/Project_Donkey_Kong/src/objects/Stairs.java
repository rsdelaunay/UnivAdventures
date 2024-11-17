package objects;

import pt.iscte.poo.utils.Point2D;

public class Stairs extends GameObject {
    public Stairs(Point2D position) {
        super("Stairs", position, 0, true); // Stairs são "climbable"
    }
}
