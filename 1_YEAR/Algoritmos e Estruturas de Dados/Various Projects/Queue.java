import java.util.Iterator;
//Aluno n.� 122123 Rodrigo Delaunay

public class Queue<T> implements Iterable<T> {
    private class Node { //privada - so se pode utilizar na classe Queue. Representa linked objects
        public T item;
        public Node next; //pr�ximo node

    }

    private Node first, last; //para criar o primeiro nó (first) e o último e saber-se quais são. vai ser um atributo da queue.
    private int size;

    public Queue() { //construtor
        first = null; // quando começo uma queue, o first não existe.
        size = 0;
    }

    public void enqueue(T item) {//add item to the queue
        Node oldlast = last; //guarda o ultimo elemento da queue
        last = new Node();
        last.item = item;
        last.next = null;
        if (!isEmpty()) //caso especial em que caso queue esteja vazia
            oldlast.next = last;
        else
            first = last;
        size++;
    }

    public T dequeue() {
        if (isEmpty())
            throw new IllegalStateException("Queue underflow! :(");
        T item = first.item; //guarda primeiro
        first = first.next; //first aponta para o next element
        //size fica menos
        size--;
        return item;
    } //remove and return the least recently added item

    public boolean isEmpty() {//is the queue empty?
        return first == null;
    }

    public int size() {
        return size;
    } //number of items in the queue

    public void shift() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty!");
        for (Node x = first; x.next != null; x = x.next) {
            if (x.next == last) {
                last.next = first;
                first = last;
                last = x;
                x.next = null;
                break;
            }
        }
    } //move the last element to the start of the queue

    public static void main(String[] args) {
        Queue<String> queue = new Queue<String>();
        queue.enqueue("Hello");
        queue.enqueue("World");
        queue.enqueue("!");
        queue.shift();
        queue.enqueue("tomaa");
        queue.dequeue();
        System.out.println(queue.size());
        for (String s : queue) {
            System.out.println(s);
        }
    }

    public Iterator<T> iterator() {
            return new QueueIterator();
    } //return the iterator for this queue

    private class QueueIterator implements Iterator<T> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            T item = current.item;
                current = current.next;
                return item;
        }
    }

}