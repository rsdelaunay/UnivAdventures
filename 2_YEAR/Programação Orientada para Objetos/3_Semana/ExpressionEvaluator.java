import java.beans.Expression;
import java.util.ArrayList;
import java.util.List;

public class ExpressionEvaluator {
    List<Operator> operators;
    List<String> ops = new ArrayList<>();
    List<Integer> values = new ArrayList<>();

    public ExpressionEvaluator(List<Operator> operators) {
        this.operators = operators;
    }

    public int evaluate(String[] expression) {
        for (String element : expression) {
            if(isOpp(element)) {
                this.ops.add(element);
            } else if(isNumber(element)) {
                this.values.add(Integer.parseInt(element));
            } else if(element.equals(")")) {
                calculate();
            }
        }

        return this.values.get(0);
    }

    private void calculate() {
        String op = ops.get(ops.size() - 1);
        ops.remove(ops.size() - 1);

        int a = values.get(values.size() - 1);
        values.remove(values.size() - 1);

        int b = values.get(values.size() - 1);
        values.remove(values.size() - 1);

        for(Operator operator : operators) {
            if(op.equals(operator.getSymbol())) {
                values.add(operator.calculate(b, a));
                return;
            }
        }
    }

    private boolean isOpp(String element) {
        for (Operator opp : this.operators) {
            if(element.equals(opp.getSymbol())) {
                return true;
            }
        }

        return false;
    }

    private static boolean isNumber(String element) {
        return element.matches("\\d+");
    }
}
