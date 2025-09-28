package pt.iscte.poo.game;

import objects.*;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;

public class GameEngine implements Observer {

	private static GameEngine INSTANCE;
	private int lastTickProcessed = 0;
	private List<Room> rooms = new ArrayList<>();
	private Room currentRoom;

	public static GameEngine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GameEngine();
		return INSTANCE;
	}

	private GameEngine() {
		loadRooms("rooms/"); // Carrega todas as salas da pasta "rooms/"
		if (!rooms.isEmpty()) {
			currentRoom = rooms.get(0);
			currentRoom.drawRoom();
		}
	}

	@Override
	public void update(Observed source) {
		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
			if (Direction.isDirection(k)) {
				currentRoom.moveManel(Direction.directionFor(k));}
			if(ImageGUI.getInstance().keyPressed() == KeyEvent.VK_B) {
						currentRoom.getJumpMan().armBomb();
			}
		}
		// atualizar estado do jogo a cada tick
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();}
	}


	private void processTick() {
		for (GameObject obj : currentRoom.getGameObjects()) { //correr lista objetos para identificar tickables
			if (obj instanceof Tickable) {
				((Tickable) obj).updateTick();
			}
		}
		currentRoom.processAdditions();
		currentRoom.processRemovals();
		ImageGUI gui = ImageGUI.getInstance();
		gui.update();
		System.out.println("Tic Tac : " + lastTickProcessed);
		lastTickProcessed++;
		gui.setStatusMessage("Good luck!         Lives: "+ currentRoom.getJumpMan().getLives() + "     Health: " + currentRoom.getJumpMan().getHealth() + "     Damage: " + currentRoom.getJumpMan().getDamage() + "     Bombs: " + currentRoom.getJumpMan().numBombs());
	}

	private void loadRooms(String directoryPath) {
		File directory = new File(directoryPath);
		if (directory.isDirectory()) {
			File[] files = directory.listFiles((dir, name) -> name.matches("room\\d+\\.txt"));
			if (files != null) {
				for (File file : files) {
					rooms.add(new Room(file.getPath()));
				}
			} else { //nenhuma sala encontrada
				promptUserForDirectory();
			}
		} else { //caminho invalido
			System.err.println("Caminho inválido: " + directoryPath);
			promptUserForDirectory();
		}
	}

	private void promptUserForDirectory() {
		Scanner scanner = new Scanner(System.in);
		boolean directoryLoaded = false;
		while (!directoryLoaded) {
			System.out.print("Insira o caminho de um diretório válido com ficheiros .txt para configurar as salas: ");
			String userInput = scanner.nextLine();
			File directory = new File(userInput);
			if (directory.isDirectory()) {
				File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));
				if (files != null && files.length > 0) {
					for (File file : files) {
						rooms.add(new Room(file.getPath()));
					}
					System.out.println("Salas carregadas do diretório: " + userInput);
					directoryLoaded = true;
				} else {
					System.err.println("O diretório não contém ficheiros .txt. Tente novamente.");
				}
			} else {
				System.err.println("O caminho inserido não é um diretório válido. Tente novamente.");
			}
		}
	}

	public boolean advanceToNextRoom() {
		int currentRoomIndex = rooms.indexOf(currentRoom);
		if (currentRoomIndex + 1 < rooms.size()) {
			currentRoom = rooms.get(currentRoomIndex + 1);
			ImageGUI.getInstance().clearImages();
			currentRoom.draw();
			return true;
		}
		return false;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public int getCurrentRoomIndex(){
		return rooms.indexOf(currentRoom);
	}

	public int getLastTickProcessed(){
		return lastTickProcessed;
	}



}

