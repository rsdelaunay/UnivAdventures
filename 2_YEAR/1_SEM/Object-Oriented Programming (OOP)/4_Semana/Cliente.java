import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private int id;
    private double valorMedioCompras;

    //construtor
    public Cliente(String nome, int id, double valorMedioCompras){
        this.nome = nome;
        this.id = id;
        this.valorMedioCompras = valorMedioCompras;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public double getValorMedioCompras() {
        return valorMedioCompras;
    }

    public double valorAPagarComDesconto(double valorAPagar) {
        return valorAPagar;
    }

    @Override
    public String toString(){
        return nome + " " +valorMedioCompras;
    }
}
