package pong;

import java.util.Observable;

public class Ball extends Observable implements Runnable
{
    private double posX, posY;
    private double velX, velY;

    public Ball()
    {
        this.posX = 250.0;
        this.posY = 250.0;

        this.velX = -2.0;
        this.velY = 2.0;
    }

    public double getNextX()
    {
        return posX + velX;
    }

    public double getNextY()
    {
        return posY + velY;
    }

    public double getVelocityX()
    {
        return velX;
    }
    public double getVelocityY()
    {
        return velY;
    }

    public double getX()
    {
        return posX;
    }

    public double getY()
    {
        return posY;
    }

    public void setNewVelocity( double x, double y )
    {
        velX = x;
        velY = y;
    }

    private void moveBall()
    {
        posX += velX;
        posY += velY;
    }

    @Override
    public void run()
    {
        while ( true )
        {
            try{
                Thread.sleep( 25 );
                if(posX < 0 || posY < 0)
                {
                    posX = 250;
                    posY = 250;
                }
                moveBall();
                System.out.println( "Ball X: " + getX() + ", Y: " + getY() );
                setChanged();
                notifyObservers();
            }
            catch( InterruptedException e )
            {
                throw new RuntimeException( e );
            }
        }
    }

}
