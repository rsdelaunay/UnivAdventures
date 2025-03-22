public class GerenteDeLoja extends Empregados{
    private boolean cumpriuObjetivos;

    public GerenteDeLoja(boolean cumpriuObjetivos, String nome) {
        super(nome);
        this.cumpriuObjetivos = cumpriuObjetivos;
    }

    @Override
    public double salario() {
        if (cumpriuObjetivos)
            return super.salario() + 200.0;
        return super.salario();
    }

    @Override
    public String toString() {
        return "Gerente de loja: " + getNome() + ", com salário: " + salario() + "€";
    }
}
