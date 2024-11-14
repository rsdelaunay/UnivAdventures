package OrderedList;

import java.util.Arrays;

public class Sorting {//Precisamos de fazer isto para nós
	
	//Metodo auxiliar para comparar
	public static boolean less(Comparable a, Comparable b) {
		return a.compareTo(b) < 0; //compará-los
	}
	
	//Metodo para trocar
	public static void swap(Object[] a, int i, int j) { //neste caso qq object pq nao precisamos de comparar. basta trocar.
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	
	//Metodo insertion sort
	public static void sort(Comparable[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i; j >= 0 && less(a[j+1], a[j]); j--) {
				swap(a, j, j+1);
			}
		}
	}
	
	public static boolean isSorted(Comparable[] a) {
		for (int i = 0; i < a.length - 1; i++) { //para percorrer a lista
			if (less(a[i + 1], a[i])) 
				return false;
		}
		return true;
	}

	public static void shuffle(Object[] a) { //objeto tambem pq podemos trocar qq array
		for (int i = 0; i < a.length; i++) {
			int random = (int) ((i+1)*Math.random()); // para fazer random dos indices inteiros do array a ser sorted
			swap (a, i, random);
			
		}	
	}
	
	
	public static void main(String[] args) {
		Integer[] a = new Integer[] {5, 3, 1, 4, 2};
		sort(a);
		System.out.println(Arrays.toString(a));
		shuffle(a);
		System.out.println(Arrays.toString(a));
	}
	

}


