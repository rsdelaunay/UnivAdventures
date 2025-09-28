import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NumberGenerator {
    private JFrame frame;
    ThreadA thA;
    ThreadB thB;
    
    public NumberGenerator()
    {
    	//	Frame initializing
    	
        frame = new JFrame( "Numbers Gen" );
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        addFrameContent();
        frame.pack();
        frame.setVisible( true );
        moveToCenter();
        
        //	Threads initializing

        thA = new ThreadA();
        thB = new ThreadB();

        thA.start();
        thB.start();

        try {
			thA.join();
	        thB.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    public void moveToCenter()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation( dimension.width / 2 - frame.getSize().width / 2,
                           dimension.height / 2 - frame.getSize().height / 2
        );
    }

    private void addFrameContent()
    {
        frame.setLayout( new FlowLayout() );

        JButton button = new JButton( "Stop" );
        button.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                thA.interrupt();
                thB.interrupt();
//                try {
//                	Thread.sleep(5000);
//            		System.exit(0);
//                }
//                catch(InterruptedException e2)
//                {
//            		System.exit(0);
//                }
            }
        } );

        frame.add( button );
    }
    
    private class ThreadA extends Thread{
    	
    	private int numbersGen = 0;

		@Override
		public void run()
		{
	        while(true)
	        {
	        	if(!isInterrupted())
	        	{
	        		System.out.println("ThreadA number generated: " + (int)((Math.random() * 9000) + 1000) );
	        		numbersGen++;
        		}
	        	else
	        	{
	        		break;
	        	}
	        }
    		System.out.println("ThreadA gerou " + numbersGen + " numeros");
		}
    }
    
    private class ThreadB extends Thread{
    	
    	private int numbersGen = 0;

		@Override
		public void run()
		{
	        while(true)
	        {
        		try		//	No need to check for isInterrupted(), we can just use the Interruption Exception
        		{
	        		System.out.println("ThreadB number generated: " + (Math.random() * 9) + 1) ;
	        		numbersGen++;
        			sleep(500);
        		}
        		catch(InterruptedException e)
        		{
	        		break;
        		}
	        }
    		System.out.println("ThreadB gerou " + numbersGen + " numeros");
		}
    }
    
    public static void main(String[] args) {
    	NumberGenerator window = new NumberGenerator();
    	
    }
}
