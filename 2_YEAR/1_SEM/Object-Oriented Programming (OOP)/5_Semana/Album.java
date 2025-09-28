import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Album {
    private String name;
    private int year;
    ArrayList<String> artists;
    ArrayList<Song> songs;

    public Album(String name, int year, ArrayList<String> artists, ArrayList<Song> songs) {
        this.name = name;
        this.year = year;
        this.artists = artists;
        this.songs = songs;
    }

    public static Album load(File file) {
        String name = "";
        int year = -1;
        ArrayList<String> artists = new ArrayList<String>();
        ArrayList<Song> songs = new ArrayList<Song>();
        try{
            Scanner scanner = new Scanner(file);
            name = scanner.nextLine();
            year = scanner.nextInt();
            while (!Objects.equals(scanner.nextLine(), "\n")) {
                artists.add(scanner.nextLine());
            }
            int trackNumber = 1;
            while (scanner.hasNextLine()) {
                String song = scanner.nextLine();
                String[] tokens = song.split(" ", 2);
                String songName = tokens[1];
                songs.add(new Song(songName, new Time(tokens[0]), trackNumber));
                trackNumber++;
            }
        } catch (Exception e){
            System.out.println("Error loading album");
            return null;
        }
        if(name.isEmpty() || year == -1 || artists.isEmpty() || songs.isEmpty()){
            System.out.println("Error loading album");
            return null;
        }
        return new Album(name, year, artists, songs);
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public Time getDuration() {
        int time = 0;
        for (Song song : songs) {
            time += song.getDuration().getSeconds();
        }
        return new Time(time / 60, time % 60);
    }

    @Override
    public String toString() {
        String album = "";
        album+=name+"\n";
        album+=year+"\n";
        for (String artist : artists) {
            album+=artist+"\n";
        }
        for (Song song : songs) {
            album+=song.toString()+"\n";
        }
        return album;
    }
}