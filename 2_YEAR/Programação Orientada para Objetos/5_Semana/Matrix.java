public class Matrix { //5.c

    private int [] data;
    private int columns;

    public Matrix(int lines, int columns) {
        this.columns = columns;
        data = new int[lines*columns];
    }

    public Matrix(int columns) {
        this(columns,columns);
    }

    public int getLines(){
        return data.length/columns;
    }

    public int getColumns() {
        return columns;
    }

    public int getValue(int line, int column) {
        return data[line*columns + column];
    }

    public void setValue(int line, int column, int value) {
        data[line*columns + column] = value;
    }

    public void multiplyDataValue(int num) {
        for (int i = 0; i < data.length; i++)
                data[i] *=  num;
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
        for (int i = 0; i < columns; i++) {
            // Itera sobre as colunas da matriz
            for (int j = 0; j < columns; j++) {
                sb.append(data[i*columns + j]).append(" "); // Adiciona o valor e um espaço
            }
            sb.append("\n"); // Quebra de linha após cada linha da matriz
        }

        return sb.toString(); // Retorna a matriz formatada como string
    }

}
