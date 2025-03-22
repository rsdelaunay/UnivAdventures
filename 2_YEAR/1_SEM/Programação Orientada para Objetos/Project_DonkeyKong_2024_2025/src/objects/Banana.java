package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.List;


public class Banana extends Item implements Interactable, Tickable {

    public Banana(Point2D position) {
        super("Banana", position, 3, false, false, 10);
    }

    public void move() {
        GameEngine gameEngine = GameEngine.getInstance();
        Room room = gameEngine.getCurrentRoom();

        Point2D goDown = getPosition().plus(Direction.DOWN.asVector());

        List<GameObject> objects = room.getObjectsInPosition(goDown);
        for (GameObject object : objects) {
            if (object instanceof JumpMan) {
                interactsWithHero();
                ImageGUI.getInstance().removeImage(this);
                GameEngine.getInstance().getCurrentRoom().addToRemoveQueue(this);
            }
        }
        setPosition(goDown); // Atualiza a posição
    }


    @Override
    public void updateTick() {
        //Verificar se a nova posicao ficou fora
        Room currentRoom = GameEngine.getInstance().getCurrentRoom();
        Point2D newPosition = getPosition().plus(Direction.DOWN.asVector());
        if (!currentRoom.isInsideRoom(newPosition)) {
            currentRoom.addToRemoveQueue(this);
            return;
        }
        move();
    }

    @Override
    public void interactsWithHero() {
        Room currentRoom = GameEngine.getInstance().getCurrentRoom();
        JumpMan jumpMan = currentRoom.getJumpMan();
        jumpMan.setHealth(jumpMan.getHealth() - 20);
        ImageGUI.getInstance().removeImage(this);
        GameEngine.getInstance().getCurrentRoom().addToRemoveQueue(this);
        ImageGUI.getInstance().setStatusMessage("Attacked by banana");
    }

    @Override
    public void interaction() {
        Room currentRoom = GameEngine.getInstance().getCurrentRoom();
        List<GameObject> objects = currentRoom.getObjectsInPosition(getPosition());
        JumpMan jumpMan = currentRoom.getJumpMan();

        for (GameObject object : objects) {
            if (object instanceof JumpMan) {
                jumpMan.setHealth(jumpMan.getHealth() - 20);
            }
        }

        boolean canPlaceJumpMan = true;
        for (GameObject object : objects) {
            if (object.isSolid()) {
                canPlaceJumpMan = false;
                break;
            }
        }

        if (canPlaceJumpMan) {
            jumpMan.setPosition(this.getPosition());
            jumpMan.setHealth(jumpMan.getHealth() - 20);
        }

        ImageGUI.getInstance().removeImage(this);
        GameEngine.getInstance().getCurrentRoom().addToRemoveQueue(this);
    }
}
