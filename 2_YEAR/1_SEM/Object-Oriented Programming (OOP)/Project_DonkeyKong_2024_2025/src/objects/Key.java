package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

import java.util.List;

public class Key extends Item implements Pickable{

    public Key(Point2D position) {
        super("Key", position, 1, false,false,0); // `true` indica que é um objeto interativo
    }


    @Override
    public void pickedByHero() {
        GameEngine gameEngine = GameEngine.getInstance();
        Room room = gameEngine.getCurrentRoom();
        ImageGUI.getInstance().removeImage(this);
        GameEngine.getInstance().getCurrentRoom().addToRemoveQueue(this);
        ImageGUI.getInstance().setStatusMessage("PORTA DESBLOQUEADA");
        List<GameObject> objects = room.getGameObjects();
        for (GameObject object : objects) {
            if (object instanceof Door){
                ((Door) object).openDoor();
            }
        }
    }


}
