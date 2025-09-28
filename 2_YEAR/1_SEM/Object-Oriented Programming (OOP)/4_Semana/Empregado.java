public class Empregado {
        private static final double SALARIO_FIXO = 800;

        public double calcularSalario() {
            return SALARIO_FIXO;
        }

        @Override
        public String toString() {
            return "Empregado com salário: " + calcularSalario() + "€";
        }
    }
