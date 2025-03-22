public class Empregados {

    private static final double SALARIO_BASE = 800.0;
    private String nome;

    public Empregados (String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public double salario() {
        return SALARIO_BASE;
    }

    @Override
    public String toString() {
        return "Empregado: " + nome + ", Salário: " + salario() + "€";
    }
}
