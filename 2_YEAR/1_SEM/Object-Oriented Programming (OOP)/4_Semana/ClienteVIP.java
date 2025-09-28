import java.util.ArrayList;
import java.util.List;

public class ClienteVIP extends Cliente{

    public static final double limiarparaVIP = 100;

    private double descontoVIP;

    List<Cliente> clientes= new ArrayList<>();

    public ClienteVIP(String nome, int id, double valorMedioCompras, double descontoVIP){
        super(nome,id,valorMedioCompras);
        this.descontoVIP = descontoVIP;
    }

    public static List<ClienteVIP> promoverParaVIP (List<Cliente> clientes) {
        List<ClienteVIP> clientesPromovidos = new ArrayList<>(); //criar lista de novos VIPS
        List<Cliente> clientesClone = new ArrayList<>(clientes); //quando uma lista esta a ser percorrida não podemos remover elementos

        for (Cliente cliente : clientesClone) {
            if (cliente.getValorMedioCompras() > ClienteVIP.limiarparaVIP) { //se ultrupassarem o limiar são promovidos
                clientesPromovidos.add(new ClienteVIP(cliente.getNome(), cliente.getId(), cliente.getValorMedioCompras(), 0.1));
                clientes.remove(cliente); //remover na lista
            }
        }

        return clientesPromovidos;
    }

    @Override
    public double valorAPagarComDesconto(double valorAPagar){
        return valorAPagar -(valorAPagar * descontoVIP);
    }

    @Override
    public String toString(){
        return getNome()+"VIP"+ " " +getValorMedioCompras() + " acesso ao desconto de: " + descontoVIP;
    }
}
