
public class Pessoa {

	private String nome;
	private int peso;
	private int altura;
	
	public Pessoa(String nome, int peso, int altura) {
		this.nome = nome;
		this.peso = peso;
		this.altura = altura;
	}
	public String getNome() {
		return nome;
	}
	public int getPeso() {
		return peso;
	}
	
	public void setPeso(int peso) {
		this.peso=peso;
	}
	
	public int getAltura() {
		return altura;
	}
	@Override
	public String toString() {
		return nome + " - " + peso + " Kg - " + altura + " cm";
	}
}
