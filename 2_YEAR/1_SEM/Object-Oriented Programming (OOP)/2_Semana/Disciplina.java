import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Disciplina {
	
	private String nome;
	private String sigla;
	private int capacidade;
	
	private ArrayList<Inscricao> inscricoes = new ArrayList<>();
	
	public Disciplina(String nome, String sigla, int capacidade) {
		this.nome = nome;
		this.sigla = sigla;
		this.capacidade = capacidade;
	}
	
	public void inscrever(Aluno aluno) {
		if (inscricoes.size() < capacidade) {
			Inscricao insc = new Inscricao(aluno);
			inscricoes.add(insc);	
		}
		else
			System.err.println("O aluno " + aluno + " já não tem espaço em " + sigla + "...");
	}

	public void desinscrever(int numAluno) { //desinscrever aluno
		if(!this.inscricoes.isEmpty()) { //se inscrições tiverem algo
			if (findAluno(numAluno) != -1){
				inscricoes.remove(findAluno(numAluno)); //remove o aluno
			}
			else{ //aluno nao esta na lista
				return; //nao faz nada cfr enunciado
			}
		}
		else{
			return; //nao faz nada cfr enunciado
		}
	}

	public int findAluno(int numAluno) {
		int indice = 0; //indice
		// Percorre a lista de inscrições
		for (Inscricao inscricao : inscricoes) {
			// Verifica se o aluno existe neste index da lista
			if (inscricao.getAluno().getNumero() == numAluno) {
					// retorna o indice do
					return indice;
			}
			else{
				indice++; //caso nao encontre, adiciona +1 ao indice
			}
		}
		// Retorna -1 (inscrição não encontrada)
		return -1;
	}

	public void lancarNota(int nota, int numAluno){
		//validar inscricao aluno
		if (findAluno(numAluno) != -1){ //esta inscrito
			inscricoes.get(findAluno(numAluno)).setNota(nota); //ir à lista com indice aluno buscá-lo e depois
			//utilizar metodo setNota com int nota. Validacao da nota ocorre nesse mesmo metodo
		}
		else{ //não encontrou inscricao aluno
			System.err.println("Aluno não inscrito.");
		}
	}

	public boolean temNota(int numAluno){
		boolean temNota = false; //boolean
		if (findAluno(numAluno) != -1){ //esta inscrito
			temNota = inscricoes.get(findAluno(numAluno)).temNota(); //ir à lista com indice aluno buscá-lo e depois
			//utilizar metodo tem nota() e guardar resultado boolean em temNota
			return temNota;
		}
		else { //caso nao encontre inscricao
			return temNota; // retorna falso
		}
	}

	public int obterNota(int numAluno){
		int nota = -1; //crio integer nota
		if (findAluno(numAluno) != -1){ //esta inscrito
			nota = inscricoes.get(findAluno(numAluno)).getNota(); //ir à lista com indice aluno buscá-lo e depois
			//utilizar metodo get nota() e guardar resultado em nota
			return nota;
		}
		else { //caso nao encontre inscricao
			System.err.println("Aluno não inscrito."); //nao existe
			return -1; //retorna 1
		}
	}

	public double notaMedia(){
		int sum = 0; //somatorio das notas;
		int i = 0; //indice para contabilizar elementos dos quais se somou a nota
		for (Inscricao inscricao : inscricoes) { //percorre array list e guarda objetos inscricao em inscricao
			if (inscricao.getNota() == -1){ //caso aluno n tenha nota
				continue;	//continua
			}
			else { //soma alunos que tem nota
				sum += inscricao.getNota(); //somatorio das notas com getNota()
				i++; //indice vai subindo, percorrendo a lista toda
			}
		}
		return (double) (sum/i); //media dividindo pelo indice
	}

	public void showInscricoes(){ //para mostrar inscricoes
		for (Inscricao inscricao : inscricoes) { //for a mostrar inscricoes
			System.out.println(inscricao);
		}
	}

	public ArrayList<Aluno> melhoresAlunos() { //lista de melhores alunos
		ArrayList<Aluno> melhoresAlunos = new ArrayList<>(); //cria lista
		int maiorNota = 0; //maior nota a 0
		for (Inscricao inscricao : inscricoes) {
			int notaAtual = inscricao.getNota(); //guarda nota da inscricao atual
			if (notaAtual > maiorNota) {
				maiorNota = notaAtual; //guarda maior nota
			}
		}
		for (Inscricao inscricao : inscricoes) {
			if (inscricao.getNota() == maiorNota) { //quando alunos têm igual à melhor nota, guarda.
				melhoresAlunos.add(inscricao.getAluno()); //adiciona à arraylist criada inicialmente
			}
		}
		return melhoresAlunos; //return da array list melhores alunos
	}

	//2.3
	public static Disciplina createDisciplina(String nomeDisciplina, int capacidade, ArrayList<Aluno> alunos) {
		Disciplina disciplina = new Disciplina (nomeDisciplina, createSigla(nomeDisciplina), capacidade);
		inscreverAlunos(alunos, disciplina); //inscrever alunos com
		return disciplina;
	}

	public static void inscreverAlunos(ArrayList<Aluno> alunos, Disciplina disciplina) {
		for (Aluno aluno : alunos) {
			disciplina.inscrever(aluno); //inscrever aluno com nome e numero
			disciplina.lancarNota(-1, aluno.getNumero()); //lancar nota a NA
		}
	}

	public static String createSigla(String nomeDisciplina){
		String sigla = "";  //cria string com
		String [] tokens = nomeDisciplina.split(" ");
		for (String token : tokens) {
			sigla += token.charAt(0); //
		}
		return sigla;
	}

	public void createFileDisciplina(Disciplina disciplina, String nomeFicheiro){
		try {
			PrintWriter writer = new PrintWriter(new File(nomeFicheiro)); //novo writer
			writer.println(disciplina.nome); //nome disciplina
			writer.println(disciplina.capacidade); //capacidade
			for(Inscricao inscricoes : disciplina.inscricoes){
				if(inscricoes.getNota() == -1)
					writer.print(inscricoes.getAluno().getNumero() + " " + "NA" + "\n");
				else
					writer.print(inscricoes.getAluno().getNumero() + " " + inscricoes.getNota() + "\n");
			}
			writer.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("problema a escrever o ficheiro");
		}
	}

	//2.5. --> Por continuar
	public static Disciplina createDisciplinaWFiles(String file_aList, String file_info) {
		ArrayList<Aluno> listaAluno = Aluno.readAlunos(file_aList);
		try{
			Scanner sc = new Scanner(new File(file_info));
			String nomeDisciplina = sc.nextLine(); //nome disciplina
			int cap = sc.nextInt();  //capacidade
			sc.close();
			return createDisciplina(nomeDisciplina,cap,listaAluno);

		} catch (FileNotFoundException e) {
			System.err.println("Ficheiro não encontrado ");
		}
		return null;
	}




	@Override
	public String toString() {
		String aux = nome + " (" + sigla + ") - cap: " + capacidade + "\n";
		for (Inscricao insc : inscricoes)
			aux += insc + "\n";
		return aux;
	}




	
	
	
	
	
}
