package pt.iscte.dcti.pcd.race;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List; 
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Track component for the car race.
 * Observes Car objects and updates their positions.
 */
public class Track extends JComponent implements Observer {
	private int numCars;
	private int numSteps;
	private int[] carPositions;
	
	// Icon variable declared here
	private ImageIcon icon; 
	
	private boolean winnerFound = false;
	private List<Thread> carThreads; 

	/**
	 * Helper method to safely load the car icon.
	 */
	private void loadIcon() {
		try {
			// Try to load the image from the classpath, assuming it's in the same package
			java.net.URL iconURL = getClass().getResource("azul.gif");
			if (iconURL != null) {
				this.icon = new ImageIcon(iconURL);
			} else {
				// If not found, try to load from direct path and print a warning
				System.err.println("WARNING: The file 'azul.gif' was not found in the classpath (check if it's in the package folder).");
				this.icon = new ImageIcon("azul.gif");
			}
		} catch (Exception e) {
			System.err.println("ERROR loading image 'azul.gif': " + e.getMessage());
			this.icon = new ImageIcon(); // Empty icon to avoid NullPointerException
		}
	}

	/**
	 * Constructor that receives the list of car threads (for challenge 'c').
	 * @param numCars Number of cars
	 * @param numSteps Number of steps in the race
	 * @param carThreads List of car threads
	 */
	public Track(int numCars, int numSteps, List<Thread> carThreads) {
		this.numCars = numCars;
		this.numSteps = numSteps;
		this.carPositions = new int[numCars];
		this.carThreads = carThreads; 
		loadIcon(); // Load the icon safely
	}
	
	/**
	 * Original constructor for parts 'a' and 'b' (without interruption).
	 * @param numCars Number of cars
	 * @param numSteps Number of steps in the race
	 */
	public Track(int numCars, int numSteps) {
		this(numCars, numSteps, null);
	}

	/**
	 * Moves a car to a given position.
	 * @param car Car index
	 * @param position Position to move to
	 */
	private void moveCar(int car, int position) {
		if (car < 0 || car >= numCars)
			throw new IllegalArgumentException("invalid car index: " + car);
		if (position < 0 || position > numSteps)
			throw new IllegalArgumentException("invalid position: " + position);
		carPositions[car] = position;
		repaint(); 
	}

	/**
	 * Paints the track and cars.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (icon == null || icon.getIconWidth() <= 0) {
			 // Draws a substitute square if the icon was not loaded
			 g.setColor(Color.BLUE);
		}

		// Draw the finish line
		g.setColor(Color.RED);
		// Ensure the icon has dimensions to avoid errors in calculation
		int iconWidth = (icon != null && icon.getIconWidth() > 0) ? icon.getIconWidth() : 20; 
		int finishX = getWidth() - iconWidth;
		g.drawLine(finishX, 0, finishX, getHeight());
		
		// Draw tracks and cars
		double deltaY = ((double) getHeight()) / (numCars + 1);
		double deltaX = ((double) getWidth() - iconWidth) / numSteps;
		for (int i = 0; i < numCars; i++) {
			// Track line
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(0, (int) (deltaY * (i + 1)), getWidth(),
							(int) (deltaY * (i + 1)));
			
			// Car (draw only if icon loaded)
			if (icon != null && icon.getImage() != null) {
				g.drawImage(icon.getImage(), (int) (carPositions[i] * deltaX),
								(int) (deltaY * (i + 1)) - icon.getIconHeight(), null);
			} else {
				 // Draw a substitute square if icon fails
				 g.setColor(Color.BLUE);
				 g.fillRect((int) (carPositions[i] * deltaX), (int) (deltaY * (i + 1)) - 10, 20, 10);
			}
		}
	}

	/**
	 * Observer update method. Called when a car changes state.
	 * @param arg0 Observable (Car)
	 * @param arg1 Argument (status)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		Car updatedCar = (Car) arg0;
		
		if ("Finished".equals(arg1)) {
			if (!winnerFound) {
				winnerFound = true;
				final int winnerId = updatedCar.getId() + 1; 
				
				// Show winner dialog on the Swing thread
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(this, 
						"The race is over! The winning car is Nº " + winnerId, 
						"Winner", 
						JOptionPane.INFORMATION_MESSAGE);
				});
				
				// Interrupt all car threads if present
				if (carThreads != null) {
					Car.setRaceFinished(true); 
					
					for (Thread thread : carThreads) {
						if (thread.isAlive()) {
							thread.interrupt(); 
						}
					}
				}
			}
		}
		
		moveCar(updatedCar.getId(), updatedCar.getPosition());
	}
}