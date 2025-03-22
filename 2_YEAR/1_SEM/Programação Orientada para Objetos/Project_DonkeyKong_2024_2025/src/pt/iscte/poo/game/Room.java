package pt.iscte.poo.game;

import objects.*;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Room {
	private static final int roomWidth = 10;
	private static final int roomHeight = 10;
	private String nextRoomFile; // Nome do próximo ficheiro de sala
	private JumpMan jumpMan;
	private Gorilla gorilla;
	private Bat bat;
	private Key key;
	private List<GameObject> gameObjects = new ArrayList<>();
	private boolean isDrawn = false;
	private List<GameObject> objectsToRemove = new ArrayList<>();
	private List<GameObject> pendingAdditions = new ArrayList<>();


	public Room(String fileName) {
		loadRoom(fileName); // Carrega e desenha a sala inicial
	}

	public void drawRoom() {
		if (!isDrawn) {
			ImageGUI.getInstance().clearImages();
			for (GameObject obj : gameObjects) {
				ImageGUI.getInstance().addImage(obj);}
			ImageGUI.getInstance().update();
			isDrawn = true;
		}
	}

	public void loadRoom(String fileName) {
		while (fileName == null || fileName.isEmpty() || !new File(fileName).exists()) {
			System.err.println("Ficheiro inválido ou não encontrado: " + fileName);
			fileName = promptUserForFile();
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String firstLine = null;

			if (!fileName.endsWith("room2.txt")) {
				firstLine = reader.readLine();
				if (firstLine == null || !firstLine.contains(";")) {
					throw new IllegalArgumentException("Formato inválido na primeira linha do ficheiro: " + fileName);
				}

				String[] parts = firstLine.split(";");
				if (parts.length < 2) {
					throw new IllegalArgumentException("Faltam informações na primeira linha do ficheiro: " + fileName);
				}
				nextRoomFile = parts[1];
			}
			ImageGUI.getInstance().clearImages();
			gameObjects.clear();
			for (int y = 0; y < roomHeight; y++) {
				for (int x = 0; x < roomWidth; x++) {
					Point2D position = new Point2D(x, y);
					ImageGUI.getInstance().addImage(new Floor(position)); // Adiciona o chão
				}
			}

			String line;
			int y = 0; // Linha da grelha
			boolean missingLine = false; //para testar faltar linha

			while ((line = reader.readLine()) != null) {
				if (line.length() < roomWidth) { //linha incompleta
					line = String.format("%-" + roomWidth + "s", line).replace(' ', ' '); //substituir com espaço para depois transformar em chão.
				}

				for (int x = 0; x < line.length(); x++) {
					char symbol = line.charAt(x);
					Point2D position = new Point2D(x, y);

					switch (symbol) {
						case 'W': // Wall
							Wall wall = new Wall(position);
							ImageGUI.getInstance().addImage(wall);
							gameObjects.add(wall);
							break;
						case 'w': // Wall with trap
							Wall wall_t = new Wall(position, true); //com trap
							ImageGUI.getInstance().addImage(wall_t);
							gameObjects.add(wall_t);
							break;
						case 'S': // Stairs
							Stairs stairs = new Stairs(position);
							ImageGUI.getInstance().addImage(stairs);
							gameObjects.add(stairs);
							break;
						case 'm': // meat
							Meat meat = new Meat(position);
							ImageGUI.getInstance().addImage(meat);
							gameObjects.add(new Floor(position));
							gameObjects.add(meat);
							break;
						case 'P': // Princesa
							Princess princesa = new Princess(position);
							ImageGUI.getInstance().addImage(princesa);
							gameObjects.add(new Floor(position));
							gameObjects.add(princesa);
							break;
						case 'G': // Gorilla
							gorilla = new Gorilla(position);
							ImageGUI.getInstance().addImage(gorilla);
							gameObjects.add(new Floor(position));
							gameObjects.add(gorilla);
							break;
						case 'b': // Bat
							bat = new Bat(position);
							ImageGUI.getInstance().addImage(bat);
							gameObjects.add(new Floor(position));
							gameObjects.add(bat);
							break;
						case 'H': // Hero (Manel)
							jumpMan = new JumpMan(position);
							ImageGUI.getInstance().addImage(jumpMan);
							gameObjects.add(new Floor(position));
							gameObjects.add(jumpMan);
							break;
						case '0': // Porta para o próximo nível
							Door door = new Door(position);
							ImageGUI.getInstance().addImage(door); // Cria a porta fechada
							gameObjects.add(door);
							break;
						case 't': // Trap
							Trap trap = new Trap(position);
							ImageGUI.getInstance().addImage(trap); // Cria uma armadilha
							gameObjects.add(trap);
							break;
						case 's': // Sword
							Sword sword = new Sword(position);
							ImageGUI.getInstance().addImage(sword); // Cria uma espada
							gameObjects.add(new Floor(position));
							gameObjects.add(sword);
							break;
						case 'k': // Key
							key = new Key(position);
							ImageGUI.getInstance().addImage(key);
							gameObjects.add(new Floor(position));
							gameObjects.add(key);
							break;
						case 'B': // Bomb
							Bomb bomb = new Bomb(position, false);
							ImageGUI.getInstance().addImage(bomb); // Cria uma espada
							gameObjects.add(new Floor(position));
							gameObjects.add(bomb);
							break;
						case ' ': // Floor
							Floor floor = new Floor(position);
							gameObjects.add(floor);
							break;
						default:
							System.err.println("Caracter desconhecido '" + symbol + "' encontrado na linha " + y + ", coluna " + x + ". Preencher com chão.");
							Floor unknownFloor = new Floor(position);
							gameObjects.add(unknownFloor);
							ImageGUI.getInstance().addImage(unknownFloor);
							break;
					}
				}
				y++;
			}

			if (y < roomHeight) {
				missingLine = true;
			}

			if (missingLine) {
				System.err.println("Erro: O ficheiro está incompleto. Faltam linhas na definição da sala: " + fileName);
				ImageGUI.getInstance().dispose();
				System.exit(1);
			}

			ImageGUI.getInstance().update();
		} catch (IOException e) {
			System.err.println("Erro ao carregar o ficheiro: " + fileName);
			e.printStackTrace();
			ImageGUI.getInstance().dispose();
			System.exit(1);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			ImageGUI.getInstance().dispose();
			System.exit(1);
		}
	}


	private String promptUserForFile() {
		Scanner scanner = new Scanner(System.in);
		String fileName = null;

		while (fileName == null || fileName.isEmpty() || !new File(fileName).exists()) {
			System.out.print("Insira o nome de um ficheiro válido (.txt) para carregar o nível: ");
			fileName = scanner.nextLine();

			if (!new File(fileName).exists()) {
				System.err.println("Ficheiro não encontrado ou inválido. Tente novamente.");
			}
		}
		return fileName;
	}

	public void moveManel(Direction direction) {
		if (jumpMan !=null){
			jumpMan.move(direction);
			ImageGUI.getInstance().update();
		}
	}

	public void addObject(GameObject object) {
		pendingAdditions.add(object);
		ImageGUI.getInstance().addImage(object);
	}


	public List<GameObject> getObjectsInPosition(Point2D position) {
		List<GameObject> objectsInPosition = new ArrayList<>();

		for(GameObject go : gameObjects){
			if(position.equals(go.getPosition())) {
				objectsInPosition.add(go);
			}
		}
		return objectsInPosition;
	}

	public void draw() {
		for (GameObject go : gameObjects) {
			ImageGUI.getInstance().addImage(go);
		}
	}

	public List<GameObject> getGameObjects(){
		return this.gameObjects;
	}

	public boolean isInsideRoom(Point2D position) { //verificar se posicao esta dentro dos limites da room
			return position.getX() >= 0 && position.getX() <= roomWidth &&
					position.getY() >= 0 && position.getY() <= roomHeight;
	}

	public void addToRemoveQueue(GameObject obj) {
		objectsToRemove.add(obj);
	}

	public void processRemovals() { //limpar objetos a remover que foram adicionados ao array removals
		//nomeadamente bananas que saiam da janela do programa
		for (GameObject obj : objectsToRemove) {
			gameObjects.remove(obj);
			ImageGUI.getInstance().removeImage(obj);
		}
		objectsToRemove.clear();
	}


	public void processAdditions() {
		gameObjects.addAll(pendingAdditions);
		pendingAdditions.clear();
	}

	public JumpMan getJumpMan() {
		return jumpMan;
	}

}