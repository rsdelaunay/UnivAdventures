import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Teste {

	public static void main(String[] args) {
		//Exercicio 2.1.
		//testes
		Aluno a1 = new Aluno("Zé", 111);
		Aluno a2 = new Aluno("Ana", 222);
		Aluno a3 = new Aluno("Rui", 333);
		Aluno a4 = new Aluno("Inês", 444);
		Aluno a5 = new Aluno("Ricardo", 555);

		Disciplina poo = new Disciplina("Programacao Orientada para Objetos", "POO", 5);
		poo.inscrever(a1);
		poo.inscrever(a2);
		poo.inscrever(a3);
		poo.inscrever(a4);
		poo.inscrever(a5);
		poo.lancarNota(18,a1.getNumero());
		poo.lancarNota(16,a2.getNumero());
		poo.lancarNota(18,a3.getNumero());
		poo.lancarNota(15,a4.getNumero());
		poo.lancarNota(18,a5.getNumero());

		ArrayList<Aluno> melhoresAlunos = poo.melhoresAlunos();
		System.out.println("Melhores Alunos:");
		for (Aluno aluno : melhoresAlunos) {
			System.out.println(aluno); //mostrar melhores alunos
		}

		//Exercicio 2.2
		ArrayList<Aluno> alunos = Aluno.readAlunos("estudantes.txt");
		for (Aluno aluno : alunos) {
			System.out.println(aluno);
		}
		//2.3. a partir do 2.2.
		//Disciplina tmc = Disciplina.createDisciplina("Teoria da Matematica para Computacao", 10, alunos);
		//tmc.showInscricoes();

		//2.4. testar criar ficheiro
		//tmc.createFileDisciplina(tmc, "teste_tmc.txt");

		//2.5 testar criação disciplina com 2 ficheiros
		System.out.println(Disciplina.createDisciplinaWFiles("Estudantes.txt","teste_tmc.txt"));



	
//		ArrayList<Disciplina> disciplinas = lerDisciplinas(new File("disciplinas_ige.txt"));
//		for (Disciplina d : disciplinas)
//			System.out.println(d);
		
	}
	
	// Exemplo de metodo para leitura de um ficheiro de texto
	// com informação sobre disciplinas (ver formato nos slides)
	public static ArrayList<Disciplina> lerDisciplinas(File ficheiro) {
	
		ArrayList<Disciplina> disciplinas = new ArrayList<>();
		
		try {
			Scanner fs = new Scanner(ficheiro);
			
			while(fs.hasNextLine()) {
				String sigla = fs.next();
				int cap = fs.nextInt();
				String nome = fs.nextLine();
				
				Disciplina d = new Disciplina(nome, sigla, cap);
				disciplinas.add(d);
			
			}
			fs.close();	
		
		} catch (FileNotFoundException e) {
			System.err.println("Problemas na abertura do ficheiro");
		}
		
		return disciplinas;
	}
}






