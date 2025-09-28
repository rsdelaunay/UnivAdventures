import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private List<Empregados> empregados;

    public Empresa() {
        this.empregados = new ArrayList<>();
    }

    public void adicionarEmpregado(Empregados empregado) {
        empregados.add(empregado);
    }

    public double calcularTotalSalarios() {
        double total = 0;
        for (Empregados empregado : empregados) {
            total += empregado.salario();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Empregados empregado : empregados) {
            sb.append(empregado.toString()).append("\n");
        }
        sb.append("Total dos salários: ").append(calcularTotalSalarios()).append("€");
        return sb.toString();
    }
}
