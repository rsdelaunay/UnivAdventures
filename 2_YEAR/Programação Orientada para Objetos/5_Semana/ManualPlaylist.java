import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

interface Playlist {
    String getName();
    Time getDuration();
    List<Artist> getArtists();
    List<Song> getSongs();
}

class ManualPlaylist implements Playlist {
    private String name;
    private List<Song> songs;

    public ManualPlaylist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Time getDuration() {
        Time totalDuration = new Time(0, 0);
        for (Song song : songs) {
            totalDuration = totalDuration.add(song.getDuration());
        }
        return totalDuration;
    }

    @Override
    public List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();
        for (Song song : songs) {
            for (Album album : AiTunas.getAlbumsContainingSong(song)) {
                artists.addAll(album.getArtists());
            }
        }
        return artists;
    }

    @Override
    public List<Song> getSongs() {
        return songs;
    }
}

class ArtistPlaylist implements Playlist {
    private String name;
    private Artist artist;
    private List<Song> songs;

    public ArtistPlaylist(Artist artist) {
        this.name = "Playlist of " + artist.getName();
        this.artist = artist;
        this.songs = new ArrayList<>();
        for (Album album : artist.getAlbums()) {
            songs.addAll(album.getSongs());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Time getDuration() {
        Time totalDuration = new Time(0, 0);
        for (Song song : songs) {
            totalDuration = totalDuration.add(song.getDuration());
        }
        return totalDuration;
    }

    @Override
    public List<Artist> getArtists() {
        return Collections.singletonList(artist);
    }

    @Override
    public List<Song> getSongs() {
        return songs;
    }
}

class AiTunas {
    private List<Album> albums;
    private List<Artist> artists;
    private List<Playlist> playlists;

    public AiTunas() {
        this.albums = new ArrayList<>();
        this.artists = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }

    public List<Album> getAllAlbums() {
        Collections.sort(albums, (a1, a2) -> Integer.compare(a1.getYear(), a2.getYear()));
        return albums;
    }

    public List<Artist> getAllArtists() {
        Collections.sort(artists, (a1, a2) -> a1.getName().compareTo(a2.getName()));
        return artists;
    }

    public Artist findArtist(String name) {
        for (Artist artist : artists) {
            if (artist.getName().equalsIgnoreCase(name)) {
                return artist;
            }
        }
        return null;
    }

    public void addArtist(Artist artist) {
        artists.add(artist);
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public List<Playlist> getAllPlaylists() {
        return playlists;
    }

    public static List<Album> getAlbumsContainingSong(Song song) {
        List<Album> albumsWithSong = new ArrayList<>();
        for (Album album : albums) {
            if (album.getSongs().contains(song)) {
                albumsWithSong.add(album);
            }
        }
        return albumsWithSong;
    }

}
