public class GerenteDeLoja extends Empregado {
    private boolean cumpriuObjetivos;

    public GerenteDeLoja(boolean cumpriuObjetivos) {
        this.cumpriuObjetivos = cumpriuObjetivos;
    }

    @Override
    public double calcularSalario() {
        double salarioBase = super.calcularSalario();
        if (cumpriuObjetivos) {
            return salarioBase + 200; // Prémio de 200€ se a loja cumpriu os objetivos
        }
        return salarioBase;
    }

    @Override
    public String toString() {
        return "Gerente de loja com salário: " + calcularSalario() + "€";
    }
}