import java.util.Iterator;
import java.util.NoSuchElementException;

public final class Interval implements Iterable<Integer> {
    private final int min;
    private final int max;

    public Interval(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public boolean isEmpty() {
        return min > max;
    }

    public int size() {
        return isEmpty() ? 0 : (max - min + 1);
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "Intervalo vazio";
        } else {
            return String.format("Intervalo [%d, %d], tamanho: %d", min, max, size());
        }
    }

    public static Interval empty() {
        return new Interval(0, -1); // Representa um intervalo vazio
    }

    public static Interval naturals(int max) { // Cria um intervalo de números naturais de 1 até max (inclusive)
        if (max < 1) {
            throw new IllegalArgumentException("O valor máximo para números naturais deve ser pelo menos 1.");
        }
        return new Interval(1, max);
    }

    public static Interval arrayIndexes(int[] array) {
        return new Interval(0, array.length - 1);
    }

    // Getters
    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    private class IntervalIterator implements Iterator<Integer> {
        private int current;

        public IntervalIterator() {
            this.current = min;
        }
        @Override
        public boolean hasNext() {
            return current <= max;
        }
        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return current++;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new IntervalIterator(); //obtem iterador
    }

}
