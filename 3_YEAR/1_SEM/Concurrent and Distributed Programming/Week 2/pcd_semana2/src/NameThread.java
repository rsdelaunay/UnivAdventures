public class NameThread extends Thread // extends Thread
{
    // Attributes
    private String name; 

    // Constructor that receives the thread name
    public NameThread( String name )
    {
        this.name = name;
    }

    // Run method that will be executed when the thread starts
    @Override //Override to indicate we are overriding the run method
    public void run() //will be executed when the thread starts
    {
        for( int i = 0; i < 10; i++ ) // Loop 10 times 
        {
            // Prints the thread name
            System.out.println("I am thread " + name);
            // Generates a random sleep time between 1000ms and 2000ms
            long timeToSleep = (long) ((Math.random() + 1) * 1000);
            //Apply sleep
            try{
                sleep(timeToSleep); // Makes the thread sleep
            }
            catch( InterruptedException e )
            {
                // If the thread is interrupted, prints a message and ends
                System.out.println("I am thread " + name + " and I was interrupted");
                return;
            }
        }
    }

    // Main method to start the threads
    public static void main( String[] args )
    {
        // Create two NameThread objects with different names
        NameThread th1 = new NameThread( "Alpha" );
        NameThread th2 = new NameThread( "Beta" );

        // Start both threads
        th1.start();
        th2.start();
        // Without join the main thread would not wait for th1 and th2 to finish
        // and would interrupt them after 4 seconds
        
        try
        {
            // Main thread sleeps for 4000ms (4 seconds)
            Thread.sleep(4000);
        }
        catch( InterruptedException e )
        {
            // If main thread is interrupted, print message and exit
            System.out.println("I am the Main thread and I was interrupted");
            return;
        }

        // Interrupt both threads after main thread wakes up
        th1.interrupt();
        th2.interrupt();

        try{
            // Wait for both threads to finish
            th1.join();
            th2.join();
        }
        catch( InterruptedException e )
        {
            // If main thread is interrupted while waiting, print message and exit
            System.out.println("I am the Main thread and I was interrupted");
            return;
        }

        // Print final message after threads have finished
        System.out.println("The threads have finished and I will terminate");
    }
}
