package race;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class Track extends JComponent implements Observer{
	private int numCars;
	private int numSteps;
	private int[] carPositions;
	private ImageIcon icon = new ImageIcon("azul.gif");

	private JFrame frame;
	private ArrayList<Car> carObj;
	private ArrayList<Thread> carThread;

	public Track(int numCars, int numSteps) {
		this.numCars = numCars;
		this.numSteps = numSteps;
		carPositions = new int[numCars];
		carObj = new ArrayList<>();
		carThread = new ArrayList<>();

		for( int i = 0; i < numCars; i++ )
		{
			carObj.add( i, new Car(i, numSteps));
			carObj.get( i ).addObserver( this );
			carThread.add(i, new Thread(carObj.get( i )));
		}
		
		addFrameContent();
	}
	
	private void addFrameContent()
	{
		frame = new JFrame("Demo Track");
		frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
		frame.add(this);
		frame.setSize(500, (100 * numCars)-1 + 25);
		frame.setVisible(true);
	}
	
	private void moveCar(int car, int position) {
		if (car < 0 || car >= numCars)
			throw new IllegalArgumentException("invalid car index: " + car);
		if (position < 0 || position > numSteps)
			throw new IllegalArgumentException("invalid position: " + position);
		carPositions[car] = position;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		double deltaY = ((double) getHeight()) / (numCars + 1);
		double deltaX = ((double) getWidth() - icon.getIconWidth()) / numSteps;
		for (int i = 0; i < numCars; i++) {
			g.drawLine(0, (int) (deltaY * (i + 1)), getWidth(),
					(int) (deltaY * (i + 1)));
			g.drawImage(icon.getImage(), (int) (carPositions[i] * deltaX),
					(int) (deltaY * (i + 1)) - icon.getIconHeight(), null);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Car updatedCar=(Car)arg0;
		moveCar(updatedCar.getId(), updatedCar.getPosition());
		// Redraw everything!
		invalidate();
	}

	public void startRace()
	{
		for( Thread t : carThread)
		{
			t.start();
		}
	}

	public static void main( String[] args )
	{
		Track track = new Track(6, 100);
		track.startRace();
	}
}
