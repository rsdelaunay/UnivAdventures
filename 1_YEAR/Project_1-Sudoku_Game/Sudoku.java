import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class Sudoku {
	
	private SudokuBoard sudokuBoard;
	public ColorImage boardImage; // para ver no PandionJ

	//	Construtor
	public Sudoku(String file, double difficulty) {
		sudokuBoard = new SudokuBoard(start(file), difficulty);
		boardImage = sudokuBoard.board;}
	//play
	public void play(int x, int y, int numb) {
		sudokuBoard.playGame(boardImage,x, y, numb);
		boolean ended = sudokuBoard.isOver();
		if (ended == true){
			SudokuAux.gameEnded(boardImage);}}
	//randomPlay
	public void randomPlay (){
		sudokuBoard.makeRandomPlay();
		boolean ended = sudokuBoard.isOver();
		if (ended == true){
			SudokuAux.gameEnded(boardImage);}}
	//undo
	public void undo(){
		sudokuBoard.undo(boardImage);}
	//load
	public void load(String file){
		int[][] matriz_jogo_2 = new int [9][9];
		int[][] matriz_inicial_2 = new int [9][9];
		try 
		{
			Scanner scanner = new Scanner(new File(file));
			while(scanner.hasNext())
			{
				for (int i = 0; i < matriz_jogo_2.length; i++)
				{
					for (int j = 0; j < matriz_jogo_2[i].length; j++)
					{
						matriz_jogo_2[i][j] = Integer.parseInt(scanner.next());
					}	
				}
				for (int i = 0; i < matriz_inicial_2.length; i++)
				{
					for (int j = 0; j < matriz_inicial_2[i].length; j++)
					{
						matriz_inicial_2[i][j] = Integer.parseInt(scanner.next());
					}	
				}
			}
			scanner.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Ficheiro não encontrado! Tente novamente, indicando o nome do ficheiro seguido do respetivo tipo (.sudgame; .txt)");
		}
		SudokuAux.drawSudokuBoard(boardImage,matriz_jogo_2);
		int count = 0;
		for (int i = 0; i < matriz_jogo_2.length; i++)
		{
			for (int j = 0; j < matriz_jogo_2[i].length; j++){
				sudokuBoard.matriz_inicial[i][j] = matriz_inicial_2[i][j];
				sudokuBoard.matriz_jogo[i][j] = matriz_jogo_2[i][j];
			if (matriz_inicial_2[i][j] != matriz_jogo_2[i][j]){
				count++;}
			}
		}
		int [][] matriz_jogadas_2 = new int [count][3];
		count = 0;
		for (int i = 0; i < matriz_jogo_2.length; i++) {
			for (int j = 0; j < matriz_jogo_2[i].length; j++) {
				if (matriz_inicial_2[i][j] != matriz_jogo_2[i][j]){
					matriz_jogadas_2[count][0] = i+1;
					matriz_jogadas_2[count][1] = j+1;
					matriz_jogadas_2[count][2] = matriz_jogo_2[i][j];
					count++;}
			}
		}
		for (int i = 0; i < matriz_jogadas_2.length; i++){
			SudokuAux.invalidLines(boardImage,matriz_jogo_2,matriz_jogadas_2[i][0],matriz_jogadas_2[i][1],matriz_jogadas_2[i][2]);
			SudokuAux.invalidColumns(boardImage,matriz_jogo_2,matriz_jogadas_2[i][0],matriz_jogadas_2[i][1],matriz_jogadas_2[i][2]);
			}
			SudokuAux.invalidSquares(boardImage,matriz_jogo_2);
	}
	//save
	void save(String nome_ficheiro) {
		String caracteres_corretos = "[^a-zA-Z0-9_\\.\\-]";
		String validatedFileName = nome_ficheiro.replaceAll(caracteres_corretos, "_");
		try {
		PrintWriter writer = new PrintWriter(new File(validatedFileName));
		String saveLine = "";
		for (int i = 0; i < 9; i++) {
			saveLine = SudokuAux.matrixLineToString(sudokuBoard.matriz_jogo, i);
			writer.println(saveLine);
		}	
		writer.println("");
		for (int i = 0; i < 9; i++) {
			saveLine = SudokuAux.matrixLineToString(sudokuBoard.matriz_inicial, i);
			writer.println(saveLine);
		}
		writer.close();
		}
		catch(FileNotFoundException e) {
		System.out.println("O ficheiro não pode ser escrito ou criado. Tente novamente.");
		}
	}
	//restart
	public void restart(){
	sudokuBoard.restart(boardImage);}
	
	private int[][] start(String file)
	{	
		int[][] matriz_jogo = new int [9][9];
		try 
		{
			Scanner scanner = new Scanner(new File(file));
			while(scanner.hasNext())
			{
				for (int i = 0; i < matriz_jogo.length; i++)
				{
					for (int j = 0; j < matriz_jogo[i].length; j++)
					{
						matriz_jogo[i][j] = Integer.parseInt(scanner.next());
					}	
				}
			}
			scanner.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Ficheiro não encontrado! Tente novamente, indicando o nome do ficheiro seguido do respetivo tipo (.sudgame; .txt)");
		}
		return matriz_jogo;}
}		