public class Addition implements Operator {
    @Override
    public String getSymbol() {
        return "+";
    }

    @Override
    public int calculate(int a, int b) {
        return a + b;
    }
}
