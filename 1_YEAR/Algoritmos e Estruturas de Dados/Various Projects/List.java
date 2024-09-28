import java.util.Iterator;
//Aluno n.º 122123 Rodrigo Delaunay
public class List<T> implements Iterable<T> {
    private class Node { //privada - so se pode utilizar nesta classe. Representa linked objects
        public T item;
        public Node next; //próximo node
        public Node previous;//além de gurdarmos item e o próximo node, queremos saber o anterior também.
        public Node(T item, Node next, Node previous) { //construtor do node
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    private Node first,last; //para criar o primeiro nó (first) e o último e saber-se quais são
    private int size;

    public List(){ //construtor
        first = null; // quando começo uma list, o first não existe.
        last = null; // last também
        size = 0;
    }
    public void add(T item){ //add item to the end of the list
        if (isEmpty()) { //caso a lista esteja vazia, first será igual ao last adicionando 1.
            last = new Node(item, null, last);
            first = last;
        } else {
            last.next = new Node(item, null, last); //cria o novo
            last = last.next;//novo last será o next do antigo last
        }
        size++;
        //Extra: Implementação terá um custo constante pois (i) envolve operações singulares constantes (não alteram conforme
        //o size da lista em questão; (ii) Não existem loops; (iii) Existe acesso direto aos assignments verificados e (iv)
        //Todas as operações são de custo constante, O(1).
    }
    public T get(int index){ //return the element at position index
        if (index <= 0 || index > size())
            throw new IllegalArgumentException("Index out of bounds.");
        Node pos = first; //cria nó com a primeira posição
        for (int n = 1; n != index; n++) {//ciclo for para percorrer a lista de nós
            pos = pos.next;} //assim que n for index, já não se mexe. o indice corresponderá, portanto, ao item do next node
        return pos.item; //retornar isso.
    }
    public T remove(int index){//remove and return the element at position index
        if (index <= 0 || index > size())
            throw new IllegalArgumentException("Index out of bounds.");
        Node prev = null, pos = first; //primeiro null
        for (int n = 1; n != index; n++) {
            prev = pos;
            pos = pos.next;
        }
        if (prev == null)
            first = first.next;
        else
            prev.next = pos.next;
        if (first == null)
            last = null;
        size--;
        return pos.item;
    }
    public boolean removeFirst(T item) { //remove the first occurrence of item; return false if nothing was removed, true otherwise
        for (Node current = first; current != null; current = current.next) { //vai de first ate null, contabilizando o last. first next next next até last
            if (current.item.equals(item)) { //.equals porque são objetos e são por referência. Não podemos avaliar objetos que funcionam com referência (somente tipos primitivos basta ==. String não é primitivo portanto também tem que ser assim)
                if (current == first) { // Se estivermos a remover o primeiro node
                    first = current.next; //será o primeiro
                    if (first != null)
                        first.previous = null; //estabelecer anterior nulo
                } else if (current == last) { // Se estivermos a remover o último
                    last = current.previous; //será o penultimo
                    if (last != null)
                        last.next = null;
                } else { // Se estivermos a remover um nó no meio
                    current.previous.next = current.next; //o next do previous passa a ser o next do current
                    current.next.previous = current.previous; //o previous ao next tornase o previous. eliminou-se ligação ao nó que queremos remover
                }
                current.item = null; // colocamos ainda o item a null para evitar garbage collection
                size--;
                return true;
            }
        }
        return false; // Item não encontrado na lista... false pois não removeu.
    }
    //Extra: Custo de implementação deste método é linear pois a operação mais custosa percorre todos os nós da lista (n elementos).
    //O(n) será o custo deste método

    public boolean removeLast(T item){ //remove the last occurrence of item; return false if nothing was removed, true otherwise
        for (Node current = last; current != null; current = current.previous) { //vai de last ate null, contabilizando o first.
            if (current.item.equals(item)) { //.equals porque são objetos e são por referência. Não podemos avaliar objetos que funcionam com referência (somente tipos primitivos basta ==. String não é primitivo portanto também tem que ser assim)
                if (current == first) { // Se estivermos a remover o primeiro node
                    first = current.next; //será o primeiro
                    if (first != null)
                        first.previous = null; //estabelecer anterior nulo
                } else if (current == last) { // Se estivermos a remover o último
                    last = current.previous; //será o penultimo
                    if (last != null)
                        last.next = null;
                } else { // Se estivermos a remover um nó no meio
                    current.previous.next = current.next; //o next do previous passa a ser o next do current
                    current.next.previous = current.previous; //o previous ao next tornase o previous. eliminou-se ligação ao nó que queremos remover
                }
                current.item = null; // colocamos ainda o item a nulo para evitar garbage collection
                size--;
                return true;
            }
        }
        return false; // Item não encontrado na lista... false pois não removeu.
    }
    //Extra: Tal como o método removeFirst tem um custo de implementação linear derivado do facto de percorrer, de forma iterada,
    //todos os nós da lista em análise, o removeLast irá de igual forma ter um custo de implementação linear (O(n)) pois
    //irá percorrer todos os nós da lista, só que do último nó (last) ao primeiro (first)

    public boolean removeAll(T item){  //remove all occurrences of item; return false if nothing was removed, true otherwise
        int contador_remove = 0;
        for (Node current = first; current != null; current = current.next) { //vai de first ate null, contabilizando o last. first next next next até last
            if (current.item.equals(item)) { //.equals porque são objetos e são por referência. Não podemos avaliar objetos que funcionam com referência (somente tipos primitivos basta ==. String não é primitivo portanto também tem que ser assim)
                if (current == first) { // Se estivermos a remover o primeiro node
                    first = current.next; //será o primeiro
                    if (first != null)
                        first.previous = null; //estabelecer anterior nulo
                } else if (current == last) { // Se estivermos a remover o último
                    last = current.previous; //será o penultimo
                    if (last != null)
                        last.next = null;
                } else { // Se estivermos a remover um nó no meio
                    current.previous.next = current.next; //o next do previous passa a ser o next do current
                    current.next.previous = current.previous; //o previous ao next tornase o previous. eliminou-se ligação ao nó que queremos remover
                }
                current.item = null; // colocamos ainda o item a nulo para evitar garbage collection
                size--;
                contador_remove++;
            }
        }
        return (contador_remove != 0); //Se for diferente de 0, removeu e retorna true. Senao, retorna falso.
    }
    public boolean isEmpty(){//is the list empty?
        return first == null; //se first ou last == null, quer dizer que está vazia.
        //Extra: Custo constante pois é uma operação direta e singular. O custo será O(1).
    }
    public boolean contains(T item) { //does the list contain item?
        for (Node current = first; current != null; current = current.next) { //vai de first ate null, contabilizando o last. first next next next até last
            if (current.item.equals(item)) //.equals porque são objetos e são por referência. Não podemos avaliar objetos que funcionam com referência (somente tipos primitivos basta ==. String não é primitivo portanto também tem que ser assim)
                return true; //tal como string, equals está "escondido" no código java
        }
        return false; //se correr tudo e não encontrar nada - false.
        //Extra: Implementação com custo linear pois efetua uma iteração por toda a lista no ciclo for, percorrendo os seus n elementos.
        //Custo será O(n).
    }

    public int size(){//number of elements in the list
        return size; //Extra: Custo constante pois é uma operação direta e singular. O custo será O(1).
    }

    public boolean isPalindrome() { //será a lista palindrome?
        int length = size(); //aranja tamanho list
        Node front = first; //dois pointers para percorrer a lista. front e back.
        Node back = last;
        // Iterar pela lista até se encontrarem no meio
        for (int i = 0; i < length / 2; i++) { //length/2 para se encontrarem no meio
            //comparar os items até se encontrarem a meio
            if (!front.item.equals(back.item)) { //se os items não forem iguais, falso.
                return false;
            }
            // Front segue para a frente
            front = front.next;
            // Back para tras. Importa verificar se o front e o back não são nulos. Senão corre mal.
            if (front != null) {
                back = back.previous; //Volta para o nó atrás.
            } else {
                // Se o front.next ou back forem nulos, algo correu mal e a lista não é palindrome
                return false;
            }
        }
        // Se todos os items forem iguais, torna true.
        return true;
    }
    //Extra: Implementação com custo linear pois iremos percorrer a lista como um todo! (duas metades de n/2 elementos).
    // O custo será O(n), sendo n o número de nós na lista.

    public Iterator<T> iterator(){
        return new ListIterator();
    }//return the iterator for the list
    private class ListIterator implements Iterator<T> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            T item = current.item; //guarda o item do current item
            current = current.next; //define current como o próximo
            return item; //retorna item.
        }
    }

    public static void main(String[] args){
        //Alguns testes...
        List<String> list = new List<String>(); //começa lista
        list.add("ola");
        list.add("ole");
        list.add("oli ole");
        list.add("ole");
        list.add("oli ole");
        String teste = "oli ole";
        list.removeAll(teste);
        int sizee =list.size();
        for (String str : list) {
            System.out.println(str);
        }
        System.out.println(sizee);
        boolean is = list.isPalindrome();
        System.out.println(is);
    }

}