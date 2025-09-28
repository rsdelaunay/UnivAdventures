import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

public class Grid {
	private JFrame frame;
	private String windowTitle;
	private int rows, columns, widthPx;

	public Grid(String wndTitle, int rows, int columns, int widthPx) {
		frame = new JFrame(wndTitle);
		this.rows = rows;
		this.columns = columns;
		this.widthPx = widthPx;
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		addFrameContent();

		frame.setResizable(false);
		frame.pack();
		this.open();
		this.moveToCenter();
	}

	public void open() {
		frame.setVisible(true);
	}

	public void moveToCenter(){
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dimension.width/2 - frame.getSize().width/2, dimension.height/2 - frame.getSize().height/2);
	}

	private void addFrameContent() {
		frame.setLayout(new GridLayout(rows, columns));

		int totalLabels = rows * columns;

		while(totalLabels > 0)
		{
			JLabel Label = new JLabel( "" );
			Border border = BorderFactory.createLineBorder( Color.black, 2 );
			Label.setBorder( border );
			Label.setPreferredSize( new Dimension( widthPx, widthPx ) );
			Label.setFont( new Font( "Arial", Font.BOLD, 42 ) );
			Label.setHorizontalAlignment( SwingConstants.CENTER );
			frame.add( Label );
			Label.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed( MouseEvent e )
				{
					changeLabel( Label );
				}
			});
			totalLabels--;
		}

	}

	public JLabel changeLabel( JLabel label )
	{
		label.setText( "X" );
		return label;
	}

//	public static void main(String[] args) {
//		Grid grid = new Grid("Test", 5, 4, 50);
//		grid.open();
//		grid.moveToCenter();
//	}
}
