package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Meat extends Item implements Pickable, Tickable {

    private int tick = 10;
    private boolean good;
    private int effectValue;


    public Meat(Point2D position) {
        super("GoodMeat", position, 1, false,false,10); // `true` indica que é um objeto interativo
        this.good = true;
        this.effectValue = getEffectValue();
    }

    @Override
    public void pickedByHero() {
        Room currentRoom = GameEngine.getInstance().getCurrentRoom();
        if (this.good) {
            currentRoom.getJumpMan().setDamage(currentRoom.getJumpMan().getDamage() + effectValue);
            ImageGUI.getInstance().removeImage(this);
            GameEngine.getInstance().getCurrentRoom().getGameObjects().remove(this);
            ImageGUI.getInstance().setStatusMessage("Tastes good ++Dmg");
        }
        else {
            currentRoom.getJumpMan().setDamage(currentRoom.getJumpMan().getDamage() - effectValue);
            ImageGUI.getInstance().removeImage(this);
            GameEngine.getInstance().getCurrentRoom().getGameObjects().remove(this);
            ImageGUI.getInstance().setStatusMessage("Tastes bad --Dmg");

        }
    }

    @Override
    public void updateTick() {
        this.tick--;
        if (this.tick == 0){
            this.good = false;
            this.setName("BadMeat");
        }
    }

}
