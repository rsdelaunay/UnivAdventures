import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {

    private final T[] array;
    private int i = 0; //indice atual
    //6.6
    public ArrayIterator(T[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return i < array.length;
    }

    @Override
    public T next() {
        if (hasNext()){
            return array[i++]; //retorna elemento atual e incrementa a posição
        }
        else{
            throw new NoSuchElementException();
        }
    }

}
