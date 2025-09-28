package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class Pong extends JComponent implements Observer, KeyListener
{
	private final int scoreLimit;
	private final int numSteps;
	private int[] playerPositions;
	private ImageIcon icon = new ImageIcon("Player.gif");
	private ImageIcon ballIcon = new ImageIcon("Ball.gif");

	private JFrame frame;

	private PlayerEntity playerOne;
	private PlayerEntity playerTwo;

	private Ball ball;
	private Thread ballThread;

	public Pong(int scoreLimit, int numSteps) {
		this.scoreLimit = scoreLimit;
		this.numSteps = numSteps;
		
		playerPositions = new int[] {numSteps/2, numSteps/2};

		playerOne = new PlayerEntity(0, numSteps);
		playerTwo = new PlayerEntity(1, numSteps);

		playerOne.addObserver( this );
		playerTwo.addObserver( this );

		ball = new Ball();
		ball.addObserver( this );

		ballThread = new Thread( ball );

		addFrameContent();
	}
	
	private void addFrameContent()
	{
		frame = new JFrame("Pong");
		frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
		frame.add(this);
		frame.setSize(500, 500); //(100 * numCars)-1 + 25);
		frame.setVisible(true);
		frame.setFocusableWindowState( true );
		frame.setFocusTraversalKeysEnabled(false);
		frame.addKeyListener(this);
	}
	
	private void movePlayer(int player, int position) {
		if (player < 0 || player >= 2)
			throw new IllegalArgumentException("invalid player index: " + player);
		if (position < 0 || position > numSteps)
			return; //throw new IllegalArgumentException("invalid position: " + position);
		playerPositions[player] = position;
		//repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0,0,getWidth(), getHeight());	//	Set dark background

		g.setColor(Color.LIGHT_GRAY);
		
		double deltaY = ((double) getHeight() - icon.getIconHeight()) / numSteps;
		double deltaX = ((double) getWidth());

		int smallHeightMargin = (int)(getHeight() * 0.05);
		g.drawLine(getWidth()/2, smallHeightMargin, getWidth()/2, getHeight() - smallHeightMargin);
		
		//	Players

		int playerMarginToBorder = (int)(deltaX * 0.05);
		
		g.drawImage(icon.getImage(), playerMarginToBorder, 
				(int)(playerPositions[0] * deltaY), null);

		g.drawImage(icon.getImage(), (int)deltaX - playerMarginToBorder, 
				(int)(playerPositions[1] * deltaY), null);

//		System.out.println("player 1 margin: " + playerMarginToBorder);
//		System.out.println("player 2 margin: " + ((int)deltaX - playerMarginToBorder));
//
//		System.out.println("player 1 y pos: " + ((int)(playerPositions[0] * deltaY)));
//		System.out.println("player 2 y pos: " + ((int)(playerPositions[1] * deltaY)));
		g.drawImage(ballIcon.getImage(), (int)ball.getX(), (int)ball.getY(), null);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if( arg0 instanceof PlayerEntity)
		{
			PlayerEntity updatedPlayer = (PlayerEntity)arg0;
			movePlayer( updatedPlayer.getId(), updatedPlayer.getPosition() );
		}
		repaint();
		// Redraw everything!
		invalidate();
	}

	public void startGame()
	{
		Timer timer = new Timer( 20, e -> playerAction() );
		timer.start();
		ballThread.start();
	}

	public void playerAction()
	{
		if(playerOne.getDirection() != PlayerDirection.NONE)
			playerOne.movePlayer( playerOne.getDirection() );

		if(playerTwo.getDirection() != PlayerDirection.NONE)
			playerTwo.movePlayer( playerTwo.getDirection() );
	}

	@Override
	public void keyTyped( KeyEvent e )
	{
		//
	}

	@Override
	public void keyReleased( KeyEvent e )
	{
		//	Player One
		if( e.getKeyCode() == KeyEvent.VK_Q )
		{
			if(playerOne.getDirection() == PlayerDirection.UP)
				playerOne.setDirection(PlayerDirection.NONE);
		}
		else if( e.getKeyCode() == KeyEvent.VK_A )
		{
			if(playerOne.getDirection() == PlayerDirection.DOWN)
				playerOne.setDirection(PlayerDirection.NONE);
		}

		//	Player Two
		if( e.getKeyCode() == KeyEvent.VK_P )
		{
			if(playerTwo.getDirection() == PlayerDirection.UP)
				playerTwo.setDirection(PlayerDirection.NONE);
		}
		else if( e.getKeyCode() == KeyEvent.VK_L )
		{
			if(playerTwo.getDirection() == PlayerDirection.DOWN)
				playerTwo.setDirection(PlayerDirection.NONE);
		}
	}

	@Override
	public void keyPressed( KeyEvent e)
	{
		//	Player One
		if( e.getKeyCode() == KeyEvent.VK_Q )
		{
			playerOne.setDirection(PlayerDirection.UP);
		}
		else if( e.getKeyCode() == KeyEvent.VK_A )
		{
			playerOne.setDirection(PlayerDirection.DOWN);
		}

		//	Player Two
		if( e.getKeyCode() == KeyEvent.VK_P)
		{
			playerTwo.setDirection(PlayerDirection.UP);
		}
		else if( e.getKeyCode() == KeyEvent.VK_L )
		{
			playerTwo.setDirection(PlayerDirection.DOWN);
		}
	}

	public static void main( String[] args )
	{
		Pong instance = new Pong(6, 100);
		instance.startGame();
	}
}
