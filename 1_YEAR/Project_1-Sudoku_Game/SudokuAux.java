import java.util.Arrays;
public class SudokuAux {
	//Atributos
	static final int MAX_ROWS = 9;
	static final int MAX_COLUMNS = 9;
	static final Color w = Color.WHITE;
	static final Color b = Color.BLACK;
	static final Color r = Color.RED;
	static final int width = 271;
	static final int height = 271;
	static final int x_square = (height-1)/9; 
	static final int y_square = (width-1)/9;
	public static int[] wrong_squares = new int[9];
	public static int[] wrong_columns = new int [MAX_COLUMNS];
	public static int[] wrong_lines = new int [MAX_ROWS];
//1.
	static boolean isValid(int[][] m){
		if (m == null)
			return false;
		if (m.length != 9)
			return false;
		for (int i = 0; i != m.length; i++){
			if (m[i].length != 9)
				return false;
			for (int j = 0; j != m[i].length; j++){
				if (m[i][j] > 9 || m[i][j] < 0)
					return false;}}
		return true;}
//2.
	static int[][] generateMatrix(int[][] m, double perct){
		if (isValid(m) == false)
			throw new IllegalArgumentException("Matriz inválida. Solicita-se remeter matriz válida");
		int[][] new_m = new int [MAX_ROWS][MAX_COLUMNS];
		for (int i = 0; i < m.length; i++){
			for (int j = 0; j < m[i].length;j++){
				new_m[i][j] = m[i][j];}
		}
		long num_cells_to_0 = Math.round(perct*(MAX_ROWS*MAX_COLUMNS));
		for (long count = num_cells_to_0; count > 0; count--){
			int r_l = (int)(Math.random()*9); 
			int r_c = (int)(Math.random()*9);
			if (new_m[r_l][r_c] == 0) 
				count++; 
			else
				new_m[r_l][r_c] = 0;
		}
		return new_m;
	}
//3.
	static String matrixToString(int[][]m){
		int acum = 0;
		for (int i = 0; i < m.length; i++){ 
			acum = m[i].length + acum; 
		}
		int [] v = new int [acum];
		int count = 0;
		for (int i = 0; i < m.length; i++){
			for (int j = 0; j < m[i].length;j++){
				v[count] = m[i][j];
				count++;
			}
		}
		String s = Arrays.toString(v).replaceAll("\\[|\\]|,|\\s", " ");
		return s;
	}
//4.
	static ColorImage obtainSudokuBoard(int [][]m){
		ColorImage boardImage = new ColorImage(width, height, w);
		drawSudokuBoard(boardImage,m);
		return boardImage;
	}
	
	static void drawSudokuBoard(ColorImage board, int[][]m){
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				board.setColor(i,j,w);}}
		for (int i = 0; i < width; i++) { 
			for (int j = 0; j < 2; j++) { 
				board.setColor(Math.round(width/3)-1-j,i,b);
				board.setColor(Math.round(width/3)*2-1-j,i,b); 
				board.setColor(i,Math.round(height/3)-1-j,b);  
				board.setColor(i,Math.round(height/3)*2-1-j,b); 
			}
			for (int k = 1; k < 9; k++) {
				board.setColor(i,y_square*k,b);
				board.setColor(x_square*k,i,b);}
			board.setColor(i,0,b);
			board.setColor(i,height-1,b);
			board.setColor(0,i,b); 
			board.setColor(width-1,i,b);
		}
		String m_elem = new String (matrixToString(m));
		int count = 0; 
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String temp = m_elem.substring(1+count,2+count);
				if (Integer.parseInt(temp)==0){
					board.drawCenteredText(x_square/2+x_square*j, y_square/2+y_square*i, temp, x_square, w);
					count +=3;}
				else{
					board.drawCenteredText(x_square/2+x_square*j, y_square/2+y_square*i, temp, x_square, b);
					count +=3;}
			}
		}
	}
//5.
	static void alterPosition(ColorImage board,int[][] m, int x, int y, int numb){
		String new_elem = String.valueOf(numb);
		String m_elem = new String (matrixToString(m)); 
		int num_elem = y + (x-1)*9; 
		int count = 3*(num_elem-1); 
		String current_elem = m_elem.substring(1+count, 2+count); 
		board.drawCenteredText(y_square/2+y_square*(y-1), x_square/2+x_square*(x-1),  current_elem, x_square, w);
		board.drawCenteredText(y_square/2+y_square*(y-1), x_square/2+x_square*(x-1),  new_elem, x_square, b); 
		m[x-1][y-1]=numb;
	}
//6.
	static void invalidLines(ColorImage board, int[][] m, int x, int y, int numb){
		int repetido = 0;
		int n_linha = x-1;
		for (int i = 0; i < m[n_linha].length; i++) {
			if (m[n_linha][i] == numb){
				repetido++;}}
		if (repetido >= 2){
			for (int r_l = 0; r_l < width ; r_l++) {
				if (n_linha <= 8 && n_linha > 0){
					board.setColor(r_l,y_square*(n_linha+1),r);
					board.setColor(r_l,y_square*(n_linha+1)-1,r);
					board.setColor(r_l,y_square*(n_linha),r);
					board.setColor(r_l,y_square*(n_linha)-1,r);
				}
				if (n_linha == 0){
					board.setColor(r_l,y_square*(n_linha+1),r);
					board.setColor(r_l,y_square*(n_linha+1)+1,r);
					board.setColor(r_l,y_square*(0),r);
					board.setColor(r_l,y_square*(0)+1,r);}
			}
			repetido = 0;
			wrong_lines[n_linha] = x;}
		if (repetido == 1){
			repetido = 0;}}
//7.	
	static void invalidColumns(ColorImage board, int[][] m, int x, int y, int numb){	
		int repetido = 0;
		int n_coluna = y-1;
		for (int i = 0; i < m.length; i++) {
			if (m[i][n_coluna] == numb){
				repetido++;}}
		if (repetido == 1){
			repetido = 0;}
		if (repetido >=2){
			if (n_coluna <= 8 && n_coluna >= 1){
				for (int r_c = 0; r_c < height ; r_c++) {
					board.setColor(x_square*(n_coluna+1),r_c,r);
					board.setColor(x_square*(n_coluna+1)-1,r_c,r);
					board.setColor(x_square*(n_coluna),r_c,r);
					board.setColor(x_square*(n_coluna)-1,r_c,r);}}
			if (n_coluna == 0){
				for (int r_c = 0; r_c < height ; r_c++) {
					board.setColor(x_square*(n_coluna+1),r_c,r);
					board.setColor(x_square*(n_coluna+1)+1,r_c,r);
					board.setColor(x_square*(0),r_c,r);
					board.setColor(x_square*(0)+1,r_c,r);}}
			repetido = 0;
			wrong_columns[n_coluna] = y;}}
//8.
static void invalidSquares(ColorImage board,int[][] m){
	int count = 0;
	for (int i = 0; i < m.length; i++) {
		for (int j = 0; j < m[i].length; j++) {
				//1.º Quadrado
		if (i < 3 && j < 3){
			for (int r_l = 0; r_l < 3; r_l++){
				for (int r_c = 0; r_c < 3; r_c++){
				if (m[i][j] != 0 && m[i][j] == m[r_l][r_c]){
					count++;}}}
					if (count >= 2){
					for (int l = 0; l < Math.round(height/3); l++) {
						for (int k = 0; k < 2; k++){
						board.setColor(Math.round(width/3)-1-k,l,r); 
						board.setColor(l,Math.round(height/3)-1-k,r); 
						board.setColor(l,0+k,r); 
						board.setColor(0+k,l,r);}} 			
						wrong_squares[0] = 1;
						count = 0;}
				if (count == 1)
						count = 0;}
				//2.º Quadrado
		if (j >= 3 && j < 6 && i < 3){
			for (int r_l = 0; r_l < 3; r_l++){
				for (int r_c = 3; r_c < 6; r_c++){
				if (m[i][j] != 0 && m[i][j] == m[r_l][r_c]){
					count++;}}}
					if (count >= 2){
					for (int l = Math.round(height/3)-1; l < Math.round(height/3)*2; l++) { 
						for (int k = 0; k < 2; k++){
						board.setColor(l,0+k,r);
						board.setColor(l,(Math.round(height/3)-1)+k,r);
						}
					}
					for (int l = 0; l < Math.round(height/3); l++) {
						for (int k = 0; k < 2; k++){
						board.setColor(Math.round(width/3)-1+k,l,r);
						board.setColor((Math.round(width/3)-1)*2+k,l,r); 
						}
					}
					wrong_squares[1] = 2;
					count = 0;}
					if (count == 1)
						count = 0;}
				// 3.º Quadrado
		if (j >= 6 && j < 9 && i < 3){
			for (int r_l = 0; r_l < 3; r_l++){
				for (int r_c = 6; r_c < 9; r_c++){
				if (m[i][j] != 0 && m[i][j] == m[r_l][r_c]){
					count++;}}}
					if (count >= 2){
					for (int l = (Math.round((height-1)/3))*2; l < height; l++) { 
						for (int k = 0; k < 2; k++){
						board.setColor(l,0+k,r);
						board.setColor(l,(Math.round(height/3)-1)+k,r);
						}
					}
					for (int l = 0; l < Math.round(height/3); l++) {
						for (int k = 0; k < 2; k++){
						board.setColor((Math.round(height/3)-1)*2+k,l,r); 
						board.setColor(width-1-k,l,r); 
						}
					}
					wrong_squares[2] = 3;
					count = 0;}
					if (count == 1)
						count = 0;}
				//4.º Quadrado
		if (i >= 3 && i < 6 && j < 3){
			for (int r_l = 3; r_l < 6; r_l++){
				for (int r_c = 0; r_c < 3; r_c++){
					if (m[i][j] != 0 && m[i][j] == m[r_l][r_c]){
					count++;}}}
					if (count >= 2){
					for (int l = Math.round(height/3)-1; l < Math.round(height/3)*2; l++) { 
						for (int k = 0; k < 2; k++){
						board.setColor(Math.round(width/3)-1-k,l,r); 
						board.setColor(0+k,l,r); 
						}
					}
					for (int l = 0; l < Math.round(height/3); l++) {
						for (int k = 0; k < 2; k++){
						board.setColor(l,Math.round(height/3)-1+k,r); 
						board.setColor(l,(Math.round(height/3)-1)*2+k,r); 
						}
					}
					wrong_squares[3] = 4;
					count = 0;}
					if (count == 1)
						count = 0;}
				//5.º Quadrado
		if (i >= 3 && i < 6 && j >= 3 && j < 6){
			for (int r_l = 3; r_l < 6; r_l++){
				for (int r_c = 3; r_c < 6; r_c++){
					if (m[i][j] != 0 && m[i][j] == m[r_l][r_c]){
					count++;}}}
					if (count >= 2){
					for (int l = Math.round(height/3)-1; l < Math.round(height/3)*2; l++) { 
						for (int k = 0; k < 2; k++){
						board.setColor((Math.round(width/3)-1)*2+k,l,r); 
						board.setColor(l,(Math.round(height/3)-1)*2+k,r);  
						board.setColor(l,(Math.round(height/3)-1)+k,r);
						board.setColor((Math.round(height/3)-1)+k,l,r);
						}
					}
					wrong_squares[4] = 5;
					count = 0;}
					if (count == 1)
						count = 0;}
				//6.º Quadrado
		if (i >= 3 && i < 6 && j >= 6 && j < 9){
			for (int r_l = 3; r_l < 6; r_l++){
				for (int r_c = 6; r_c < 9; r_c++){
					if (m[i][j] != 0 && m[i][j] == m[r_l][r_c]){
					count++;}}}
					if (count >= 2){
					for (int l = (Math.round((width-1)/3))*2; l < width; l++) { 
						for (int k = 0; k < 2; k++){
						board.setColor(l,(Math.round(height/3)-1)+k,r); //linha horizontal superior
						board.setColor(l,(Math.round(height/3)-1)*2+k,r); //linha horizontal inf
						}
					}
					for (int l = Math.round(height/3)-1; l < Math.round(height/3)*2; l++) {
						for (int k = 0; k < 2; k++){
						board.setColor((Math.round(height/3)-1)*2+k,l,r); //linha vertical esq
						board.setColor(width-1-k,l,r); //linha vertical dir 					}
						}
					}
					wrong_squares[5] = 6;
					count = 0;}
					if (count == 1)
						count = 0;}
				//7.º Quadrado
		if (i >= 6 && j < 3){
			for (int r_l = 6; r_l < 9; r_l++){
				for (int r_c = 0; r_c < 3; r_c++){
					if (m[i][j] != 0 && m[i][j] == m[r_l][r_c]){
					count++;}}}
					if (count >= 2){
					for (int l = (Math.round((height-1)/3))*2; l < height; l++) { 
						for (int k = 0; k < 2; k++){
						board.setColor(Math.round(width/3)-1-k,l,r); //vertical dir
						board.setColor(0+k,l,r); //vertical esq
						}
					}
					for (int l = 0; l < Math.round(height/3); l++) {
						for (int k = 0; k < 2; k++){
						board.setColor(l,(Math.round(height/3)-1)*2+k,r); //horizontal sup
						board.setColor(l,height-1-k,r); //horizontal inf
						}
					}
					wrong_squares[6] = 7;
					count = 0;}
					if (count == 1)
						count = 0;}
				//8.º Quadrado
		if (i >= 6 && j >= 3 && j < 6){
			for (int r_l = 6; r_l < 9; r_l++){
				for (int r_c = 3; r_c < 6; r_c++){
					if (m[i][j] != 0 && m[i][j] == m[r_l][r_c]){
					count++;}}}
					if (count >= 2){
					for (int l = Math.round(height/3)-1; l < Math.round(height/3)*2; l++) { 
						for (int k = 0; k < 2; k++){
						board.setColor(l,(Math.round(height/3))*2+k,r); //linha horizontal superior
						board.setColor(l,width-1-k,r); //linha horizontal inf
						}
					}
					for (int l = (Math.round((height-1)/3))*2; l < height; l++){
						for (int k = 0; k < 2; k++){
						board.setColor(Math.round(width/3)-1+k,l,r); //linha vertical esq
						board.setColor((Math.round(width/3)-1)*2+k,l,r); //linha vertical dir 
						}
					}
					wrong_squares[7] = 8;
					count = 0;}
					if (count == 1)
						count = 0;}
				//9.º Quadrado
		if (i >= 6 && j >= 6){
			for (int r_l = 6; r_l < 9; r_l++){
				for (int r_c = 6; r_c < 9; r_c++){
					if (m[i][j] != 0 && m[i][j] == m[r_l][r_c]){
					count++;}}}
					if (count >= 2){
					for (int l = (Math.round((height-1)/3))*2; l < height; l++) { 
						for (int k = 0; k < 2; k++){
						board.setColor(width-1-k,l,r); //linha vertical dir
						board.setColor(l,height-1-k,r); //linha horizontal inf
						board.setColor(l,(Math.round(height/3)-1)*2+k,r); //linha vertical esq
						board.setColor((Math.round(height/3)-1)*2+k,l,r); //linha horizontal sup
						}
					}
					wrong_squares[8] = 9;
					count = 0;}
					if (count == 1)
						count = 0;}}}} 
//Funções auxiliares criadas pelo autor	
	static String matrixLineToString(int[][] m, int line)
	{
		String str = "";
		for (int i = 0; i < m[line].length; i++) {

			if(i != 0)
			{
				str += " ";
			}
			str += String.valueOf(m[line][i]);
		}
		return str;
	}
//Interligada com 8. SudokuBoard
	static void gameEnded(ColorImage board){
		for (int i = 1; i < width-1; i++){
			for (int j = 1; j < height-1; j++) {
				board.setColor(i,j,w);}}
		String temp = "Sudoku terminado!";
		board.drawCenteredText((width-1)/2, (height-1)/2, temp, 30, b);}		
//Interligada com 6. SudokuBoard	
	static boolean iswrongSquare(int numb){
		if (wrong_squares[numb-1] == numb){
			return true;}
		return false;}
//Interligadas com 7. SudokuBoard	
	static boolean iswrongLine(int numb){
		if (wrong_lines[numb-1] == numb){
			return true;}
		return false;}
		
	static boolean iswrongColumn(int numb){
		if (wrong_columns[numb-1] == numb){
			return true;}
		return false;}
//Interligada com 6. SudokuBoard		
	static int[] wrongSquares(){
		int count = 0;
		for (int i = 0; i < wrong_squares.length; i++) {
			if (wrong_squares[i] != 0){
				count++;}}
		int[] wrong_squares_final = new int [count];
		count = 0;
		for (int i = 0; i < wrong_squares.length; i++) {
			if (wrong_squares[i] != 0){
				wrong_squares_final[count] = wrong_lines[i];
				count++;}}
		return wrong_squares_final;}
//Interligadas com 7. SudokuBoard	
	static int[] wrongLines(){
		int count = 0;
		for (int i = 0; i < wrong_lines.length; i++) {
			if (wrong_lines[i] != 0){
				count++;}}
		int[] wrong_lines_final = new int [count];
		count = 0;
		for (int i = 0; i < wrong_lines.length; i++) {
			if (wrong_lines[i] != 0){
				wrong_lines_final[count] = wrong_lines[i];
				count++;}}
		return wrong_lines_final;}
	
	static int[] wrongColumns(){
		int count = 0;
		for (int i = 0; i < wrong_columns.length; i++) {
			if (wrong_columns[i] != 0){
				count++;}}
		int[] wrong_columns_final = new int [count];
		count = 0;
		for (int i = 0; i < wrong_columns.length; i++) {
			if (wrong_columns[i] != 0){
				wrong_columns_final[count] = wrong_columns[i];
				count++;}}
		return wrong_columns_final;}
}