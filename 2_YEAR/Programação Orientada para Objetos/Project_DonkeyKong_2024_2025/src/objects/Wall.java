package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Wall extends Structure {

	private boolean has_trap;

	public Wall(Point2D position) {
		super("Wall", position, 2, true, false);
		this.has_trap = false;
	}

	public Wall(Point2D position, boolean has_trap) {
		super("Wall", position, 2, true, false);
		this.has_trap = true;
	}

	@Override
	public void heroStandsOn(Point2D position) {
		Point2D below = new Point2D(position.getX(), position.getY() + 1);
		if (this.has_trap) {
			GameEngine gameEngine = GameEngine.getInstance();
			Room room = gameEngine.getCurrentRoom();
			room.addToRemoveQueue(this);
			Trap trap = new Trap(below);
			room.addObject(trap);
			ImageGUI.getInstance().removeImage(this);
		}
	}
}
