public class Aluno {
        int numero;
        String nome;
        int anoMatricula;
        int anoNascimento;

        public Aluno(int numero, String nome, int anoMatricula, int anoNascimento) {
            this.nome = nome;
            this.numero = numero;
            this.anoMatricula = anoMatricula;
            this.anoNascimento = anoNascimento;
        }

        public String getNome() {
            return nome;
        } // arranjar nome

        public int getNumero() {
            return numero;
        } // obter numero do aluno

        public int getAnoMatricula() {
            return anoMatricula;
        }

        public int getAnoNascimento() {
            return anoNascimento;
        }

        @Override
        public String toString() {
            return numero + ": " + nome + " " + anoMatricula + " " + anoNascimento;
        }

}
