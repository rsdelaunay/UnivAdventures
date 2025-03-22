package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Stairs extends Structure {
    public Stairs(Point2D position) {
        super("Stairs", position, 0, false, true); // Stairs são "climbable"
    }

    @Override
    public void heroStandsOn(Point2D position){
       return;
    }
}
