package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;

public class Main {
	public static void main(String[] args) {
		ImageGUI gui = ImageGUI.getInstance();
		GameEngine engine = GameEngine.getInstance();
		gui.registerObserver(engine);
		gui.go();
	}
}