package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Sword extends Item implements Pickable{
    private int effectValue;

    public Sword(Point2D position) {
        super("Sword", position, 1, false,false,20); // `true` indica que é um objeto interativo
        this.effectValue = getEffectValue();
    }


    @Override
    public void pickedByHero() {
        Room currentRoom = GameEngine.getInstance().getCurrentRoom();
        currentRoom.getJumpMan().setDamage(currentRoom.getJumpMan().getDamage()+effectValue);
        ImageGUI.getInstance().removeImage(this);
        GameEngine.getInstance().getCurrentRoom().addToRemoveQueue(this);
        ImageGUI.getInstance().setStatusMessage("Apanhei a ESPADA + 20 de dano");
    }

}
