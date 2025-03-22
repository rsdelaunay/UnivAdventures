package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.List;

public class Gorilla extends Personagem implements Interactable, Tickable {

    public Gorilla(Point2D position) {
        super("DonkeyKong", position, 1, 60, 15, 1, true, false);
    }

    @Override
    public void move() {
        GameEngine gameEngine = GameEngine.getInstance();
        Room room = gameEngine.getCurrentRoom();

        Point2D goLeft = getPosition().plus(Direction.LEFT.asVector());
        Point2D goRight = getPosition().plus(Direction.RIGHT.asVector());

        Point2D targetPosition = Math.random() < 0.5 ? goLeft : goRight;

        if(GameEngine.getInstance().getCurrentRoomIndex() == 2){
            if(getPosition().getX() - GameEngine.getInstance().getCurrentRoom().getJumpMan().getPosition().getX() <= 0)
            targetPosition = goRight;
            else
                targetPosition = goLeft;
        }

        if (boundaries(targetPosition)) {
            List<GameObject> nextObjects = room.getObjectsInPosition(targetPosition);

            for (GameObject object : nextObjects) {
                if (object instanceof JumpMan) {
                    interactsWithHero();
                    return;
                }
                if(object instanceof Bomb){
                    ((Interactable) object).interaction();
                    return;
                }
                if (object.isSolid()) {
                    return;
                }
            }
            setPosition(targetPosition);
        }
    }

    @Override
    public void updateTick() {
        move();
        // Probabilidade de 30% para lançar uma banana
        if (Math.random() < 0.3) {
            Point2D bananaPosition = getPosition().plus(Direction.DOWN.asVector());
            Room currentRoom = GameEngine.getInstance().getCurrentRoom();
            Banana banana = new Banana(bananaPosition);
            currentRoom.addObject(banana);
        }
    }

    @Override
    public void interactsWithHero() {
        Room currentRoom = GameEngine.getInstance().getCurrentRoom();
        currentRoom.getJumpMan().setHealth(currentRoom.getJumpMan().getHealth() - this.getDamage());
        System.out.println("Ataquei o heroi " + currentRoom.getJumpMan().getHealth());
        ImageGUI.getInstance().setStatusMessage("Attacked by gorilla");
    }

    @Override
    public void interaction() {
        Room currentRoom = GameEngine.getInstance().getCurrentRoom();
        this.setHealth(getHealth()- currentRoom.getJumpMan().getDamage());
        System.out.println("Ataquei o macaco " + this.getHealth());
        if(getHealth() <= 0) {
            ImageGUI.getInstance().removeImage(this);
            GameEngine.getInstance().getCurrentRoom().addToRemoveQueue(this);} // remove o gorila
    }

}
