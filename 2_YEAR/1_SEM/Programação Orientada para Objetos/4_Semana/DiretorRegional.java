public class DiretorRegional extends Empregados{

    private double lucroMensal;

    public DiretorRegional(String nome, double lucroMensal){
        super(nome);
        this.lucroMensal = lucroMensal;
    }

    @Override
    public double salario() {
        double premio = 0.01 * lucroMensal; // Prémio de 1% do lucro mensal da região
        return super.salario() * 2 + premio;
    }

    @Override
    public String toString() {
        return "Diretor regional: " + getNome() + ", com salário: " + salario() + "€";
    }

}
