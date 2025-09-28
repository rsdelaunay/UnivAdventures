package pong;

import java.util.Observable;

public class PlayerEntity extends Observable {
	private int id;
	private int limit;
	private int position;
    private PlayerDirection direction;
	private final int STEP_SIZE = 2;

	public int getId() {
		return id;
	}

	public int getPosition() {
		return position;
	}

	public PlayerDirection getDirection()
	{
		return direction;
	}

	public void setDirection( PlayerDirection direction )
	{
		this.direction = direction;
	}

	public PlayerEntity( int id, int limit) {
		super();
		this.id = id;
		this.limit = limit;
		position = limit/2;
		direction = PlayerDirection.NONE;
	}

	public void movePlayer(PlayerDirection direction)
	{
        if( direction == PlayerDirection.UP )
		{
			if( position - STEP_SIZE < 0 )
				return;
			position -= STEP_SIZE;
		}
		else if( direction == PlayerDirection.DOWN )
		{
			if( position + STEP_SIZE > limit )
				return;
			position += STEP_SIZE;
		}
		else
		{
			return;
		}

		setChanged();
		notifyObservers();
	}
}
