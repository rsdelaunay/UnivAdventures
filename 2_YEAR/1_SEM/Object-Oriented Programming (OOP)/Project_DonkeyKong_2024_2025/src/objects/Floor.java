package objects;

import pt.iscte.poo.utils.Point2D;

public class Floor extends Structure {

	public Floor(Point2D position) {
		super("Floor", position, 0, false, false);
	}

	@Override
	public void heroStandsOn(Point2D position) {
		return;
	}
}
