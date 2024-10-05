import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Aluno {

	private String nome;
	private int numero;
	
	public Aluno(String nome, int numero) {
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

	public static ArrayList<Aluno> readAlunos(String path){ //para guardar lista alunos. Estatico para posteriormente
		//utilizarmos sem termos que criar instancias
		//Nao depende de nenhum atributo da classe aluno e é geral -- pode ser estática.
		ArrayList<Aluno> alunos = new ArrayList<>();
		try { //try para procurar ficheiro
			Scanner s = new Scanner(new File(path)); //cria scanner com o ficheiro visto no path dado
			//costuma ser sempre assim:
			while (s.hasNextLine()){ //enquanto existe uma proxima linha no scanner
				String line = s.nextLine(); //guarda a linha em line
				String[] tokens = line.split(" ",2); //devolve um array de strings sem separacao do espaco no ficheiro.
				//1 Branca de Neve fica um array com 4 elementos
				//Parametro limite -- só vai separar duas vezes. assim ficamos com numero e nome
				int numero = Integer.parseInt(tokens[0]); //numero sera sempre o primeiro indice desse array de strings
				String nome = tokens[1]; //nome completo pois metemos limite no line.split
				Aluno a = new Aluno(nome,numero); //cria objeto aluno
				alunos.add(a); //add aluno à list
				}
			s.close();
		} catch (FileNotFoundException e) { //caso nao encontre o ficheiro
			e.printStackTrace();
		}
		return alunos;
	}



}
