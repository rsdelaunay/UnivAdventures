package pt.iscte.dcti.pcd.race;

import java.util.Observable;

/**
 * Represents a car in a race. Each car runs in its own thread and notifies observers of its position.
 */
public class Car extends Observable implements Runnable { // Observable to notify Track of position changes and
	//Runnable to run in separated threads

	private int id; // Unique identifier for the car
	private int limit; // Finish line position
	private int position = 0; // Current position of the car
	private volatile boolean running = true; // Flag to control if the car is running

	// Static variable to indicate if the race has finished
	private static volatile boolean raceFinished = false;

	//Getters and Setters
	public int getId() {
		return id;
	}

	public int getPosition() {
		return position;
	}

	public int getLimit() {
		return limit;
	}

	/**
	 * Sets the race finished flag
	 * @param finished true if the race is finished, false otherwise
	 */
	public static void setRaceFinished(boolean finished) {
		raceFinished = finished;
	}

	/**
	 * Constructs a car with the given ID and finish line position.
	 * @param id the car's ID
	 * @param limit the finish line position
	 */
	public Car(int id, int limit) {
		super();
		this.id = id;
		this.limit = limit;
	}

	/**
	 * Stops the car from running.
	 */
	public void stop() {
		running = false;
	}

	/**
	 * Runs the car in a separate thread, moving it forward until it reaches the finish line,
	 * is stopped, or the race is finished. Notifies observers of position changes and when finished.
	 */
	@Override
	public void run() {
		// Check if the car should keep running, hasn't reached the finish line, and the race isn't finished
		while (running && position < limit && !raceFinished) {
			position++; // Move the car forward
			setChanged(); // To indicate that the observable has changed
			notifyObservers(); // Notify observers of the position change

			// Check if the car has reached the finish line
			if (position >= limit) {
				// Notify observers that the car has finished
				setChanged();
				notifyObservers("Finished");
				break; // Exit the loop after notifying finish
			}

			try {
				// Sleep to simulate movement and allow thread scheduling
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// Handle interruption (challenge 'c')
				Thread.currentThread().interrupt();
				running = false; // Stop running if interrupted
				break;
			}
		}
	}
}