package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class Door extends Structure implements Interactable {

    private boolean open;

    public Door(Point2D position) {
        super("DoorClosed", position, 0, false, false);
        this.open = false;
    }

    public void openDoor(){
        this.open = true;
        this.setName("DoorOpen");
    }

    @Override
    public void interactsWithHero() {
        return;
    }

    @Override
    public void interaction() {
        if (this.open){
            GameEngine.getInstance().advanceToNextRoom();
            System.out.println("Próxima sala carregada");
        }
    }

    @Override
    public void heroStandsOn(Point2D position) {
        return;
    }

}