public class Aluno {
    int numero;
    String nome;

    public Aluno(int numero, String nome) {
        this.nome = nome;
        this.numero = numero;

    }

    public String getNome() {
        return nome;
    } // arranjar nome

    public int getNumero() {
        return numero;
    } // obter numero do aluno


    @Override
    public String toString() {
        return numero + ": " + nome;
    }

}
