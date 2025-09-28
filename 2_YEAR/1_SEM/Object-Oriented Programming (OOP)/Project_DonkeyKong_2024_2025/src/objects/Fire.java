package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Fire extends GameObject implements Tickable {

    public Fire(Point2D position) {
        super("Fire", position, 0, false,false);
    }

    @Override
    public void updateTick() {
        ImageGUI.getInstance().removeImage(this);
        GameEngine.getInstance().getCurrentRoom().addToRemoveQueue(this);
    }
}
