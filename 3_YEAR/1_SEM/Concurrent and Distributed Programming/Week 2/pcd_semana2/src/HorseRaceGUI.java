import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main class for the Horse Race GUI
public class HorseRaceGUI
{
    private JFrame mainFrame; // Main window frame
    private boolean raceStarted; // Flag to check if race has started
    JTextField textHorse1; // Text field for horse 1
    JTextField textHorse2; // Text field for horse 2
    JTextField textHorse3; // Text field for horse 3

    // Constructor to set up the GUI
    public HorseRaceGUI()
    {
        mainFrame = new JFrame( "Horse Race" ); // Create the main window
        mainFrame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE ); // Exit application on close
        addFrameContent(); // Add components to the frame
        mainFrame.pack(); // Adjust window size
        open(); // Show the window
        moveToCenter(); // Center the window on the screen
        raceStarted = false; // Race has not started yet
    }

    // Method to make the window visible
    public void open()
    {
        mainFrame.setVisible( true );
    }

    // Method to center the window on the screen
    public void moveToCenter()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation( dimension.width / 2 - mainFrame.getSize().width / 2,
                           dimension.height / 2 - mainFrame.getSize().height / 2
        );
    }

    // Method to start the horse race
    public void startRace(){

        textHorse1.setText( "30");
        textHorse2.setText( "30");
        textHorse3.setText( "30");

        // Create horse objects and associate them with text fields
        Horse h1 = new Horse( "Lusitano", textHorse1 );
        Horse h2 = new Horse( "Oscar", textHorse2 );
        Horse h3 = new Horse( "Temu", textHorse3 );

        // Start horse threads
        h1.start();
        h2.start();
        h3.start();
    }

    // Method to add components to the main frame
    private void addFrameContent()
    {
        mainFrame.setLayout( new BorderLayout() ); // Set layout manager

        JPanel countersPanel = new JPanel(new FlowLayout()); 

        textHorse1 = new JTextField( "horse1" );
        countersPanel.add( textHorse1, BorderLayout.NORTH ); 
        textHorse2 = new JTextField( "horse2" );
        countersPanel.add( textHorse2, BorderLayout.NORTH );
        textHorse3 = new JTextField( "horse3" );
        countersPanel.add( textHorse3, BorderLayout.NORTH );

        JButton button = new JButton( "Start" ); //Start button
        button.addActionListener( // Add action listener to the button
            new ActionListener()
        {
            @Override // Override the actionPerformed method
            public void actionPerformed( ActionEvent e ) // Method called when button is clicked
            {
                if( raceStarted ) // If boolean of raceStarted is true - don't start another
                    return;
                startRace();
            }
        } );

        mainFrame.add( countersPanel, BorderLayout.NORTH );
        mainFrame.add( button, BorderLayout.CENTER );
    }

    // Main method to launch the application
    public static void main(String[] args) {
        HorseRaceGUI window = new HorseRaceGUI();
    }
}
