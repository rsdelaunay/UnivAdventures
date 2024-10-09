import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private List<Empregado> empregados;

    public Empresa() {
        this.empregados = new ArrayList<>();
    }

    public void adicionarEmpregado(Empregado empregado) {
        empregados.add(empregado);
    }

    public double calcularTotalSalarios() {
        double total = 0;
        for (Empregado empregado : empregados) {
            total += empregado.calcularSalario();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Empregado empregado : empregados) {
            sb.append(empregado.toString()).append("\n");
        }
        sb.append("Total dos salários: ").append(calcularTotalSalarios()).append("€");
        return sb.toString();
    }
}