package objects;


public class Score {

    private String nome;
    private objects.Time time;

    public Score (String nome, objects.Time time){
        this.nome = nome;
        this.time = time;
    }

    public String getNome() {
        return nome;
    }

    public Time getTime() {
        return time;
    }

    @Override
    public String toString() {
        return nome + " - " + time;
    }


}
