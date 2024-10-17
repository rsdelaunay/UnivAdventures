public class Song {
    private String name;
    private Time duration;
    private int trackNumber;


    public Song(String name, Time duration, int trackNumber) {
        this.name = name;
        this.duration = duration;
        this.trackNumber = trackNumber;
    }

    public String getName() {
        return name;
    }

    public Time getDuration() {
        return duration;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    @Override
    public String toString() {
        return trackNumber + ": " + duration + " " + name;
    }
}