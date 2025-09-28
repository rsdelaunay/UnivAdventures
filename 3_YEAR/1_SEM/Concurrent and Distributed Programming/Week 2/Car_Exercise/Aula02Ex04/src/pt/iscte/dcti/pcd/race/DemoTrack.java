package pt.iscte.dcti.pcd.race;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * DemoTrack - Main class to run the car race simulation.
 */
public class DemoTrack {
	
	// Race configuration
	private static final int NUM_CARS = 5;
	private static final int NUM_STEPS = 50;

	public static void main(String[] args) {
		// GUI code must run on the Event Dispatch Thread (EDT)
		SwingUtilities.invokeLater(() -> createAndShowGUI()); 
	}

	/**
	 * Creates and displays the GUI for the race simulation.
	 */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Race Simulation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// List to store references to all threads (for Challenge 'c')
		List<Thread> carThreads = new ArrayList<>();
		
		// List to store references to all cars
		List<Car> cars = new ArrayList<>();
		
		// Instantiate the track, passing the thread list for Challenge 'c'
		// For only parts 'a' and 'b', use: new Track(NUM_CARS, NUM_STEPS)
		Track track = new Track(NUM_CARS, NUM_STEPS, carThreads); 
		track.setPreferredSize(new Dimension(800, 400));

		// 1. Create cars, threads, and connect the observer (Track)
		for (int i = 0; i < NUM_CARS; i++) {
			Car car = new Car(i, NUM_STEPS);
			car.addObserver(track); // Track is the observer of Car
			cars.add(car);

			Thread thread = new Thread(car); // Create thread with the Runnable object (Car)
			carThreads.add(thread); // Store the reference
		}

		// Set up and display the window
		frame.getContentPane().add(track, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// 2. Start the race (start all threads)
		for (Thread thread : carThreads) {
			thread.start();
		}
	}
}
