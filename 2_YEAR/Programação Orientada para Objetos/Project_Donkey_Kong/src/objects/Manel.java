package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel extends GameObject {

	public Manel(Point2D initialPosition) {
		super("JumpMan", initialPosition, 1, false);
	}

	public void move(Direction d) {
		Point2D nextPos = getPosition().plus(d.asVector());
		// Verifica limites da grelha 10x10
		if (nextPos.getX() < 0 || nextPos.getY() < 0 || nextPos.getX() >= 10 || nextPos.getY() >= 10)
			return;
		setPosition(nextPos);
	}
}
