import javax.swing.*; //swing

/**
 * Represents a race horse as a thread.
 */
public class Horse extends Thread //because we want to create threads (different horses)
{
    // Reference to the text field associated with this horse
    JTextField myTextField;

    /**
     * Constructor for Horse.
     * @param name Name of the horse (thread name)
     * @param textFieldReference Reference to the JTextField to update
     */
    public Horse( String name, JTextField textFieldReference )
    {
        // Set the thread's name using the provided name
        this.setName( name );
        // Store the reference to the text field
        myTextField = textFieldReference;
    }

    /**
     * The code executed by the thread when started.
     */
    // Override the run method to define the thread's behavior
    @Override
    public void run()
    {
        // Repeat 30 times
        for( int i = 0; i < 30; i++ )
        {
            // Print which horse is running
            System.out.println("I am the race horse " + getName());
            // Sleep for a random time between 1000ms and 2000ms
            long timeToSleep = (long) ((Math.random() + 1) * 1000);
            try{
                sleep(timeToSleep);
                // Decrease the value in the text field by 1
                int newValue = Integer.parseInt( myTextField.getText() ) - 1; //parseInt converts String to int
                myTextField.setText( String.valueOf( newValue ) ); //setText only accepts String, so we convert int to String using valueOf
            }
            catch( InterruptedException e )
            {
                // If interrupted, print a message and exit
                System.out.println("Horse " + getName() + " interrupted");
                return;
            }
        }
    }
}
