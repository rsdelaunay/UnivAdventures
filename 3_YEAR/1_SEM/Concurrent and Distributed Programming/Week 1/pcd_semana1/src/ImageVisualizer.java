import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

public class ImageVisualizer
{
	private final String path;
	private JFrame frame;
	private File[] currFiles;
	private int imgIdx;
	private JLabel currImage;
	private ImageIcon icon;
	private JLabel imageNameLabel;

	public ImageVisualizer() {
		this("images");
	}
	
	public ImageVisualizer(String path) {
		frame = new JFrame("Window Title Test");
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.path = path;
		currFiles = readFolder(path);
		
		addFrameContent();
		setCurrentImage(0);
		frame.pack(); //redimensiona janela compacta de forma a ter todo o seu conteudo visivel
	}

	public void open() {
		frame.setVisible(true);
	}

	private void addFrameContent() {

		frame.setLayout(new BorderLayout());
		
		imageNameLabel = new JLabel("Current Image.jpg");
		currImage = new JLabel("image canvas");
		icon = new ImageIcon("filepath/image_file.jpg");
		
		JButton updateButton = new JButton("Update");
		JButton previousButton = new JButton("<<<");
		JButton nextButton = new JButton(">>>");

		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentImage(imgIdx - 1);
			}
		});
		
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentImage(imgIdx + 1);
			}
		});
		
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currFiles = readFolder(path);
				setCurrentImage(0);
			}
		});

		frame.add(currImage, BorderLayout.CENTER);
		frame.add(imageNameLabel, BorderLayout.NORTH);
		frame.add(previousButton, BorderLayout.WEST);
		frame.add(nextButton, BorderLayout.EAST);
		frame.add(updateButton, BorderLayout.SOUTH);
	}
	
	private void setCurrentImage(int imageIndex)
	{
		if(imageIndex < -1 || imageIndex > currFiles.length) //out of bounds
			return;
		
		if(imageIndex == -1 || imageIndex == currFiles.length) //bounds
		{
			imgIdx = imageIndex;
			imageNameLabel.setText(" ");
			currImage.setText("Fim das Imagens :(");
			currImage.setIcon(null);
			return;
		}

		imageNameLabel.setText(currFiles[imageIndex].getName());
		currImage.setText("");
		icon = new ImageIcon(path + "/" + currFiles[imageIndex].getName());
		currImage.setIcon(icon);
		imgIdx = imageIndex;
		return;
	}
	
	private File[] readFolder(String path) {
		File[] files = new File(path).listFiles(new FileFilter() {
			public boolean accept(File f) {    
				//	System.out.println(f.getName());	//	print file names
				// check basico, nao verifica realmente o tipo de ficheiro mas serve para fim didatico
				if (f.getName().contains(".jpg" ) || f.getName().contains(".jpeg" ) || f.getName().contains(".png" ) ) return true;
				else return false;
			}
		});
		
		return files;
	}

	public static void main(String[] args) {
		
		ImageVisualizer window = new ImageVisualizer();
		window.open();
	}
}
