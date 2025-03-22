public class Power implements Operator{
    @Override
    public String getSymbol() {
        return "^";
    }

    @Override
    public int calculate(int a, int b) {
        return (int) Math.pow(a, b);
    }
}
