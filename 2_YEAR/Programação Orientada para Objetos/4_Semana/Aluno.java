public class Aluno {
    private String nome;
    private int numero;

    public Aluno(int numero, String nome) {
        this.nome = nome;
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Aluno{" + "nome=" + nome + ", numero=" + numero + '}';
    }

}
