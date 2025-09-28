package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public class Bomb extends Item implements Pickable,Tickable,Interactable {

    private boolean isArmed = false;
    private int armed = 5;

    public Bomb(Point2D position, boolean isArmed) {
        super("Bomb", position, 1, false, false, 300);
        this.isArmed = isArmed;
        if (isArmed) {this.setSolid();}
    }


    @Override
    public void pickedByHero() {
        GameEngine gameEngine = GameEngine.getInstance();
        Room room = gameEngine.getCurrentRoom();
        ImageGUI.getInstance().removeImage(this);
        room.addToRemoveQueue(this);
        GameEngine.getInstance().getCurrentRoom().getJumpMan().addBombs();
    }

    @Override
    public void updateTick() {
        if (isArmed) {
            System.out.println(armed--);}
        if (armed == 0) {
            Room currentRoom = GameEngine.getInstance().getCurrentRoom();
            ImageGUI.getInstance().removeImage(this);
            GameEngine.getInstance().getCurrentRoom().addToRemoveQueue(this);
            Fire fire_center = new Fire(this.getPosition());
            currentRoom.addObject(fire_center);

            for (GameObject go : GameEngine.getInstance().getCurrentRoom().getGameObjects()) { //elimina tudo à volta
                for (Point2D point : this.getPosition().getNeighbourhoodPoints()) {
                    if (go.getPosition().equals(point) && !(go instanceof JumpMan) && !(go instanceof Stairs) && !(go instanceof Wall)) {
                        ImageGUI.getInstance().removeImage(go);
                        GameEngine.getInstance().getCurrentRoom().addToRemoveQueue(go);
                        currentRoom.addObject(new Floor(go.getPosition()));
                        Fire fire = new Fire(go.getPosition());
                        currentRoom.addObject(fire);
                    }
                    if (go instanceof JumpMan && currentRoom.getJumpMan().getPosition().equals(point)){ //Explode e o heroi está lá
                        currentRoom.getJumpMan().setHealth(0);
                    }
                }
            }
        }
    }

    @Override
    public void interactsWithHero() {

    }

    @Override
    public void interaction() {
        System.out.println("Pisei a bomba");
        if(isArmed) {
            armed = 1;
        }
    }
}



