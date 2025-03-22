//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Matrix a = new Matrix(3,3);
        Matrix b = new Matrix(3,3);


        a.setValue(1,1,3);
        b.setValue(1,1,3);

        System.out.println(sumAndScale(a,b,2));

    }

    static Matrix sumAndScale(Matrix a, Matrix b, int scalar){
        if(!a.lengthEqual(b)) return null;

        Matrix c = new Matrix(a.getLines(),a.getColumns());
        for(int i = 0; i < a.getLines();i++) {
            for (int j = 0; j < a.getColumns(); j++) {
                c.setValue(i, j, (a.getValue(i,j)+ b.getValue(i,j))*scalar);
            }
        }
        return c;
    }

}