package Aula_01;
import java.util.Scanner; //para termos o scanner
//Aluno n.º 122123 Rodrigo Delaunay
public class Date {
	
	private int month;
	private int day;
	private int year;
	
	
	public Date(int month, int day, int year) {
		if (isValid(month,day,year) == false) //Para validar a data fornecida
			throw new IllegalArgumentException("Data fornecida é ilegal!");
		this.month = month; //this. vai utilizar a variável atributo.
		this.day = day;
		this.year = year;
	}
	
	private boolean isValid(int month, int day, int year) { //como é uma função interna da classe pode ser private
		boolean validYear = year >= 0 ; //ano DC
		boolean validMonth = month >= 1 && month <= 12; //mês entre 1 e 12
		boolean validDay = day >= 1 && day <= daysInMonth(month);//porque os dias dependem do mês
		return validYear && validMonth && validDay; // para os números fornecidos serem válidos, temos que ter as três variáveis
													//true.
	}
	
	private int daysInMonth(int month) {
		if (month == 11 || month == 9 || month == 6 || month == 4) //meses abril, junho, setembro e novembro têm 30 dias.
			return 30;
		if (month == 2) {
			if (isLeapYear(year)) { //descobrir se o fevereiro é em ano bissexto ou não
				return 29;}// caso seja, fev tera 29 dias.
			else return 28;} // caso nao, 28 dias.
		return 31;//restantes meses.
	}
	
	private boolean isLeapYear (int year) { //ERRO NESTE. TESTAR.
	    // Verificar se o ano é divisivel por 4
        boolean divBy4 = year % 4 == 0;
        // Verificar se o ano é divisivel por 100
        boolean divBy100 = year % 100 == 0;
        // Verificar se o ano é divisivel por 400
        boolean divBy400 = year % 400 == 0;
        // Ano bissexto ou é:
        // - Divisivel por 4 e nao por 100;
        // - Divisivel por 400.
        return (divBy4 && !divBy100) || divBy400;
		}
	
	public boolean before(Date other) { //is this date before other?
		if (year() > other.year) { //se o ano da nossa data for maior, date foi depois. Portanto date é not before
			return false;}
		if (year() < other.year) { //se o ano da nossa data for menor, other foi depois. Portanto date é before
			return true;}
		if (month() < other.month) {//Anos iguais, se o mês da nossa data for menor, date foi antes. Before true 
			return true;}
		if (month() > other.month) {//Anos iguais, se o mês da nossa data for menor, date foi antes. Before false 
			return false;}
		if (day() < other.day) {//Anos iguais, meses iguais, se o dia da nossa data for menor, date foi antes. Before true. 
			return true;}
		if (day() > other.day) {//Anos iguais, meses iguais, se o dia da nossa data for menor, date foi antes. Before false. 
			return false;}
		return false; //se for tudo igual, não será before.
	}
	
	public int daysSinceBeginYear() {
		if (month == 1) { // se for janeiro, basta os dias desse mês
			return day;
		}
		int count_days = 0;
		for (int i = 1; i < month; i++) {
			count_days+=daysInMonth(i); //vai somando dias totais ate mes n-1
		}
		count_days+= day; // depois basta somar o dia do mês fornecido
		return count_days; //retornar.
	}
	
	public int daysUntilEndYear() {
		if (month == 12) { // se for dezembro, basta 31 - o dia fornecido
			return 31-day;
		}
		int count_days = 0;
		for (int i = 12; i > month; i--) {
			count_days+=daysInMonth(i); //vai somando dias totais ate mes n+1
		}
		count_days+= daysInMonth(month)-day; //dias para terminar o mes a decorrer.
		return count_days;
	}
		
	public int daysBetween(Date other) {
		int count_days = 0;
		if (before(other)) {
			//dada fornecida antes do other
			if (other.year() == year) { //data do other esta no mesmo ano.
				if (other.month == month) { //mesmo mês
					count_days = other.day()-day; //diferença dias
					return count_days;
				}
				if (other.month() == month+1) { //se for só um mês de diferença
					count_days = other.day() + (daysInMonth(month)-day); //dias do other ja decorridos e dias restantes na data antes
					return count_days;
				}
				//mais que um mês de diferença - somar os dias dos meses entre as datas
				for (int i = other.month()-1; i > month; i--) {
					count_days += daysInMonth(i);
				}
				//somar os dias do other aos dias que ainda faltam decorrer do mês da data antes
				count_days+= other.day() + (daysInMonth(month)-day);
				return count_days;
			}
			if (other.year() == year+1) { // um ano de diferença - basta somar os dias que faltam à data antes (other) e os dias que já decorreram na data depois
				count_days = other.daysSinceBeginYear()+daysUntilEndYear();
				return count_days;
			}
			// mais que um ano de dif
			count_days = other.daysSinceBeginYear()+daysUntilEndYear();
			for (int i = other.year()-1; i > year; i--) {
				if (isLeapYear(i)) {
					count_days+=366;} // se o ano for bissexto, soma-se 366 dias
				else {
					count_days+=365;}
			}
			return count_days;
		}
		else { //repetição para caso a data não seja before other 
			if (year == other.year()) { //datas mesmo ano
				if (month == other.month()) { //mesmo mês
					count_days = day-other.day(); //diferença dias
					return count_days;
				}
				if (month == other.month()+1) { //um mês de diferença
					count_days = day + (daysInMonth(other.month())-other.day());
					return count_days;
				}// mais que um mês de diferença
				for (int i = month-1; i > other.month(); i--) {
					count_days += daysInMonth(i);
				}
				count_days+= day + (daysInMonth(other.month())-other.day());
				return count_days;
			}
			if (year == other.year()+1) { //um ano de diferença
				count_days = daysSinceBeginYear()+other.daysUntilEndYear();
				return count_days;
			}
			//mais que um ano de diferença
			count_days = daysSinceBeginYear()+other.daysUntilEndYear();
			for (int i = year-1; i > other.year(); i--) {
				if (isLeapYear(i)) {
					count_days+=366;} // se o ano for bissexto, soma-se 366 dias
				else {
					count_days+=365;}
			}
			return count_days;
		}
	}
	
	public int month() {
		return month; //retorna month
	}
	
	public int day() {
		return day; //retorna day
	}
		
	public int year() {
		return year; //retorna year
	}
	
	public String toString() { //função para transformar a data em string e depois aparecer.
		//Colocar a data à estilo americano 
		return month + "/" + day + "/" + year; //aparece na consola.
	}
	
	public static void main(String[] args) { //fazer sempre assim para o main que irá ser corrido
		Scanner sc = new Scanner(System.in); //recebe onde vai ler, portando metemos System.in
		
		System.out.print("Introduza o mês: ");
		int month = sc.nextInt(); //digo ao scanner para ler a next int que o utilizador escreveu. Para strings - nextLn
		
		System.out.print("Introduza o dia: ");
		int day = sc.nextInt(); //digo ao scanner para ler a next int que o utilizador escreveu
		
		System.out.print("Introduza o ano: ");
		int year = sc.nextInt(); //digo ao scanner para ler a next int que o utilizador escreveu
	
		sc.close(); //fechar para ler somente um string
		
		Date d = new Date(month, day, year);
		
		System.out.println(d.toString()); //podemos, por omissão, não escrever o toString pois o java vai procurar

	}

	
	
	
	
	
	
	
	
	
	
	

}
