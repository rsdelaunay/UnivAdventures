public class MatrixCopy {//5.a/b
    //public class Matrix {

    private int[][] matrix;

    public MatrixCopy(int lines, int columns) {
        matrix = new int[lines][columns]; // inicializar matriz a zeros
    }

    public MatrixCopy(int matrixSquare) {
        this(matrixSquare, matrixSquare);
    }

    public int getColumns() {
        return matrix[0].length;
    }

    public int getLines() {
        return matrix.length;
    }

    public int getValue(int line, int column) {
        return matrix[line][column];
    }

    public void setValue(int line, int column, int value) {
        matrix[line][column] = value;
    }

    public void multiplyArrayValue(int num) {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                matrix[i][j] *= num;
    }

    public boolean lengthEqual(Matrix m) {
        if (m.getColumns() != getColumns())
            return false;
        if (m.getLines() != getLines())
            return false;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Itera sobre as linhas da matriz
        for (int i = 0; i < matrix.length; i++) {
            // Itera sobre as colunas da matriz
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j]).append(" "); // Adiciona o valor e um espaço
            }
            sb.append("\n"); // Quebra de linha após cada linha da matriz
        }

        return sb.toString(); // Retorna a matriz formatada como string
    }

    //}

}
