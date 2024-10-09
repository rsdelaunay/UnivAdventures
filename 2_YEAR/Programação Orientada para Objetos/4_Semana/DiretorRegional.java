public class DiretorRegional extends Empregado {
    private double lucroMensal;

    public DiretorRegional(double lucroMensal) {
        this.lucroMensal = lucroMensal;
    }

    @Override
    public double calcularSalario() {
        double salarioBase = super.calcularSalario() * 2; // Salário fixo é o dobro do de um empregado comum
        double premio = 0.01 * lucroMensal; // Prémio de 1% do lucro mensal da região
        return salarioBase + premio;
    }

    @Override
    public String toString() {
        return "Diretor regional com salário: " + calcularSalario() + "€";
    }
}