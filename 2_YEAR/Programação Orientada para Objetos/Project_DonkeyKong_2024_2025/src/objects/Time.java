package objects;

public class Time {
    private int totalSeconds;

    public Time(int minutes, int seconds) {
        this.totalSeconds = minutes * 60 + seconds;
    }

    public Time(int seconds) {
        this.totalSeconds = seconds;
    }

    public Time(String timeStr) {
        String[] parts = timeStr.split(":");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        this.totalSeconds = minutes * 60 + seconds;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public int getMinutes() {
        return totalSeconds / 60;
    }

    public int getSeconds() {
        return totalSeconds % 60;
    }

    public Time add(Time other) {
        return new Time(0, this.totalSeconds + other.totalSeconds);
    }

    @Override
    public String toString() {
        return getMinutes() + ":" + String.format("%02d", getSeconds());
    }
}