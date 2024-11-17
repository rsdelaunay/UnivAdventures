package objects;

import pt.iscte.poo.utils.Point2D;

public class Sword extends GameObject {

    public Sword(Point2D position) {
        super("Sword", position, 1, true); // `true` indica que é um objeto interativo
    }
}
