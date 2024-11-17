package objects;

import pt.iscte.poo.utils.Point2D;

public class Door extends GameObject {

    public Door(Point2D position) {
        super("DoorClosed", position, 0, false);
    }

}