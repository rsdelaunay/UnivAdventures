package objects;

import pt.iscte.poo.utils.Point2D;

public abstract class Item extends GameObject {

    private static int effectValue;

    public Item(String name, Point2D position, int layer,boolean solid, boolean climbable, int effectValue) {
        super(name, position, layer, solid, climbable);
        Item.effectValue = effectValue;
    }

    public static int getEffectValue() {
        return effectValue;
    }
}
