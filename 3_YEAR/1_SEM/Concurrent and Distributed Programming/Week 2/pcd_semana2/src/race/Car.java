package race;

import java.util.Observable;

public class Car extends Observable implements Runnable {
	private int id;
	private int limit;
	private int position=0;
	
	public int getId() {
		return id;
	}

	public int getPosition() {
		return position;
	}

	public Car(int id, int limit) {
		super();
		this.id = id;
		this.limit = limit;
	}

	@Override
	public void run()
	{
		while( position < limit )
		{
			long timeToSleep = (long) ((Math.random() + 1) * 200);
			try{
				Thread.sleep(timeToSleep);
				position++;
				setChanged();
				notifyObservers();
			}
			catch( InterruptedException e )
			{
				e.printStackTrace();
				System.out.println("acidente");
				return;
			}
		}
	}
}
