import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class FrameTest {
	private JFrame frame;

	public FrameTest() {
		frame = new JFrame("Window Title Test");
		
		// para que o botao de fechar a janela termine a aplicacao
		// caso nao esteja definido, a janela fecha
		// mas a aplicacao mantem-se a correr
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		addFrameContent();
		
		// para que a janela se redimensione de forma a ter todo o seu conteudo visivel
		frame.pack();
		System.out.println("Current size: " + frame.getSize().toString());
	}

	public void open() {
		// para abrir a janela (torna-la visivel)
		frame.setVisible(true);
	}

	public void moveToCenter(){
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dimension.width/2 - frame.getSize().width/2, dimension.height/2 - frame.getSize().height/2);
	}

	private void addFrameContent() {
		/* para organizar o conteudo em grelha (linhas x colunas)
		se um dos valores for zero, o numero de linhas ou colunas (respetivamente) fica indefinido,
		e estas sao acrescentadas automaticamente */

		frame.setLayout(new GridLayout(4,2));

		JLabel textLabel = new JLabel("text");
		frame.add(textLabel);
		JTextField text = new JTextField("text");
		frame.add(text);

		JLabel widthLabel = new JLabel("width");
		frame.add(widthLabel);
		JTextField widthText = new JTextField("");
		frame.add(widthText);

		JLabel heightLabel = new JLabel("height");
		frame.add(heightLabel);
		JTextField heightText = new JTextField("");
		frame.add(heightText);

		JCheckBox check = new JCheckBox("check");
		frame.add(check);
		JButton button = new JButton("button");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//String titleToSet = text.getText();
				//frame.setTitle(titleToSet);

				int widthToSet = 162;
				int heightToSet = 138;

				try{
					widthToSet = Integer.parseInt(widthText.getText());
					heightToSet = Integer.parseInt(heightText.getText());
				}
				catch( Exception e2 )
				{
					System.out.println("altura/largura invalidos");
					frame.pack();
				}

				if(widthToSet < 162)
					widthToSet = 162;

				if(heightToSet < 138)
					heightToSet = 138;

				frame.setSize(widthToSet, heightToSet);
				moveToCenter();
				System.out.println("a definir altura " + widthToSet + " e largura " + heightToSet);
			}
		});
		frame.add(button);
	}

	public static void main(String[] args) {
		FrameTest window = new FrameTest();
		window.open();
		window.moveToCenter();
	}
}
