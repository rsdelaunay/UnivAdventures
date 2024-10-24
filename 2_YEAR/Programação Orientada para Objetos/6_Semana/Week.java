import java.util.Iterator;

public class Week implements Iterable<WeekDay> {
    private final WeekDay[] days;

    public Week() {
        days = new WeekDay[]{
                new WeekDay("MONDAY"),
                new WeekDay("TUESDAY"),
                new WeekDay("WEDNESDAY"),
                new WeekDay("THURSDAY"),
                new WeekDay("FRIDAY")
        };
    }

    @Override
    public Iterator<WeekDay> iterator() {
        return new WeekDayIterator(days);
    }

    public class WeekDayIterator implements Iterator<WeekDay> {
        private int i = 0;
        private final WeekDay[] days;

        public WeekDayIterator(WeekDay[] days) {
            this.days = days;
        }

        @Override
        public boolean hasNext() {
            return i < days.length;
        }

        @Override
        public WeekDay next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return days[i++];
        }

    }
}
