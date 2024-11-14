class SudokuBoard {
	//Atributos
	int[][] matriz_jogo;
	int[][] matriz_inicial;
	int[][] matriz_jogadas;
	int i_jogada = 0;
	ColorImage board;
	
	//Construtor
	SudokuBoard(int[][] m, double difficulty){
		matriz_jogo = m;
		matriz_jogo = SudokuAux.generateMatrix(matriz_jogo,difficulty);
		matriz_inicial = copyMatrice(matriz_jogo);
		matriz_jogadas = matriceJogadas(matriz_inicial);
		createGame(matriz_jogo);}
	
	static int[][] copyMatrice(int[][] m){
		int[][] m2 = new int [9][9];
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				m2[i][j] = m[i][j];}}
		return m2;}

	static int[][] matriceJogadas(int[][] m){
		int count = 0;
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				if (m[i][j] == 0){
					count++;}}}
		int[][] m2 = new int [count][3]; 
		return m2;}
//1.	
	ColorImage createGame(int[][]m){
		if (SudokuAux.isValid(m) == true){
		board = SudokuAux.obtainSudokuBoard(m);
		return board;
		}
		else{
			throw new IllegalArgumentException("Matriz inválida!");
		}}
//2.
	int valueIn(int[][]m, int x, int y){
		return m[x][y];}
//3.
	void playGame(ColorImage board, int x, int y, int numb){
		if (numb > 9 || numb < 0){
			throw new IllegalArgumentException("Número inválido.");}
		if (x > 9 || x < 1){
			throw new IllegalArgumentException("Coordenada x inválida.");}
		if (y > 9 || y < 1){
			throw new IllegalArgumentException("Coordenada y inválida.");}
		if (valueIn(matriz_jogo, x-1, y-1) == 0){
			SudokuAux.alterPosition(board,matriz_jogo,x,y,numb);
			SudokuAux.invalidLines(board,matriz_jogo,x,y,numb);
			SudokuAux.invalidColumns(board,matriz_jogo,x,y,numb);
			SudokuAux.invalidSquares(board,matriz_jogo);
			matriz_jogadas[i_jogada][0] = x;
			matriz_jogadas[i_jogada][1] = y;
			matriz_jogadas[i_jogada][2] = numb;
			i_jogada++;}
		else if (valueIn(matriz_jogo, x-1, y-1) != 0){
			throw new IllegalStateException("Não é possível efetuar uma jogada nas coordenadas indicadas.");
			}}
//4.
	void makeRandomPlay(){
		int count = 0;
		for (int i = 0; i < matriz_jogo.length; i++) {
			for (int j = 0; j < matriz_jogo[i].length; j++) {
				if (matriz_jogo[i][j] == 0){
					count++;}}}
		if (count == 0){
			throw new IllegalStateException("Não existem mais posições por jogar.");}
		int[][] m2 = new int [count][2]; //matriz com linhas e colunas por jogar
		count = 0;
		for (int i = 0; i < matriz_jogo.length; i++) {
			for (int j = 0; j < matriz_jogo[i].length; j++) {
				if (matriz_jogo[i][j] == 0){
					m2[count][0] = i+1; //guarda linha
					m2[count][1] = j+1; //guarda coluna
					count++;}}}
		int count_random = (int) Math.floor(Math.random()*(m2.length)); //jogada random entre as disponiveis
		int random_value = (int)(Math.floor(Math.random()*10)); //random value entre 0 e 9]
		while (random_value == 0){
			random_value = (int)(Math.floor(Math.random()*10));}
		//1.º quadrado
		if (m2[count_random][0] <= 3 && m2[count_random][1] <= 3){
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					while (random_value == matriz_jogo[i][j] || random_value == 0){
						random_value = (int)(Math.floor(Math.random()*10));}//altera o valor caso já exista no 1.º quadrado
				}
			}
			SudokuAux.alterPosition(board,matriz_jogo,m2[count_random][0],m2[count_random][1],random_value);
		}
		//2.º quadrado
		else if (m2[count_random][0] <= 3 && m2[count_random][1] > 3 && m2[count_random][1] <= 6){
			for (int i = 0; i < 4; i++) {
				for (int j = 3; j < 7; j++) {
					while (random_value == matriz_jogo[i][j] || random_value == 0){
						random_value = (int)(Math.floor(Math.random()*10));}//altera o valor caso já exista no 1.º quadrado
				}
			}
			SudokuAux.alterPosition(board,matriz_jogo,m2[count_random][0],m2[count_random][1],random_value);
		}
		//3.º quadrado
		else if (m2[count_random][0] <= 3 && m2[count_random][1] > 6 && m2[count_random][1] <= 9){
			for (int i = 0; i < 4; i++) {
				for (int j = 6; j < 9; j++) {
					while (random_value == matriz_jogo[i][j] || random_value == 0){
						random_value = (int)(Math.floor(Math.random()*10));}//altera o valor caso já exista no 1.º quadrado
				}
			}
			SudokuAux.alterPosition(board,matriz_jogo,m2[count_random][0],m2[count_random][1],random_value);
		}
		//4.º quadrado
		else if (m2[count_random][0] > 3 && m2[count_random][0] <= 6 && m2[count_random][1] <= 3){
			for (int i = 3; i < 6; i++) {
				for (int j = 0; j < 4; j++) {
					while (random_value == matriz_jogo[i][j] || random_value == 0){
						random_value = (int)(Math.floor(Math.random()*10));}//altera o valor caso já exista no 1.º quadrado
				}
			}
			SudokuAux.alterPosition(board,matriz_jogo,m2[count_random][0],m2[count_random][1],random_value);
		}
		//5.º quadrado
		else if (m2[count_random][0] > 3 && m2[count_random][0] <= 6 && m2[count_random][1] > 3 && m2[count_random][1] <= 6){
			for (int i = 3; i < 6; i++) {
				for (int j = 3; j < 6; j++) {
					while (random_value == matriz_jogo[i][j] || random_value == 0){
						random_value = (int)(Math.floor(Math.random()*10));}//altera o valor caso já exista no 1.º quadrado
				}
			}
			SudokuAux.alterPosition(board,matriz_jogo,m2[count_random][0],m2[count_random][1],random_value);
		}
		//6.º quadrado
		else if (m2[count_random][0] <= 9 && m2[count_random][0] > 6 && m2[count_random][1] > 6){
			for (int i = 3; i < 6; i++) {
				for (int j = 6; j < 9; j++) {
					while (random_value == matriz_jogo[i][j] || random_value == 0){
						random_value = (int)(Math.floor(Math.random()*10));}//altera o valor caso já exista no 1.º quadrado
				}
			}
			SudokuAux.alterPosition(board,matriz_jogo,m2[count_random][0],m2[count_random][1],random_value);
		}
		//7.º quadrado
		else if (m2[count_random][0] > 6 && m2[count_random][0] <= 3){
			for (int i = 6; i < 9; i++) {
				for (int j = 0; j < 3; j++) {
					while (random_value == matriz_jogo[i][j] || random_value == 0){
						random_value = (int)(Math.floor(Math.random()*10));}//altera o valor caso já exista no 1.º quadrado
				}
			}
			SudokuAux.alterPosition(board,matriz_jogo,m2[count_random][0],m2[count_random][1],random_value);
		}
		//8.º quadrado
		else if (m2[count_random][0] > 6 && m2[count_random][0] > 3 && m2[count_random][0] <= 6){
			for (int i = 6; i < 9; i++) {
				for (int j = 3; j < 6; j++) {
					while (random_value == matriz_jogo[i][j] || random_value == 0){
						random_value = (int)(Math.floor(Math.random()*10));}//altera o valor caso já exista no 1.º quadrado
				}
			}
			SudokuAux.alterPosition(board,matriz_jogo,m2[count_random][0],m2[count_random][1],random_value);
		}
		//9.º quadrado
		else if (m2[count_random][0] > 6 && m2[count_random][0] > 6){
			for (int i = 6; i < 9; i++) {
				for (int j = 6; j < 9; j++) {
					while (random_value == matriz_jogo[i][j] || random_value == 0){
						random_value = (int)(Math.floor(Math.random()*10));}//altera o valor caso já exista no 1.º quadrado
				}
			}
			SudokuAux.alterPosition(board,matriz_jogo,m2[count_random][0],m2[count_random][1],random_value);
		}
		SudokuAux.invalidLines(board,matriz_jogo,m2[count_random][0],m2[count_random][1],random_value);
		SudokuAux.invalidColumns(board,matriz_jogo,m2[count_random][0],m2[count_random][1],random_value);
		matriz_jogadas[i_jogada][0] = m2[count_random][0];
		matriz_jogadas[i_jogada][1] = m2[count_random][1];
		matriz_jogadas[i_jogada][2] = random_value;
		i_jogada++;
		SudokuAux.invalidSquares(board,matriz_jogo);
	}
//5.
	void restart(ColorImage board){
		for (int i = 0; i < matriz_jogadas.length; i++){
			for (int j = 0; j < matriz_jogadas[i].length; j++) {
				matriz_jogadas[i][j] = 0;
			}}
		for (int i = 0; i < matriz_jogo.length; i++){
			for (int j = 0; j < matriz_jogo[i].length; j++) {
				matriz_jogo[i][j] = matriz_inicial[i][j];
			}}
		SudokuAux.drawSudokuBoard(board,matriz_inicial);
	}
//6.
	 int[] invalidSquares(ColorImage board, int[][] m){
		SudokuAux.invalidSquares(board,m);
		int[] wrong_squares = SudokuAux.wrongSquares();
		return wrong_squares;
	}
//7.
	 int[] invalidLines(ColorImage board, int[][] m){
		int[] wrong_lines = SudokuAux.wrongLines();
		return wrong_lines;
	}
	
	 int[] invalidColumns(ColorImage board, int[][] m){
		int[] wrong_columns = SudokuAux.wrongColumns();
		return wrong_columns;
	}
//8.	
	boolean isOver(){
		for (int i = 0; i < matriz_jogo.length; i++) {
			for (int j = 0; j < matriz_jogo[i].length; j++) {
				if (matriz_jogo[i][j] == 0){
					return false;}}}
		for (int i = 1; i <= 9; i++){
			if (SudokuAux.iswrongSquare(i) == true){
				return false;}
			if (SudokuAux.iswrongLine(i) == true){
				return false;}
			if (SudokuAux.iswrongColumn(i) == true){
				return false;}}
		return true;
		}	
//9.
	void undo(ColorImage board){
		if (i_jogada == 0){
			throw new IllegalStateException("Sudoku Board encontra-se no estado inicial.");}
			i_jogada--;
			SudokuAux.alterPosition(board,matriz_jogo,matriz_jogadas[i_jogada][0],matriz_jogadas[i_jogada][1],0);
			matriz_jogadas[i_jogada][0] = 0;
			matriz_jogadas[i_jogada][1] = 0;
			matriz_jogadas[i_jogada][2] = 0;
			SudokuAux.drawSudokuBoard(board,matriz_jogo);
			for (int i = i_jogada-1; i >= 0; i--){
			SudokuAux.invalidLines(board,matriz_jogo,matriz_jogadas[i][0],matriz_jogadas[i][1],matriz_jogadas[i][2]);
			SudokuAux.invalidColumns(board,matriz_jogo,matriz_jogadas[i][0],matriz_jogadas[i][1],matriz_jogadas[i][2]);
			}
			SudokuAux.invalidSquares(board,matriz_jogo);
	}	
}